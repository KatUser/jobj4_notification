package ru.checkdev.notification.telegram.service;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.beans.factory.annotation.Value;
import reactor.core.publisher.Mono;
import ru.checkdev.notification.domain.PersonDTO;

import java.time.Duration;
import reactor.util.retry.Retry;


@Service
@Slf4j
@Setter
public class TgAuthCallWebClient {
    private WebClient webClient;

    public TgAuthCallWebClient(@Value("${server.auth}") String urlAuth) {
        this.webClient = WebClient.create(urlAuth);
    }

    /**
     * Метод GET с применением Retry и Circuit Breaker
     *
     * @param url URL http
     * @return Mono<Person>
     */
    @CircuitBreaker(name = "tgAuthCircuitBreaker", fallbackMethod = "fallbackGet") // Применение Circuit Breaker
    public Mono<PersonDTO> doGet(String url) {
        return webClient
                .get()
                .uri(url)
                .retrieve()
                .bodyToMono(PersonDTO.class)
                .retryWhen(
                        Retry.backoff(3, Duration.ofSeconds(1))
                                .maxBackoff(Duration.ofSeconds(10))
                )
                .doOnError(err -> log.error("API not found: {}", err.getMessage()));
    }

    /**
     * Метод POST с применением Retry и Circuit Breaker
     *
     * @param url       URL http
     * @param personDTO Body PersonDTO.class
     * @return Mono<Object>
     */
    @CircuitBreaker(name = "tgAuthCircuitBreaker", fallbackMethod = "fallbackPost") // Применение Circuit Breaker
    public Mono<Object> doPost(String url, PersonDTO personDTO) {
        return webClient
                .post()
                .uri(url)
                .bodyValue(personDTO)
                .retrieve()
                .bodyToMono(Object.class)
                .retryWhen(
                        Retry.backoff(3, Duration.ofSeconds(1))
                                .maxBackoff(Duration.ofSeconds(10))
                )
                .doOnError(err -> log.error("API not found: {}", err.getMessage()));
    }

    // Fallback метод для GET
    public Mono<PersonDTO> fallbackGet(String url, Throwable throwable) {
        log.error("GET request failed, fallback triggered: {}", throwable.getMessage());
        return Mono.empty(); // Или возвращайте какой-то запасной ответ
    }

    // Fallback метод для POST
    public Mono<Object> fallbackPost(String url, PersonDTO personDTO, Throwable throwable) {
        log.error("POST request failed, fallback triggered: {}", throwable.getMessage());
        return Mono.empty(); // Или возвращайте какой-то запасной ответ
    }
}

package ru.checkdev.notification.telegram.service;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.checkdev.notification.domain.PersonDTO;

@Service
@Slf4j
@Setter
public class TgMockCallWebClient {

    private WebClient mockWebClient;

    public TgMockCallWebClient(@Value("${server.mock}") String urlAuth) {
        this.mockWebClient = WebClient.create(urlAuth);
    }

    @Retry(name = "tgMockRetry") // Применение Retry
    @CircuitBreaker(name = "tgMockCircuitBreaker", fallbackMethod = "fallbackPost") // Применение Circuit Breaker
    public Mono<PersonDTO> doPostToBind(String url, String email) {

        return mockWebClient
                .post()
                .uri(url)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject(new PersonDTO(email)))
                .retrieve()
                .bodyToMono(PersonDTO.class)
                .doOnError(err -> log.error("API not found: {}", err.getMessage()));

    }

    @Retry(name = "tgMockRetry") // Применение Retry
    @CircuitBreaker(name = "tgMockCircuitBreaker", fallbackMethod = "fallbackPost") // Применение Circuit Breaker
    public Mono<Void> doPostToUnbind(String url, String email) {

        return mockWebClient
                .post()
                .uri(url)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject(new PersonDTO(email)))
                .retrieve()
                .bodyToMono(Void.class)
                .doOnError(err -> log.error("API not found: {}", err.getMessage()));

    }

    // Fallback метод для POST
    public Mono<String> fallbackPost(String url, String string, Throwable throwable) {
        log.error("POST request failed, fallback triggered: {}", throwable.getMessage());
        return Mono.empty(); // Или возвращайте какой-то запасной ответ
    }
}

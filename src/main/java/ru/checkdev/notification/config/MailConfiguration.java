package ru.checkdev.notification.config;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.extern.slf4j.Slf4j;
import net.sargue.mailgun.Configuration;
import net.sargue.mailgun.Mail;
import net.sargue.mailgun.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;
import ru.checkdev.notification.domain.Setting;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
public class MailConfiguration {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    @Retry(name = "tgAuthRetry") // Применение Retry
    @CircuitBreaker(name = "circuitBreaker", fallbackMethod = "fallSend")
    public void send(String subject, String body, String to, List<Setting> settings) {
        Map<Setting.Key, Setting> keys = settings.stream().collect(
                Collectors.toMap(Setting::getKey, x -> x)
        );
        Configuration configuration = new Configuration()
                .domain("mail.hunt4.pro")
                .apiKey("c01235aad1495736b5ccfa9aa73ccdc0-a4502f89-b32888cf")
                .from("Команда Hunt4.pro", keys.get(Setting.Key.FROM).getValue());
        Response response = Mail.using(configuration)
                .to(to)
                .subject(subject)
                .text(body)
                .build()
                .send();
        logger.debug("send email to {}, {}", to, response.responseMessage());
    }

    // Fallback метод для Send Mail
    public Mono<Object> fallbackPost(String subject, String body, String to, List<Setting> settings, Throwable throwable) {
        log.error("Send Mail request failed, fallback triggered: {}", throwable.getMessage());
        return Mono.empty();
    }
}

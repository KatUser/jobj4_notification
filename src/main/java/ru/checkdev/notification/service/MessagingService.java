package ru.checkdev.notification.service;

import jakarta.annotation.PreDestroy;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.checkdev.notification.model.Notify;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

@Service
@AllArgsConstructor
public class MessagingService {

    private final TemplateService templates;

    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public void put(final Notify notify) {
        this.scheduler.execute(() -> this.templates.send(notify));
    }

    @PreDestroy
    public void close() {
        this.scheduler.shutdown();
    }
}

package ru.checkdev.notification.telegram.service;

import lombok.AllArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.checkdev.notification.domain.PersonDTO;

@AllArgsConstructor
@Service
public class ServiceSendingToKafka {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public PersonDTO send(PersonDTO personDTO) {
        kafkaTemplate.send("job4j_notification", personDTO);
        return personDTO;
    }
}

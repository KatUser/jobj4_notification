package ru.checkdev.notification.service;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.checkdev.notification.model.SubscribeTopic;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class SubscribeTopicServiceTest {

    @Autowired
    private SubscribeTopicService service;

    @Test
    public void whenGetAllSubTopicReturnContainsValue() {
        SubscribeTopic subscribeTopic = this.service.save(new SubscribeTopic(0, 1, 1));
        List<SubscribeTopic> result = this.service.findAll();
        assertTrue(result.contains(subscribeTopic));
    }

    @Test
    public void requestByUserIdReturnCorrectValue() {
        SubscribeTopic subscribeTopic = this.service.save(new SubscribeTopic(1, 2, 2));
        List<Integer> result = this.service.findTopicIdsByUserId(subscribeTopic.getUserId());
        assertEquals(result, List.of(2));
    }

    @Test
    public void whenDeleteTopicCatItIsNotExist() {
        SubscribeTopic subscribeTopic = this.service.save(new SubscribeTopic(2, 3, 3));
        subscribeTopic = this.service.delete(subscribeTopic);
        List<SubscribeTopic> result = this.service.findAll();
        assertFalse(result.contains(subscribeTopic));
    }
}
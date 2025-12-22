package ru.checkdev.notification.web;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import ru.checkdev.notification.model.SubscribeTopic;
import ru.checkdev.notification.service.SubscribeTopicService;

import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class SubscribeTopicControllerTest {

    @Autowired
    private SubscribeTopicController subscribeTopicController;

    @MockitoBean
    private SubscribeTopicService service;

    private final SubscribeTopic subscribeTopic = new SubscribeTopic(1, 2, 5);

    @Test
    @WithMockUser
    public void whenFindTopicByUserId() {
        when(service.findTopicIdsByUserId(anyInt())).thenReturn(List.of(subscribeTopic.getTopicId()));
        var result = subscribeTopicController.findTopicByUserId(subscribeTopic.getUserId());
        assertThat(result).isNotNull();
        assertThat(result.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(Objects.requireNonNull(result.getBody()).contains(subscribeTopic.getTopicId())).isTrue();
    }
}
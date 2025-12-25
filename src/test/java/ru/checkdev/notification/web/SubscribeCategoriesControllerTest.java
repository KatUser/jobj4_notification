package ru.checkdev.notification.web;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.checkdev.notification.model.SubscribeCategory;
import ru.checkdev.notification.service.SubscribeCategoryService;

import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureMockMvc
public class SubscribeCategoriesControllerTest {

    @Autowired
    private SubscribeCategoriesController subscribeCategoriesController;

    @MockBean
    private SubscribeCategoryService subscribeCategoryService;

    private final SubscribeCategory subscribeCategory = new SubscribeCategory(1, 2, 5);

    @Test
    public void whenFindCategoriesByUserId() {
        when(subscribeCategoryService.findCategoriesByUserId(subscribeCategory.getUserId())).thenReturn(List.of(subscribeCategory.getCategoryId()));
        var result = subscribeCategoriesController.findCategoriesByUserId(subscribeCategory.getUserId());
        assertThat(result).isNotNull();
        assertThat(result.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(Objects.requireNonNull(result.getBody()).contains(5)).isTrue();
    }
}
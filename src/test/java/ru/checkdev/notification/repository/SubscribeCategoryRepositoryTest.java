package ru.checkdev.notification.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.checkdev.notification.model.SubscribeCategory;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
class SubscribeCategoryRepositoryTest {

    @Autowired
    private SubscribeCategoryRepository subscribeCategoryRepository;

    @BeforeEach
    void setUp() {
        subscribeCategoryRepository.deleteAll();
    }

    @Test
    void findAll() {
        SubscribeCategory subscribeCategory = new SubscribeCategory(1, 1);
        subscribeCategoryRepository.save(subscribeCategory);
        assertThat(subscribeCategoryRepository.findAll().contains(subscribeCategory)).isTrue();
    }

    @Test
    void findByUserId() {
        SubscribeCategory subscribeCategory = new SubscribeCategory(2, 1);
        subscribeCategoryRepository.save(subscribeCategory);
        assertThat(subscribeCategoryRepository.findByUserId(2).contains(subscribeCategory)).isTrue();
    }

    @Test
    void findByUserIdAndCategoryId() {
        SubscribeCategory subscribeCategory = new SubscribeCategory(2, 1);
        subscribeCategoryRepository.save(subscribeCategory);
        assertThat(subscribeCategoryRepository.findByUserIdAndCategoryId(2, 1).equals(subscribeCategory)).isTrue();
    }

    @Test
    void findEmptyAll() {
        assertThat(subscribeCategoryRepository.findAll().isEmpty()).isTrue();
    }

    @Test
    void findByUserIdWhenUserDoesNotExist() {
        SubscribeCategory subscribeCategory = new SubscribeCategory(2, 1);
        subscribeCategoryRepository.save(subscribeCategory);
        assertThat(subscribeCategoryRepository.findByUserId(1).isEmpty()).isTrue();
    }

    @Test
    void findByUserIdAndCategoryIdWhenUserDoesNotExist() {
        SubscribeCategory subscribeCategory = new SubscribeCategory(2, 1);
        subscribeCategoryRepository.save(subscribeCategory);
        assertThat(subscribeCategoryRepository.findByUserIdAndCategoryId(5, 1)).isNull();
    }

    @Test
    void findByUserIdAndCategoryIdWhenCategoryDoesNotExist() {
        SubscribeCategory subscribeCategory = new SubscribeCategory(2, 1);
        subscribeCategoryRepository.save(subscribeCategory);
        assertThat(subscribeCategoryRepository.findByUserIdAndCategoryId(2, 5)).isNull();
    }
}
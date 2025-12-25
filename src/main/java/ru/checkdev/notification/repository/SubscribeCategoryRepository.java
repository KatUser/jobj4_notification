package ru.checkdev.notification.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.checkdev.notification.model.SubscribeCategory;

import java.util.List;

@Repository
public interface SubscribeCategoryRepository extends CrudRepository<SubscribeCategory, Integer> {

    @Override
    List<SubscribeCategory> findAll();

    List<SubscribeCategory> findByUserId(int id);

    SubscribeCategory findByUserIdAndCategoryId(int userId, int categoryId);

    void deleteAll();
}
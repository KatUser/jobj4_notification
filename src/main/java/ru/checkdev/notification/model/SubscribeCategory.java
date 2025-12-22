package ru.checkdev.notification.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "cd_subscribe_category")
public class SubscribeCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int userId;
    private int categoryId;

    public SubscribeCategory(int userId, int categoryId) {
        this.userId = userId;
        this.categoryId = categoryId;
    }
}
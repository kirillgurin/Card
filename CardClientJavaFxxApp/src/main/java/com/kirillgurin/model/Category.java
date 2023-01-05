package com.kirillgurin.model;

import java.util.Objects;

public class Category {
    private int id;
    private String name;
    private int userId;

    public Category() {
    }

    public Category(int id, String name, int user_id) {
        this.id = id;
        this.name = name;
        this.userId = user_id;
    }

    public Category(String name, int user_id) {
        this.name = name;
        this.userId = user_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return id == category.id && userId == category.userId && Objects.equals(name, category.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, userId);
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", user_id=" + userId +
                '}';
    }
}
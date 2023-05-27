package com.example.initialspring.domain;

public class Category {
    private String title;
    private int categoryId;


    public Category(String title, int categoryId) {
        this.title = title;
        this.categoryId = categoryId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }
}

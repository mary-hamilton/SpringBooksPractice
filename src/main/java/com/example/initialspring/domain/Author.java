package com.example.initialspring.domain;

public class Author {

    private String name;

    private int authorId;

    public Author(String name, int authorId) {
        this.name = name;
        this.authorId = authorId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }
}

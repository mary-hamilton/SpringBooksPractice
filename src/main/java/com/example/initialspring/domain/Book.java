package com.example.initialspring.domain;

public class Book {
    private int bookId;

    private String title;

    private String synopsis;
    private Category category;
    private Author author;

    public Book(int bookId, String title, String synopsis, Category category, Author author) {
        this.bookId = bookId;
        this.title = title;
        this.synopsis = synopsis;
        this.category = category;
        this.author = author;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }
}

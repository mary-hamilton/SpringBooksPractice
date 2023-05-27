package com.example.initialspring.service;

import com.example.initialspring.domain.Book;
import com.example.initialspring.repository.BookRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class BookService {
    private final BookRepository bookRepository;
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }
    public List<Book> getBooks() {
        return bookRepository.getBooks();
    }

    public Book getBookById(int bookId) {
        return bookRepository.getBookById(bookId);
    }

    private void validateBook(String title, String synopsis) throws IllegalArgumentException {
        ArrayList<String> problems = new ArrayList<>();
        if(title == null || title.isBlank()) {
            problems.add("Title cannot be empty");
        }
        if(synopsis == null || synopsis.isBlank()) {
            problems.add("Synopsis cannot be empty");
        }
        if(!problems.isEmpty()) {
            throw new IllegalArgumentException(String.join(", ", problems));
        }
    }

    public Book createBook(String title, String synopsis, int categoryId, int authorId) throws IllegalArgumentException {
        validateBook(title, synopsis);
        int bookId = bookRepository.createBook(title, synopsis, categoryId, authorId);
        return bookRepository.getBookById(bookId);
    }

    public Book updateBook(int bookId, String title, String synopsis, int categoryId, int authorId) throws IllegalArgumentException {
        validateBook(title, synopsis);
        bookRepository.updateBook(bookId, title, synopsis, categoryId, authorId);
        return bookRepository.getBookById(bookId);
    }

    public void deleteBook(int bookId) {
        bookRepository.deleteBook(bookId);
    }
}
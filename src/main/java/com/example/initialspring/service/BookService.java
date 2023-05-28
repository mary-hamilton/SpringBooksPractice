package com.example.initialspring.service;

import com.example.initialspring.domain.Book;
import com.example.initialspring.repository.BookRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    public List<String> getFields() {
        return bookRepository.getFields();
    }

    private void vaidateFieldSearch(String field, String query) {
        List<String> fields = getFields();
        ArrayList<String> problems = new ArrayList<>();
        if (field == null || field.isBlank()) {
            problems.add("Field name cannot be empty");
        }
        if (query == null || query.isBlank()) {
            problems.add("Query cannot be empty");
        }
        if (!fields.contains(field)) {
            problems.add("Must be a valid field name");
        }
        if (!problems.isEmpty()) {
            throw new IllegalArgumentException(String.join(", ", problems));
        }
    }

    public List<Book> getBooksByField(String field, String query) {
        query = query.replace("_", " ");
        vaidateFieldSearch(field, query);
        field = enragingConverter(field);
        return bookRepository.getBooksByField(field, query);
    }

    public Book getBookById(int bookId) {
        return bookRepository.getBookById(bookId);
    }

    private void validateBook(String title, String synopsis) throws IllegalArgumentException {
        ArrayList<String> problems = new ArrayList<>();
        if (title == null || title.isBlank()) {
            problems.add("Title cannot be empty");
        }
        if (synopsis == null || synopsis.isBlank()) {
            problems.add("Synopsis cannot be empty");
        }
        if (!problems.isEmpty()) {
            throw new IllegalArgumentException(String.join(", ", problems));
        }
    }
    private String enragingConverter(String field) {
        Pattern aliasTester = Pattern.compile("(\\w\\.\\w+)\\s+as\\s+(\\w+)");
        Matcher aliasFinder = aliasTester.matcher(BookRepository.getSqlSelect());
        boolean aliased = false;
        while(aliasFinder.find()) {
            if (field.equals(aliasFinder.group(2))){
                field = aliasFinder.group(1);
                aliased = true;
            }
        }
        if (!aliased) {
            field = "b." + field;
        }
        return field;
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

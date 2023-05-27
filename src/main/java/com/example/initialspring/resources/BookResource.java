package com.example.initialspring.resources;

import com.example.initialspring.domain.Book;
import com.example.initialspring.resources.responses.MessageJsonResponse;
import com.example.initialspring.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/books")
public class BookResource {
    private final BookService bookService;
    public BookResource(BookService bookService) {
        this.bookService = bookService;
    }
    @GetMapping
    public ResponseEntity<List<Book>> getBooks() {
        List<Book> books = bookService.getBooks();
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @GetMapping("/fields")
    public ResponseEntity<List<String>> getFields() {
        List<String> fields = bookService.getFields();
        return new ResponseEntity<>(fields, HttpStatus.OK);
    }

    @GetMapping("/{bookId}")
    public ResponseEntity<Book> getBookById(@PathVariable("bookId") int bookId) {
        Book book = bookService.getBookById(bookId);
        return new ResponseEntity<>(book, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Book> postBook(@RequestBody Map<String, Object> bookJson) {
        Book createdBook;
        try {
            createdBook = bookService.createBook(
                    (String)bookJson.get("title"),
                    (String)bookJson.get("synopsis"),
                    (int)bookJson.get("categoryId"),
                    (int)bookJson.get("authorId")
            );
        } catch (IllegalArgumentException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
        return new ResponseEntity<>(createdBook, HttpStatus.OK);
    }

    @PatchMapping("/{bookId}")
    public ResponseEntity<Book> updateBook(@RequestBody Map<String, Object> bookJson, @PathVariable("bookId") int bookId) {
        Book updatedBook;
        try {
            updatedBook = bookService.updateBook(
                    bookId,
                    (String)bookJson.get("title"),
                    (String)bookJson.get("synopsis"),
                    (int)bookJson.get("categoryId"),
                    (int)bookJson.get("authorId")
            );
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
        return new ResponseEntity<>(updatedBook, HttpStatus.OK);
    }

    @DeleteMapping("/{bookId}")
    public ResponseEntity<MessageJsonResponse> deleteBook(@PathVariable("bookId") int bookId) {
        bookService.deleteBook(bookId);
        return new ResponseEntity<>(new MessageJsonResponse("It's deleted"), HttpStatus.OK);
    }

}

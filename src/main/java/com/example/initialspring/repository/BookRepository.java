package com.example.initialspring.repository;

import com.example.initialspring.domain.Author;
import com.example.initialspring.domain.Book;
import com.example.initialspring.domain.Category;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
public class BookRepository {

    private static final String SQL_SELECT = "" +
            "SELECT b.book_id, b.title as book_title, b.synopsis, b.category_id, b.author_id, a.author_id, a.name as author_name, c.title as category_title " +
            "FROM Book b " +
            "LEFT JOIN Category c " +
            "ON b.category_id = c.category_id " +
            "LEFT JOIN Author a " +
            "ON b.author_id = a.author_id ";
    private static final String SQL_SELECT_BY_ID = SQL_SELECT + "WHERE b.book_id = ?";
    private static final String SQL_CREATE = "INSERT INTO Book (title, synopsis, category_id, author_id) VALUES (?,?,?,?)";
    private static final String SQL_UPDATE = "UPDATE Book SET title = ?, synopsis = ?, category_id = ?, author_id = ? WHERE book_id = ?";
    private static final String SQL_DELETE = "DELETE FROM Book WHERE book_id = ?";

    private final JdbcTemplate jdbcTemplate;

    public BookRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Book> bookRowMapper = ((rs, rowNum) -> new Book(
            rs.getInt("book_id"),
            rs.getString("book_title"),
            rs.getString("synopsis"),
            new Category(rs.getString("category_title"), rs.getInt("category_id")),
            new Author(rs.getString("author_name"), rs.getInt("author_id"))
    ));

    public List<Book> getBooks() {
        return jdbcTemplate.query(SQL_SELECT, bookRowMapper);
    }

    public List<Book> getBooksByField(String field, String query) {
        field = enragingConverter(field);
        String customQuery = SQL_SELECT + "WHERE " + field + " = ?";
        return jdbcTemplate.query(customQuery, bookRowMapper, query);
    }

    private String enragingConverter(String field) {
        switch(field) {
            case "author_name":
                field = "a.name";
                break;
            case "category_title":
                field = "c.title";
                break;
            case "book_title":
                field = "b.title";
                break;
            default:
                field = "b." + field;
        }
        return field;
    }

    public List<String> getFields() {
        Set<Set<String>> uniqueColumns = new HashSet<>(jdbcTemplate.query(SQL_SELECT, fieldMapper));
        List<Set<String>> listUniqueColumns = new ArrayList<>(uniqueColumns);
        return new ArrayList<>(listUniqueColumns.get(0));
    }

    private final RowMapper<Set<String>> fieldMapper = ((resultSet, rowNumber) -> {
        ResultSetMetaData metaData = resultSet.getMetaData();
        int columns = metaData.getColumnCount();
        Set<String> columnLabels = new HashSet<>();
        for (int i = 1; i <= columns; i++) {
            columnLabels.add(metaData.getColumnLabel(i));
        }
        return columnLabels;
    }
    );

    public Book getBookById(int bookId) {
        return jdbcTemplate.queryForObject(SQL_SELECT_BY_ID, bookRowMapper, bookId);
    }

    public int createBook(String title, String synopsis, int categoryId, int authorId) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_CREATE, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, title);
            preparedStatement.setString(2, synopsis);
            preparedStatement.setInt(3, categoryId);
            preparedStatement.setInt(4, authorId);
            return preparedStatement;
        }, keyHolder);
        return keyHolder.getKey().intValue();
    }

    public void updateBook(int bookId, String title, String synopsis, int categoryId, int authorId){
        jdbcTemplate.update(SQL_UPDATE, title, synopsis, categoryId, authorId, bookId);
    }

    public void deleteBook(int bookId) {
        jdbcTemplate.update(SQL_DELETE, bookId);
    }
}

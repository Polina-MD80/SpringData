package com.example.bookshopsystem.repository;

import com.example.bookshopsystem.model.entity.Author;
import org.hibernate.sql.Select;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    @Query("select a from Author a ORDER BY a.books.size DESC ")
    List<Author> getAllAuthorsByBooksDesc();



}
package com.example.bookshopsystem.service;

import com.example.bookshopsystem.model.entity.Author;

import java.io.IOException;

public interface AuthorService {
    void seedAuthors() throws IOException;

    Author getRandomAuthor();

    void getAllAuthorsWithTheCountOfTheirBooks();



}

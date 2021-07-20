package com.example.bookshopsystem.service;

import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public interface BookService {

    void seedBooks() throws IOException;

    void getAllBooksAfterYearByName(int i);

    void getAllAuthorsWithBooksBefore(int i);

    void getAllBooksAsTitleReleaseAndCopiesFrom(String firstName, String lastName);
}

package com.example.springintro.service;

import com.example.springintro.model.entity.AgeRestriction;
import com.example.springintro.model.entity.Book;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public interface BookService {
    void seedBooks() throws IOException;

    List<Book> findAllBooksAfterYear(int year);

    List<String> findAllAuthorsWithBooksWithReleaseDateBeforeYear(int year);

    List<String> findAllBooksByAuthorFirstAndLastNameOrderByReleaseDate(String firstName, String lastName);


    List<String> findAllBookTitlesWithAgeRestriction(AgeRestriction ageRestriction);

    List<String> findAllGoldenEditionBookTitlesWitLessThan5000();

    List<String> findAllBookTitlesAndPricesForBooksWithPriceLessThan5orMoreThan40();

    List<String> findAllBookTitlesForBooksNOtReleasedIn(LocalDate yearOfReleaseStart, LocalDate yearOfReleaseEnd);

    List<String> findAllBooksBefore(LocalDate releaseDate);

    List<String> findAllBookTitlesContaining(String str);

    List<String> findAllBookTitlesWithAuthorLastNameStartsWith(String str);

    int countAllBooksWithTitleLongerThan(int number);

    String findInfoForBookWithTitle(String bookTitle);


    int increaseCopiesByDateAndAmount(LocalDate date, int copies);

    int deleteFromBooksWhereCopiesAreLessThan(int numberOfCopies);

    String findAuthorsBookCount(String f_name, String l_name);
}

package com.example.springintro.repository;

import com.example.springintro.model.entity.AgeRestriction;
import com.example.springintro.model.entity.Book;
import com.example.springintro.model.entity.EditionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findAllByReleaseDateAfter(LocalDate releaseDateAfter);

    List<Book> findAllByReleaseDateBefore(LocalDate releaseDate);

    List<Book> findAllByAuthor_FirstNameAndAuthor_LastNameOrderByReleaseDateDescTitle(String author_firstName, String author_lastName);

    List<Book> findAllByAgeRestriction(AgeRestriction ageRestriction);

    List<Book> findAllByEditionTypeAndCopiesLessThan(EditionType editionType, Integer copies);

    List<Book> findAllByPriceLessThanOrPriceGreaterThan(BigDecimal price, BigDecimal price2);

    List<Book> findAllByReleaseDateBeforeOrReleaseDateAfter(LocalDate date1, LocalDate date2);

    List<Book> findAllByTitleContaining(String str);

    List<Book> findAllByAuthor_LastNameStartsWith(String str);

    @Query("select count(b) from Book b where length(b.title) > :number")
    int countBookByTitleLongerThan(int number);

    @Query("select concat(b.title,' ',b.editionType,' ', b.ageRestriction,' ',b.price) from Book b where b.title = :bookTitle")
    String bookInfo(@Param(value = "bookTitle") String bookTitle );

    Book findBookByTitle(String title);

    @Modifying
    @Query("update Book b set b.copies = b.copies + :copies where b.releaseDate > :date")
    int updateBookCopiesBy(@Param(value = "date") LocalDate date,@Param(value = "copies") int copies);

    @Modifying
    int deleteBooksByCopiesLessThan(int numberOfCopies);

    @Procedure("get_authors_books_by_author_name")
    String getAuthorsBookByAuthorsName(String f_name, String l_name);
}

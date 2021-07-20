package com.example.springintro;

import com.example.springintro.model.entity.AgeRestriction;
import com.example.springintro.model.entity.Book;
import com.example.springintro.service.AuthorService;
import com.example.springintro.service.BookService;
import com.example.springintro.service.CategoryService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class CommandLineRunnerImpl implements CommandLineRunner {

    private final CategoryService categoryService;
    private final AuthorService authorService;
    private final BookService bookService;
    private final BufferedReader reader;

    public CommandLineRunnerImpl(CategoryService categoryService, AuthorService authorService, BookService bookService, BufferedReader bufferedReader) {
        this.categoryService = categoryService;
        this.authorService = authorService;
        this.bookService = bookService;
        this.reader = bufferedReader;
    }

    @Override
    public void run(String... args) throws Exception {
        //tasks added by Poli_ip
        seedData();

        System.out.println("Enter task number:");
        String input;
        while (!"END".equals(input = reader.readLine())) {

            int taskNumber = Integer.parseInt(input);

            switch (taskNumber) {
                case 1 -> taskOne();
                case 2 -> taskTwo();
                case 3 -> taskThree();
                case 4 -> taskFour();
                case 5 -> taskFive();
                case 6 -> taskSix();
                case 7 -> taskSeven();
                case 8 -> taskEight();
                case 9 -> taskNine();
                case 10 -> taskTen();
                case 11 -> taskEleven();
                case 12 -> taskTwelve();
                case 13 -> taskThirteen();
                case 14 -> taskFourteen();
                default -> System.out.println("Not existing task number!");
            }
            System.out.println("Enter task number:");

        }

//        printAllBooksAfterYear(2000);
//        printAllAuthorsNamesWithBooksWithReleaseDateBeforeYear(1990);
//        printAllAuthorsAndNumberOfTheirBooks();
//        printALlBooksByAuthorNameOrderByReleaseDate("George", "Powell");

    }

    private void taskFourteen() throws IOException {
        //create procedure get_authors_books_by_author_name(
        //first_name_ VARCHAR(255), last_name_ VARCHAR(255), OUT result VARCHAR(255))
        //BEGIN
        //   set  result = (select concat_ws(' ', a.first_name, a.last_name,'has written', count(b.id), 'books')
        //    from authors as a
        //             join books b on a.id = b.author_id
        //    where a.first_name = first_name_
        //      and a.last_name = last_name_
        //    group by author_id);
        //
        //end;
        System.out.println("First create procedure from script in the taskFourteen method!!! Then..");
        System.out.println("Enter writers full name:");
        String[] tokens = reader.readLine().split("\\s+");
        String f_name = tokens[0];
        String l_name = tokens[1];


        System.out.println(this.bookService.findAuthorsBookCount(f_name, l_name));

    }

    private void taskThirteen() throws IOException {
        System.out.println("Enter number f copies:");
        int numberOfCopies = Integer.parseInt(reader.readLine());
        int deletedBooks = this.bookService.deleteFromBooksWhereCopiesAreLessThan(numberOfCopies);
        System.out.printf("Number of deleted books is %d%n", deletedBooks);
    }

    private void taskTwelve() throws IOException {
        System.out.println("Enter date:");
        LocalDate date = LocalDate.parse(reader.readLine(), DateTimeFormatter.ofPattern("dd MMM yyyy"));
        System.out.println("Enter copies:");
        int copies = Integer.parseInt(reader.readLine());
        int changedRows = bookService.increaseCopiesByDateAndAmount(date, copies);
        System.out.printf("%d books are released after %s, so total of %d book copies were added",
                changedRows, date, changedRows * copies);
    }

    private void taskEleven() throws IOException {
        System.out.println("Enter book title:");
        String bookTitle = reader.readLine();
        System.out.println(this.bookService.findInfoForBookWithTitle(bookTitle));
    }

    private void taskTen() {
        this.authorService.getAllAuthorsWithTheirTotalCopies().forEach(System.out::println);
    }

    private void taskNine() throws IOException {
        System.out.println("Enter number:");
        int number = Integer.parseInt(reader.readLine());
        int numberOfBooks = this.bookService.countAllBooksWithTitleLongerThan(number);
        System.out.printf("There are %d books with longer title than %d symbols%n", numberOfBooks, numberOfBooks);
    }

    private void taskEight() throws IOException {
        System.out.println("Enter author last name starts with:");
        String str = reader.readLine();

        bookService.findAllBookTitlesWithAuthorLastNameStartsWith(str)
                .forEach(System.out::println);
    }

    private void taskSeven() throws IOException {
        System.out.println("Enter containing string:");
        String str = reader.readLine();
        this.bookService.findAllBookTitlesContaining(str).forEach(System.out::println);

    }

    private void taskSix() throws IOException {
        System.out.println("Enter Author first name ending letters:");
        String endingString = reader.readLine();

        this.authorService.findAllFullNamesOfAuthorsFirstNameEndingWith(endingString)
                .forEach(System.out::println);

    }

    private void taskFive() throws IOException {
        System.out.println("Enter date of release:");
        String[] tokens = reader.readLine().split("-");
        int date = Integer.parseInt(tokens[0]);
        int month = Integer.parseInt(tokens[1]);
        int year = Integer.parseInt(tokens[2]);

        LocalDate releaseDate = LocalDate.of(year, month, date);

        this.bookService.findAllBooksBefore(releaseDate).forEach(System.out::println);
    }

    private void taskFour() throws IOException {
        System.out.println("Enter year of release:");
        int year = Integer.parseInt(reader.readLine());
        LocalDate yearOfReleaseStart = LocalDate.of(year, 1, 1);
        LocalDate yearOfReleaseEnd = LocalDate.of(year, 12, 31);

        this.bookService.findAllBookTitlesForBooksNOtReleasedIn(yearOfReleaseStart, yearOfReleaseEnd)
                .forEach(System.out::println);
    }

    private void taskThree() {
        this.bookService.findAllBookTitlesAndPricesForBooksWithPriceLessThan5orMoreThan40()
                .forEach(System.out::println);
    }

    private void taskTwo() {
        this.bookService.findAllGoldenEditionBookTitlesWitLessThan5000()
                .forEach(System.out::println);
    }

    private void taskOne() throws IOException {
        System.out.println("Enter age restriction:");
        AgeRestriction ageRestriction = AgeRestriction.valueOf(reader.readLine().toUpperCase());
        bookService.findAllBookTitlesWithAgeRestriction(ageRestriction)
                .forEach(System.out::println);

    }

    private void printALlBooksByAuthorNameOrderByReleaseDate(String firstName, String lastName) {
        bookService
                .findAllBooksByAuthorFirstAndLastNameOrderByReleaseDate(firstName, lastName)
                .forEach(System.out::println);
    }

    private void printAllAuthorsAndNumberOfTheirBooks() {
        authorService
                .getAllAuthorsOrderByCountOfTheirBooks()
                .forEach(System.out::println);
    }

    private void printAllAuthorsNamesWithBooksWithReleaseDateBeforeYear(int year) {
        bookService
                .findAllAuthorsWithBooksWithReleaseDateBeforeYear(year)
                .forEach(System.out::println);
    }

    private void printAllBooksAfterYear(int year) {
        bookService
                .findAllBooksAfterYear(year)
                .stream()
                .map(Book::getTitle)
                .forEach(System.out::println);
    }

    private void seedData() throws IOException {
        categoryService.seedCategories();
        authorService.seedAuthors();
        bookService.seedBooks();
    }
}

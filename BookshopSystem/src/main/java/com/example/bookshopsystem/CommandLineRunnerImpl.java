package com.example.bookshopsystem;

import com.example.bookshopsystem.repository.AuthorRepository;
import com.example.bookshopsystem.service.AuthorService;
import com.example.bookshopsystem.service.BookService;
import com.example.bookshopsystem.service.CategoryService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Component
public class CommandLineRunnerImpl implements CommandLineRunner {
    private final CategoryService categoryService;
    private final AuthorService authorService;
    private final BookService bookService;
    private BufferedReader reader;


    public CommandLineRunnerImpl(CategoryService categoryService, AuthorRepository authorRepository, AuthorService authorService, BookService bookService) {
        this.categoryService = categoryService;
        this.authorService = authorService;
        this.bookService = bookService;
        this.reader = new BufferedReader(new InputStreamReader(System.in));
    }
    //TODO read the next line!!!
    /// Expect to be asked for console input to chose subtask number!!

    @Override
    public void run(String... args) throws Exception {

        seedData();

        System.out.println("Enter subTask Number from 1 to 4:");
        int taskNumber = Integer.parseInt(reader.readLine());

        switch (taskNumber) {
            case 1 -> bookService.getAllBooksAfterYearByName(2000);
            case 2 -> bookService.getAllAuthorsWithBooksBefore(1990);
            case 3 -> authorService.getAllAuthorsWithTheCountOfTheirBooks();
            case 4 -> bookService.getAllBooksAsTitleReleaseAndCopiesFrom("George", "Powell");
            default -> System.out.println("Wrong subTask number");
        }


    }

    private void seedData() throws IOException {
        categoryService.seedCategories();
        authorService.seedAuthors();
        bookService.seedBooks();
    }
}

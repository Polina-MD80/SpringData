package com.example.bookshopsystem.service;

import com.example.bookshopsystem.model.entity.Author;
import com.example.bookshopsystem.repository.AuthorRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class AuthorServiceImpl implements AuthorService {
    private static final String AUTHORS_FILE_PATH = "src/main/resources/files/authors.txt";
    private final AuthorRepository authorRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public void seedAuthors() throws IOException {
        if (authorRepository.count() > 0) {
            return;
        }
        List<String> rows = Files.readAllLines(Path.of(AUTHORS_FILE_PATH));
        rows.forEach(row -> {
            String[] fullName = row.split("\\s+");
            Author author = new Author(fullName[0], fullName[1]);
            authorRepository.save(author);
        });
    }

    @Override
    public Author getRandomAuthor() {
        Random random = new Random();
        long val = random.nextInt(30) + 1;
        return authorRepository.findById(val).orElseThrow();
    }

    @Override
    public void getAllAuthorsWithTheCountOfTheirBooks() {
        authorRepository.getAllAuthorsByBooksDesc()
                .stream().map(a -> String.format("%s %s -> %d",
                a.getFirstName(), a.getLastName(), a.getBooks().size()))
                .forEach(System.out::println);
    }


}

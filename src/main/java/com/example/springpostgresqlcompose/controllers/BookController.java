package com.example.springpostgresqlcompose.controllers;

import com.example.springpostgresqlcompose.db.model.Book;
import com.example.springpostgresqlcompose.services.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/book")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class BookController {

    private final BookService bookService;

    @PostMapping
    public Book createNewBook(@RequestBody Book book) {
        return bookService.createNewBook(book);
    }

    @GetMapping
    public List<Book> getAllBook() {
        return bookService.getAllBook();
    }
}

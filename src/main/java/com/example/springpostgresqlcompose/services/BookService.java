package com.example.springpostgresqlcompose.services;

import com.example.springpostgresqlcompose.db.model.Book;
import com.example.springpostgresqlcompose.db.repositories.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class BookService {

    private final BookRepository bookRepository;

    public Book createNewBook(Book book) {
        return bookRepository.save(book);
    }

    public List<Book> getAllBook() {
        return bookRepository.findAll();
    }
}

package com.example.springpostgresqlcompose.db.repositories;

import com.example.springpostgresqlcompose.db.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}

package co.edu.javeriana.libreria.controller;

import co.edu.javeriana.libreria.domain.Book;
import co.edu.javeriana.libreria.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
public class BookController {

    @Autowired
    private BookService service;


    @GetMapping("/books")
    public List<Book> list() {
        return service.listAll();
    }

    @GetMapping("/books/{id}")
    public ResponseEntity<Book> get(@PathVariable Integer id) {
        try {
            Book book = service.get(id);
            return new ResponseEntity<Book>(book, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<Book>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/books")
    public void add(@RequestBody Book book) {
        service.save(book);
    }

    @PutMapping("/books/{id}")
    public ResponseEntity<?> update(@RequestBody Book book, @PathVariable Integer id) {
        try {
            Book existBook = service.get(id);
            service.save(book);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/books/{id}")
    public void delete(@PathVariable Integer id) {
        service.delete(id);
    }
}

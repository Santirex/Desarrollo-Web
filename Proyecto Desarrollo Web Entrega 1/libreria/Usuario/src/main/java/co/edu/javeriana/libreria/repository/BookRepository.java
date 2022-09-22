package co.edu.javeriana.libreria.repository;

import co.edu.javeriana.libreria.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Integer> {

}

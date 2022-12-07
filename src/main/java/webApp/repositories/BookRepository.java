package webApp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import webApp.models.Book;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
    List<Book> findByNameStartingWith(String title);
}

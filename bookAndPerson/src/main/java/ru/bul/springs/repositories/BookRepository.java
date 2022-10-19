package ru.bul.springs.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.bul.springs.models.Book;
import ru.bul.springs.models.Person;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book,Integer> {

   List<Book> findByNameBookStartingWith(String nameBook);




}

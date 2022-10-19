package ru.bul.springs.services;


import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.bul.springs.models.Book;
import ru.bul.springs.models.Person;
import ru.bul.springs.repositories.BookRepository;
import ru.bul.springs.repositories.PeopleRepository;

import java.util.*;

@Service
@Transactional(readOnly = true)
public class BookService {
   private final BookRepository bookRepository;

   private final PeopleRepository peopleRepository;



   @Autowired
    public BookService(BookRepository bookRepository, PeopleRepository peopleRepository) {
        this.bookRepository = bookRepository;
       this.peopleRepository = peopleRepository;
   }

    public List<Book> findAll(){
        return  bookRepository.findAll();
    }

    public Book findOne(int id){
        Optional<Book> founbook= bookRepository.findById(id);
        return founbook.orElse(null);
    }


    @Transactional
    public void save(Book book){

        bookRepository.save(book);

    }

    @Transactional
    public void update(int id,Book updatedBook){
        Book bookToBeUpdated = bookRepository.findById(id).get();

        updatedBook.setId(id);
        updatedBook.setOwner(bookToBeUpdated.getOwner());

        bookRepository.save(updatedBook);
    }

    @Transactional
    public void delete(int id){
        bookRepository.deleteById(id);
    }

    public Person getOwnerBook(int id){
        Optional<Book> book= bookRepository.findById(id);
        if(book.isPresent()){
            Hibernate.initialize(book.get().getOwner());
            return book.get().getOwner();
        }
        else {
            return null;
        }

    }
    @Transactional
    public void release(int id){
      Optional<Book> book = bookRepository.findById(id);
      book.get().setDateTake(null);
      book.get().setOwner(null);

    }
    @Transactional
    public void assign(int id,Person sperson){
        Optional<Book> book1 = bookRepository.findById(id);
        book1.get().setOwner(sperson);
        book1.get().setDateTake(new Date());
    }

    public List<Book> sort(){
       return bookRepository.findAll(Sort.by("yearBook"));
    }
    public List<Book> plagin(int page,int perPage){
       return bookRepository.findAll(PageRequest.of(page,perPage)).getContent();
    }

    public List<Book> combo(int page,int perPage){
        return bookRepository.findAll(PageRequest.of(page,perPage,Sort.by("yearBook"))).getContent();
    }
    public List<Book> findByNameBookStartingWith(String nameBook){
       return bookRepository.findByNameBookStartingWith(nameBook);
    }


    }




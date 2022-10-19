package ru.bul.springs.services;


import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.bul.springs.models.Book;
import ru.bul.springs.models.Person;
import ru.bul.springs.repositories.PeopleRepository;

import java.util.*;

@Service
@Transactional(readOnly = true)
public class PeopleService {

    private final PeopleRepository peopleRepository;

    @Autowired
    public PeopleService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    public List<Person> findAll(){

        return  peopleRepository.findAll();
    }

    public Person findOne(int id) {
        Optional<Person> foundPerson = peopleRepository.findById(id);
        return foundPerson.orElse(null);
    }


    @Transactional
    public void save(Person person){
        peopleRepository.save(person);

    }


    @Transactional
    public void update(int id,Person updperson){
        updperson.setId(id);
        peopleRepository.save(updperson);


    }

    @Transactional
    public void delete(int id){
        Optional<Person> person1=peopleRepository.findById(id);
        List<Book> booksForPerson=person1.get().getBooks();
        for (Book b:
             booksForPerson) {
            b.setDateTake(null);
        }
        peopleRepository.deleteById(id);
    }


    public List<Book> getBooksByPersonId(int id) {
        Optional<Person> person = peopleRepository.findById(id);
        if (person.isPresent()) {
            Hibernate.initialize(person.get().getBooks());

            person.get().getBooks().forEach(book -> {
                long diffInMillies = Math.abs(book.getDateTake().getTime() - new Date().getTime());
                // 864000000 милисекунд = 10 суток
                if (diffInMillies > 864000000)
                    book.setExpired(true); // книга просрочена
            });


            return person.get().getBooks();
        } else {
            return Collections.emptyList();
        }
    }

        public  Optional<Person> getFindByFIO(String fio) {
            return peopleRepository.findByFIO(fio);
        }


    }









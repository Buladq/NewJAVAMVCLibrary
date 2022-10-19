package ru.bul.springs.util;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.bul.springs.models.Person;
import ru.bul.springs.services.PeopleService;

@Component
public class PersonValidator  implements Validator {
    private final PeopleService peopleService;

    @Autowired
    public PersonValidator(PeopleService peopleService) {
        this.peopleService = peopleService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Person.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Person person = (Person) o;

        if (peopleService.getFindByFIO(person.getFIO()).isPresent())
            errors.rejectValue("FIO", "", "Человек с таким ФИО уже существует");
    }
}

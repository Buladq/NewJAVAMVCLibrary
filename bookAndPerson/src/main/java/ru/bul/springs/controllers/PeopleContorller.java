package ru.bul.springs.controllers;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.bul.springs.models.Person;
import ru.bul.springs.services.PeopleService;
import ru.bul.springs.util.PersonValidator;

import javax.validation.Valid;
import java.util.Collections;

@Controller
@RequestMapping("/people")
public class PeopleContorller {

//    private final PersonDAO personDAO;

    private final PeopleService peopleService;
    private final PersonValidator personValidator;

    @Autowired
    public PeopleContorller(PeopleService peopleService, PersonValidator personValidator) {
        this.peopleService = peopleService;

        this.personValidator = personValidator;
    }

    @GetMapping// оставляем пустым
    public String index(Model model){

        model.addAttribute("people",peopleService.findAll());



        return "people/index";
    }

    @GetMapping("/new")
    public String newPerson(@ModelAttribute("person") Person person){

        return "people/new";
    }

    @PostMapping
    public String create(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult){
        personValidator.validate(person,bindingResult);

        if(bindingResult.hasErrors())
            return "people/new";

        peopleService.save(person);
        return "redirect:/people";

    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("person", peopleService.findOne(id));
        return "people/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("person") @Valid Person person,BindingResult bindingResult, @PathVariable("id") int id) {
        personValidator.validate(person,bindingResult);

        if(bindingResult.hasErrors()){
            return "people/edit";
        }

        peopleService.update(id, person);
        return "redirect:/people";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id,Model model){
        model.addAttribute("person",peopleService.findOne(id));


        if(peopleService.getBooksByPersonId(id).size()!=0){
            model.addAttribute("have","Книги:");
            model.addAttribute("books",peopleService.getBooksByPersonId(id));
        }
        else {

            model.addAttribute("have","Человек пока не взял ни одной книги");
        }



        return "people/show";
    }




    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id){
        peopleService.delete(id);
        return "redirect:/people";

    }
}

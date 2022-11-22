package ru.bul.springs.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.bul.springs.models.Book;
import ru.bul.springs.models.Person;
import ru.bul.springs.services.BookService;
import ru.bul.springs.services.PeopleService;

import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/book")
public class BookController {

//    private final BookDAO bookDAO;
//    private final PersonDAO personDAO;

    private final PeopleService peopleService;

    private final BookService bookService;

    @Autowired
    public BookController(PeopleService peopleService, BookService bookService) {
        this.peopleService = peopleService;
        this.bookService = bookService;
    }

    @GetMapping// оставляем пустым
    public String index(Model model, @RequestParam(value = "sort_by_year" ,required = false) boolean sort_by_year,
                        @RequestParam(value = "page",required = false)Integer page,
                        @RequestParam(value = "perPage",required = false)Integer perPage){



        if (sort_by_year &&page!=null&&perPage!=null){
            model.addAttribute("books",bookService.combo(page,perPage));
        }
         else if(page!=null&&perPage!=null){
            model.addAttribute("books",bookService.plagin(page,perPage));
        }

         else if(sort_by_year){
            model.addAttribute("books",bookService.sort());
        }


        else {
             model.addAttribute("books",bookService.findAll());

        }

        return "book/index";
    }


    @GetMapping("/search")
    public String search(){
        return "book/search";
    }

    @PostMapping("/search")
    public String search(Model model,@RequestParam(value = "nameBook")String nameBook){

        model.addAttribute("books", bookService.findByNameBookStartingWith(nameBook));
        return "book/search";
    }








    @GetMapping("/new")
    public String newBook(@ModelAttribute("book") Book book){

        return "book/new";
    }

    @PostMapping
    public String create(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult){


        if(bindingResult.hasErrors())
            return "book/new";

        bookService.save(book);
        return "redirect:/book";

    }



    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("book", bookService.findOne(id));
        return "book/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("book") @Valid Book book,BindingResult bindingResult, @PathVariable("id") int id) {


        if (bindingResult.hasErrors())
            return "book/edit";

        bookService.update(id, book);

        return "redirect:/book";


    }


    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id){
        bookService.delete(id);
        return "redirect:/book";

    }



    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model, @ModelAttribute("person") Person person){
        model.addAttribute("book",bookService.findOne(id));

        Optional<Person> bookhas = Optional.ofNullable(bookService.getOwnerBook(id));


        //проверка есть ли владелец у книги
        if (bookhas.isPresent()){
            model.addAttribute("having", bookhas.get());
        }

        else{
            model.addAttribute("noHaving", peopleService.findAll());

        }

        return "book/show";
    }

    @PatchMapping("/{id}/release")
    public String release(@PathVariable("id") int id) {
    bookService.release(id);
        return "redirect:/book/" + id;
    }

    @PatchMapping("/{id}/assign")
    public String assign(@PathVariable("id") int id, @ModelAttribute("person") Person selectedPerson) {
    bookService.assign(id,selectedPerson);
        return "redirect:/book/" + id;
    }
}

package ru.bul.springs.models;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Table(name = "book")
public class Book {

    @Id
    @Column(name = "id_book")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    @NotEmpty(message = "Нужно ввести название книги")
    @Size(min = 3,max = 50,message = "Название книги от 3 до 50 символов")
   @Column(name = "name_book")
    private String nameBook;
    @NotEmpty(message = "Нужно ввести автора книги: Ф И")
    @Size(min = 3,max = 50,message = "Автор книги от 3 до 50 символов в формате Ф И")
    @Pattern(regexp = "([А-ЯЁ][а-яё]+[\\-\\s]?){2,}",message ="ФИ должно быть такой формы: Ф И (при желании О)" )
    @Column(name = "author_book")
    private String author_book;

    @Min(value=1000,message = "год должен быть меньше 1000")
    @Column(name = "year_book")
    private int yearBook;


    @Column(name = "date_of_take")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateTake;

    @ManyToOne
    @JoinColumn(name = "people_id",referencedColumnName = "id_person")
    private Person owner;


    @Transient
    private boolean expired; //невидимое поле для hibernate





    public Book(String name_book, String author_book, int yearBook) {


        this.nameBook = name_book;
        this.author_book = author_book;
        this.yearBook = yearBook;
    }

    public Book() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id_book) {
        this.id = id_book;
    }

    public String getNameBook() {
        return nameBook;
    }

    public void setNameBook(String name_book) {
        this.nameBook = name_book;
    }

    public String getAuthor_book() {
        return author_book;
    }

    public void setAuthor_book(String author_book) {
        this.author_book = author_book;
    }

    public int getYearBook() {
        return yearBook;
    }

    public void setYearBook(int year_book) {
        this.yearBook = year_book;
    }

    public Person getOwner() {
        return owner;
    }

    public void setOwner(Person owner) {
        this.owner = owner;
    }

    public Date getDateTake() {
        return dateTake;
    }

    public void setDateTake(Date dateTake) {
        this.dateTake = dateTake;
    }

    public boolean isExpired() {
        return expired;
    }

    public void setExpired(boolean expired) {
        this.expired = expired;
    }
}

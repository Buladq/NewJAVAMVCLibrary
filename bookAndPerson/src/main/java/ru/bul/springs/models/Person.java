package ru.bul.springs.models;


import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.List;

@Entity
@Table(name = "person")
public class Person {

    @Id
    @Column(name = "id_person")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
   private int id;

   @NotEmpty(message = "Нужно ввести ФИО!")
   @Size(min = 3,max = 50,message = "ФИО должно быть от 3 до 50 символов")
   @Pattern(regexp = "([А-ЯЁ][а-яё]+[\\-\\s]?){3,}",message ="ФИО должно быть такой формы: Ф И О" )
   @Column(name = "fio")
   private String FIO;

   @Min(value=1,message = "возвраст должен быть больше 1")
   @NotNull(message = "введит возвраст")
   @Column(name = "year_of_birth")
   private int year_of_birth;

   @OneToMany(mappedBy = "owner")
   private List<Book> books;



    public Person(String FIO, int year_of_birtch) {

        this.FIO = FIO;
        this.year_of_birth = year_of_birtch;

    }
    public Person(){}


    public int getId() {
        return id;
    }

    public void setId(int id_person) {
        this.id = id_person;
    }

    public String getFIO() {
        return FIO;
    }

    public void setFIO(String FIO) {
        this.FIO = FIO;
    }

    public int getYear_of_birth() {
        return year_of_birth;
    }

    public void setYear_of_birth(int year_of_birth) {
        this.year_of_birth = year_of_birth;
    }
    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }
}

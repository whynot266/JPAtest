package com.ivt.spring.jpa.demo.main;

import com.ivt.spring.jpa.demo.config.SpringConfig;
import com.ivt.spring.jpa.demo.entity.BookDetailsEntity;
import com.ivt.spring.jpa.demo.entity.BookEntity;
import com.ivt.spring.jpa.demo.entity.CategoryEntity;
import com.ivt.spring.jpa.demo.repository.BookRepository;
import com.ivt.spring.jpa.demo.repository.CategoryRepository;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class Main {
    static AnnotationConfigApplicationContext context= new AnnotationConfigApplicationContext(SpringConfig.class);
    static BookRepository bookRepository= (BookRepository) context.getBean("bookRepository");
    static CategoryRepository categoryRepository= (CategoryRepository) context.getBean("categoryRepository");
    public static void main(String[] args) {
        createNewBookEntryWithNewCategory();



        findByAuthor("TamVo");

        updateBookName(1,"ILoveYou");
    }
    public static BookEntity createNewBook(){
        BookDetailsEntity bookDetailsEntity= new BookDetailsEntity();
        bookDetailsEntity.setIsbn("asdadasd");
        bookDetailsEntity.setNumberOfPage(200);
        bookDetailsEntity.setPrice(99);
        bookDetailsEntity.setPublishDate(LocalDate.parse("2009-01-01"));

        BookEntity bookEntity= new BookEntity();
        bookEntity.setName("TamStory");
        bookEntity.setAuthor("TamVo");
        bookEntity.setBookDetails(bookDetailsEntity);
        bookDetailsEntity.setBook(bookEntity);

        return bookEntity;
    }
    public static void findByAuthor(String author){
        List<BookEntity> bookEntityList= bookRepository.findByAuthor(author);
        if(bookEntityList!=null){
            System.out.println("Find");
            for (BookEntity bookEntity: bookEntityList){
                System.out.println(bookEntity.toString());
            }
        }
    }
    public static void createNewBookEntry(){
        CategoryEntity categoryEntity= new CategoryEntity();
        categoryEntity.setId(1);

        BookEntity bookEntity= createNewBook();
        bookEntity.setCategory(categoryEntity);
        bookRepository.save(bookEntity);
    }
    public static void createNewBookEntryWithNewCategory(){
        CategoryEntity categoryEntity= createNewCategory();
        categoryRepository.save(categoryEntity);

        BookEntity bookEntity= createNewBook();
        bookEntity.setCategory(categoryEntity);
        bookRepository.save(bookEntity);


    }

    public static CategoryEntity createNewCategory(){
        CategoryEntity categoryEntity= new CategoryEntity();
        categoryEntity.setName("IT");
        categoryEntity.setDescription("IT books");
        return  categoryEntity;
    }
    public static void updateBookName(int id, String name){
        Optional<BookEntity> bookEntityOptional= bookRepository.findById(id);
        if (bookEntityOptional!=null){
            BookDetailsEntity bookDetailsEntity= bookEntityOptional.get().getBookDetails();
            bookDetailsEntity.setPublishDate(LocalDate.now());
            bookEntityOptional.get().setName(name);
            bookEntityOptional.get().setBookDetails(bookDetailsEntity);

        }else{
            System.out.println("Not found the book");
        }
        bookRepository.save(bookEntityOptional.get());
        System.out.println(bookEntityOptional.toString());
    }
}

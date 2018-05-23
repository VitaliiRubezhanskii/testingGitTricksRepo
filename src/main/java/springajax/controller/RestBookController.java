package springajax.controller;


import springajax.domain.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springajax.service.BookService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/liba")
public class RestBookController {

    @Autowired
    private BookService bookService;


    @GetMapping("/books")
    public Response getAllBooks(){
        return new Response("Ok",bookService.getAllBooks());
    }


    @GetMapping("/book/{id}")
    public Book getOneBook(@PathVariable int id){
        return bookService.getOneBook(id);
    }


    @PostMapping("/save")
    public Response save(@RequestBody Book book){
        List<Book> bookadded=new ArrayList<>();
        bookadded.add(bookService.save(book));
        return new Response("Ok",bookadded);
    }

    @DeleteMapping("delete/{id}")
    public Book deleteBook(@RequestBody Book book){
        bookService.delete(book);
        return book;


    }


}

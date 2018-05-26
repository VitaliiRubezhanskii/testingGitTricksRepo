package springajax.controller;


import springajax.domain.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springajax.service.BookService;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping("/liba")
public class RestBookController {


    private static final Logger LOGGER = Logger.getLogger( RestBookController.class.getName());

    @Autowired
    private BookService bookService;


    @GetMapping("/books")
    public Response getAllBooks(){
        List<Book> foundAllBooks=bookService.getAllBooks();
        LOGGER.log( Level.INFO, "found books in count of "+ foundAllBooks.size());
        return new Response("Ok",foundAllBooks);
    }


    @GetMapping("/book/{id}")
    public Book getOneBook(@PathVariable int id){
        return bookService.getOneBook(id);
    }


    @PostMapping("/books")
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

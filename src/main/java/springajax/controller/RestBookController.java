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

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/books")
    public List<Book> getAllBooks(){
        List<Book> foundAllBooks=bookService.getAllBooks();
        LOGGER.log( Level.INFO, "found books in count of "+ foundAllBooks.size());
        return foundAllBooks;//new Response("Ok",foundAllBooks);
    }


    @GetMapping("/book/{id}")
    public Book getOneBook(@PathVariable int id){
        return bookService.getOneBook(id);
    }


    @PostMapping(value = "/books",produces = "application/json",consumes = "application/json")
    public Response save(@RequestBody Book book){
        List<Book> bookadded=new ArrayList<>();
        bookadded.add(bookService.save(book));
        return new Response("Ok",bookadded);
    }

    @DeleteMapping("book/{id}")
    public Book deleteBook(@PathVariable int id){
     return bookService.delete(getOneBook(id));



    }


}

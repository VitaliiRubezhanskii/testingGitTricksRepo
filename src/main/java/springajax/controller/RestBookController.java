package springajax.controller;


import org.hibernate.validator.internal.engine.ConstraintViolationImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import springajax.domain.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springajax.exception.BookNotFoundException;
import springajax.service.BookService;
import springajax.service.helper.PageParameter;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping("/liba")
public class RestBookController {


    private static final Logger LOGGER = Logger.getLogger(RestBookController.class.getName());

    @Autowired
    private BookService bookService;

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/books")
    public ResponseEntity<List<Book>> getAllBooks(){
        List<Book> foundAllBooks=bookService.getAllBooks();
        LOGGER.log( Level.INFO, "found books in count of "+ foundAllBooks.size());
        return foundAllBooks.size()==0?
                new ResponseEntity<>(foundAllBooks,HttpStatus.NO_CONTENT):
                new ResponseEntity<>(foundAllBooks,HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/books/{id}")
    public ResponseEntity<Book> getOneBook(@PathVariable int id){
        Book oneBook = bookService.getOneBook(id);
        return oneBook==null?
                new ResponseEntity<>(oneBook,HttpStatus.NO_CONTENT):
                new ResponseEntity<>(oneBook,HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/books/{page}/{size}")
    public ResponseEntity<List<Book>> getAllBooksByPage(@PathVariable("page") int page,
                                                        @PathVariable("size") int size){
      List<Book> foundBook= bookService.getAllPaginatedBooks(page,size);
        return new ResponseEntity<>(foundBook,HttpStatus.OK);

    }

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping(value = "/books",produces = "application/json",consumes = "application/json")
    public Response save(@RequestBody Book book){
        List<Book> bookadded=new ArrayList<>();
        if (book.getId()==9) throw new BookNotFoundException("No available books");
        bookadded.add(bookService.save(book));
        return new Response("Ok",bookadded);
    }

    @DeleteMapping("book/{id}")
    public ResponseEntity<Book> deleteBook(@PathVariable int id){
     return new ResponseEntity<>(bookService.delete(bookService.getOneBook(id)),HttpStatus.OK);



    }


}

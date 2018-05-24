package springajax.service;

import springajax.domain.Book;

import java.util.List;

public interface BookService {

    Book save(Book book);
    Book delete(Book book);
    Book getOneBook(int id);
    List<Book> getAllBooks();


}

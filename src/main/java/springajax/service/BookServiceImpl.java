package springajax.service;

import springajax.domain.Book;
import springajax.localstorage.BookCustomStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookCustomStorage bookCustomStorage;

    @Override
    public Book save(Book book) {
         bookCustomStorage.setBook(book);
         return book;
    }

    @Override
    public Book update(Book book) {
        return null;
    }

    @Override
    public Book delete(Book book) {
         bookCustomStorage.deleteBook(book);
         return book;
    }

    @Override
    public Book getOneBook(int id) {
        Book foundBook=null;
        List<Book> books= bookCustomStorage.getBookList();

        for (Book book:books){
                 if (book.getId()==id){
                     foundBook=book;
                 }
        }
             return foundBook;

    }

    @Override
    public List<Book> getAllBooks() {
        return bookCustomStorage.getBookList();
    }
}

package springajax.service;

import springajax.domain.Book;
import springajax.localstorage.BookCustomStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;

    @Override
    public Book save(Book book) {

        return  bookRepository.save(book);
    }

    @Override
    public Book delete(Book book) {
         bookRepository.delete(book);
         return book;
    }

    @Override
    public Book getOneBook(int id) {
        return bookRepository.findOne(id);
     }

    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }
}

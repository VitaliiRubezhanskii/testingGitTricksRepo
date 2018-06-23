package springajax.service;

import org.springframework.data.domain.PageRequest;
import springajax.domain.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springajax.service.helper.PageParameter;

import java.awt.print.Pageable;
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

    public List<Book> getAllPaginatedBooks(int page,int size) {

        int sizeOfList=bookRepository.findAll().size();
        return bookRepository.findAll(new PageRequest(--page,size)).getContent();
    }


}

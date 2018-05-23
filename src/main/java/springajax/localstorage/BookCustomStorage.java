package springajax.localstorage;

import springajax.domain.Book;

import java.util.ArrayList;
import java.util.List;

public class BookCustomStorage {

     List<Book> bookList=new ArrayList<>();
    private Book book;



    public BookCustomStorage() {
        //initial data
        Book book1=new Book(1,"Rest","John Doe","isbn1","programming");
        Book book2=new Book(2,"Hibernate","John Smith","isbn2","programming");
        bookList.add(book1);
        bookList.add(book2);
    }

    public List<Book> getBookList() {
        return bookList;
    }

    public void setBook(Book book) {
        bookList.add(book);
    }

    public void setBookList(List<Book> books) {
        bookList.addAll(books);
    }

    public void deleteBook(Book book) {
        bookList.remove(book);
    }
}

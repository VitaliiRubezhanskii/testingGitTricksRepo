package springajax.controller;

import springajax.domain.Book;

import java.util.List;

public class Response {

    private String status;
   private List<Book> books;

    public Response() {
    }

    public Response(String status, List<Book> books) {
        this.status = status;
        this.books = books;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }
}

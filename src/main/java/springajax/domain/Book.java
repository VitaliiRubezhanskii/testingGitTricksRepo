package springajax.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name="book")
public class Book {

    private int id;
    private String bookTitle;

    private String isbn;
    private String genre;
    private Set<Author> authors;
    private Category category;

    public Book() {
    }



    public Book(int id, String bookTitle,
                String isbn, String genre, Set<Author> authors,
                Category category) {
        this.id = id;
        this.bookTitle = bookTitle;
        this.isbn = isbn;
        this.genre = genre;
        this.authors = authors;
        this.category = category;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "book_id")
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }


    @Column(name = "title")
    public String getBookTitle() {
        return bookTitle;
    }
    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }


    @Column(name = "isbn")
    public String getIsbn() {
        return isbn;
    }
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    @Column(name = "genre")
    public String getGenre() {
        return genre;
    }
    public void setGenre(String genre) {
        this.genre = genre;
    }

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "book_author",  joinColumns = {
            @JoinColumn(name = "book_id", nullable = false, updatable = false) },
            inverseJoinColumns = { @JoinColumn(name = "author_id",
                    nullable = false, updatable = false) })
    public Set<Author> getAuthors() {
        return authors;
    }
    public void setAuthors(Set<Author> authors) {
        this.authors = authors;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "books_book_id",updatable = false,insertable = false)
    public Category getCategory() {
        return category;
    }
    public void setCategory(Category category) {
        this.category = category;
    }
}

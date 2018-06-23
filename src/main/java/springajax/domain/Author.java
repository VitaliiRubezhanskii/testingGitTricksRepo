package springajax.domain;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "author")
public class Author {

    private int id;
    private String author;
    private Set<Book> books;

    public Author() {
    }

    public Author(int id, String author, Set<Book> books) {
        this.id = id;
        this.author = author;
        this.books = books;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "author_id")
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "author")
    public String getAuthor() {
        return author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "authors")
    public Set<Book> getBooks() {
        return books;
    }
    public void setBooks(Set<Book> books) {
        this.books = books;
    }
}

package springajax.domain;


import javax.persistence.*;

@Entity
@Table(name="book")
public class Book {

    private int id;
    private String bookTitle;
    private String author;
    private String isbn;
    private String genre;

    public Book() {
    }


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public Book(int id, String bookTitle, String author, String isbn, String genre) {
        this.id = id;
        this.bookTitle = bookTitle;
        this.author = author;
        this.isbn = isbn;
        this.genre = genre;
    }
    @Column(name = "title")
    public String getBookTitle() {
        return bookTitle;
    }
    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }


    @Column(name="author")
    public String getAuthor() {
        return author;
    }
    public void setAuthor(String author) {
        this.author = author;
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
}

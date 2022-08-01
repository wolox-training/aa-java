package wolox.training.models;

import com.sun.istack.NotNull;
import wolox.training.exceptions.BookAlreadyOwnedException;
import wolox.training.exceptions.BookNotFoundException;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The user object contains main details about a User.
 */
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(nullable = false, unique = true)
    public String username;

    @NotNull
    @Column(nullable = false, unique = true)
    public String name;

    @NotNull
    @Column(nullable = false, unique = true)
    public LocalDate birthdate;

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    public List<Book> books;


    public User(String username, String name, LocalDate birthdate) {
        this.username = username;
        this.name = name;
        this.birthdate = birthdate;
        this.books = new ArrayList<>();
    }

    public User() {
    }

    public long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    /**
     * Brings a list of books
     * @return list of books that results unmodifiable.
     */
    public List<Book> getBooks() {
        return Collections.unmodifiableList(books);
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    /**
     * Add a book to the list of books of the user
     * @param book: The details of the new book (Book)
     * @throws BookAlreadyOwnedException if the book is already on the list of the user
     */
    public void addBook(Book book) {
        if (!books.contains(book)) {
            books.add(book);
        } else {
            throw new BookAlreadyOwnedException();
        }
    }

    /**
     * Remove a book to the list of books of the user
     * @param book: The details of the book to be removed (Book)
     * @throws BookNotFoundException if the book is not found on the list it can't be removed
     */
    public void removeBook(Book book) {
        if (books.contains(book)) {
            books.remove(book);
        } else {
            throw new BookNotFoundException();
        }
    }
}

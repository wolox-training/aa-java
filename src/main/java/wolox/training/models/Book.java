package wolox.training.models;

import com.google.common.base.Preconditions;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * The book object contains main details about a Book.
 */
@ApiModel(description = "Books from the data base")
@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ApiModelProperty(notes = "The book title", required = true)
    @Column(nullable = false, unique = true)
    private String title;

    @ApiModelProperty(notes = "Short description about the book", required = true)
    @Column(nullable = false)
    private String subtitle;

    @ApiModelProperty(notes = "Full name of the author of the book", required = true)
    @Column(nullable = false)
    private String author;

    @ApiModelProperty(notes = "Genre of the book", required = true)
    @Column(nullable = false)
    private String genre;

    @ApiModelProperty(notes = "Front page image of the book", required = true)
    @Column(nullable = false)
    private String image;

    @ApiModelProperty(notes = "The company in charge of the book selling", required = true)
    @Column(nullable = false)
    private String publisher;

    @ApiModelProperty(notes = "The year were the book was published", required = true)
    @Column(nullable = false)
    private String year;

    @ApiModelProperty(notes = "The amount of pages the book contains", required = true)
    @Column(nullable = false)
    private Integer pages;

    @ApiModelProperty(notes = "Product identifier", required = true)
    @Column(nullable = false)
    private String isbn;

    @ManyToMany(mappedBy = "books")
    public List<User> users;

    public Book(String title, String subtitle, String author, String genre, String image, String publisher, String year, Integer pages, String isbn) {
        this.title = title;
        this.subtitle = subtitle;
        this.author = author;
        this.genre = genre;
        this.image = image;
        this.publisher = publisher;
        this.year = year;
        this.pages = pages;
        this.isbn = isbn;
        this.users = new ArrayList<>();
    }

    public Book() {
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = checkNotNull(title);
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = checkNotNull(subtitle);
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        Preconditions.checkArgument(!author.isEmpty(), "The author name cannot be empty");
        this.author = author;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        Preconditions.checkArgument(genre.length() > 1, "The genre field must have more than 1 character");
        this.genre = checkNotNull(genre);
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = checkNotNull(image);
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        Preconditions.checkArgument(!publisher.isEmpty(), "The publisher name cannot be empty");
        this.publisher = publisher;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        Preconditions.checkArgument(year.matches("[0-9]+"), "The year field must contain only numbers");
        Preconditions.checkArgument(Integer.valueOf(year) <= LocalDateTime.now().getYear(),
                "The year field must be less than or equal to the current year");
        this.year = checkNotNull(year);
    }

    public Integer getPages() {
        return pages;
    }

    public void setPages(Integer pages) {
        Preconditions.checkArgument(pages > 1, "The pages must be more than 1");
        this.pages = checkNotNull(pages);
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        Preconditions.checkArgument(isbn.matches("[0-9]+"), "The isbn field must contain only numbers");
        this.isbn = checkNotNull(isbn);
    }

    public List<User> getUsers() {
        return (List<User>) Collections.unmodifiableList(users);
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}

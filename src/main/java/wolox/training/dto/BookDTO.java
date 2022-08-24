package wolox.training.dto;

import java.util.ArrayList;
import java.util.List;

public class BookDTO {

    private String title;
    private String subtitle;
    private List<String> publishers;
    private String publishDate;
    private Integer numberOfPages;
    private List<String> authors;
    private String isbn;

    public BookDTO() {
    }

    public BookDTO(String title, String subtitle, String publishDate, Integer numberOfPages, String isbn) {
        this.title = title;
        this.subtitle = subtitle;
        this.publishers = new ArrayList<>();
        this.publishDate = publishDate;
        this.numberOfPages = numberOfPages;
        this.authors = new ArrayList<>();
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public List<String> getPublishers() {
        return publishers;
    }

    public void setPublishers(List<String> publishers) {
        this.publishers = publishers;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    public Integer getNumberOfPages() {
        return numberOfPages;
    }

    public void setNumberOfPages(Integer numberOfPages) {
        this.numberOfPages = numberOfPages;
    }

    public List<String> getAuthors() {
        return authors;
    }

    public void setAuthors(List<String> authors) {
        this.authors = authors;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    @Override
    public String toString() {
        return "BookDTO{" +
                "title='" + title + '\'' +
                ", subtitle='" + subtitle + '\'' +
                ", publishers=" + publishers +
                ", publishDate='" + publishDate + '\'' +
                ", numberOfPages=" + numberOfPages +
                ", authors=" + authors +
                ", isbn='" + isbn + '\'' +
                '}';
    }
}

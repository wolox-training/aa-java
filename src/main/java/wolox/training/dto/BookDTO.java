package wolox.training.dto;

import java.util.ArrayList;
import java.util.List;

public class BookDTO {
    private String isbn;
    private String title;
    private String subtitle;
    private List<String> publishers;
    private String publishDate;
    private Integer numberPages;
    private List<String> authors;

    public BookDTO() {
    }

    public BookDTO(String isbn, String title, String subtitle, String publishDate, Integer numberPages) {
        this.isbn = isbn;
        this.title = title;
        this.subtitle = subtitle;
        this.publishers = new ArrayList<>();
        this.publishDate = publishDate;
        this.numberPages = numberPages;
        this.authors = new ArrayList<>();
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
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

    public Integer getNumberPages() {
        return numberPages;
    }

    public void setNumberPages(Integer numberPages) {
        this.numberPages = numberPages;
    }

    public List<String> getAuthors() {
        return authors;
    }

    public void setAuthors(List<String> authors) {
        this.authors = authors;
    }
}

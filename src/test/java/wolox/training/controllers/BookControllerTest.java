package wolox.training.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import wolox.training.models.Book;
import wolox.training.repositories.BookRepository;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(BookController.class)
class BookControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private BookRepository mockBookRepository;
    private Book bookTest;

    @BeforeEach
    void setUp() {
        bookTest = new Book();
        bookTest.setGenre("Psychological Horror");
        bookTest.setAuthor("Stephen King");
        bookTest.setImage("https://i.pinimg.com/originals/92/01/66/920166cd7d561d16dd28e508061d2d6e.jpg");
        bookTest.setTitle("Misery");
        bookTest.setSubtitle("-");
        bookTest.setPublisher("Viking Press");
        bookTest.setYear("1987");
        bookTest.setPages(420);
        bookTest.setIsbn("09780582402751");
    }
}
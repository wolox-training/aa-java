package wolox.training.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import wolox.training.models.Book;
import wolox.training.repositories.BookRepository;
import wolox.training.repositories.UserRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(BookController.class)
class BookControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private BookRepository bookRepository;

    @MockBean
    private UserRepository userRepository;
    private Book bookTest;
    private List<Book> listBooks = new ArrayList<>();
    private final ObjectMapper mapper = new ObjectMapper();

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
        bookTest.setIsbn("9780582402751");

        listBooks.add(bookTest);
    }

    @Test
    void givenBooks_whenGetBooks_thenReturnJsonArray() throws Exception {
        String jsonBooks = mapper.writeValueAsString(listBooks);

        given(bookRepository.findAll()).willReturn(listBooks);

        String result = mvc.perform(MockMvcRequestBuilders.get("/api/books")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        assertEquals(jsonBooks, result);
    }
}
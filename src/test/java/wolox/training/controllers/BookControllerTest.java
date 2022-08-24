package wolox.training.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import wolox.training.models.Book;
import wolox.training.repositories.BookRepository;
import wolox.training.repositories.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
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

    public static final String API_BOOKS = "/api/books/";

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
    void whenGetBooks_thenReturnBooksList() throws Exception {
        String expected = mapper.writeValueAsString(listBooks);

        given(bookRepository.findAll()).willReturn(listBooks);

        String result = mvc.perform(MockMvcRequestBuilders.get(API_BOOKS)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        assertEquals(expected, result);
    }

    @Test
    void whenFindByTitleWhichExist_thenBookIsReturned() throws Exception {
        String expected = mapper.writeValueAsString(bookTest);
        Mockito.when(bookRepository.findByTitle(bookTest.getTitle())).thenReturn(Optional.of(bookTest));

        String result = mvc.perform(MockMvcRequestBuilders.get(API_BOOKS.concat("title/").concat(bookTest.getTitle()))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        assertEquals(expected, result);
    }

    @Test
    void whenFindByIdWhichExist_thenBookIsReturned() throws Exception {
        String expected = mapper.writeValueAsString(bookTest);
        Mockito.when(bookRepository.findById(Long.valueOf(1))).thenReturn(Optional.of(bookTest));

        String result = mvc.perform(MockMvcRequestBuilders.get(API_BOOKS.concat(String.valueOf(Long.valueOf(1))))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        assertEquals(expected, result);
    }

    @Test
    void whenCreateBook_thenBookIsReturned() throws Exception {
        String expected = mapper.writeValueAsString(bookTest);
        Mockito.when(bookRepository.save(any())).thenReturn(bookTest);

        String result = mvc.perform(MockMvcRequestBuilders.post(API_BOOKS)
                .content(mapper.writeValueAsString(bookTest))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        assertEquals(expected, result);
    }

    @Test
    void whenDeleteBook_thenSuccessDeleteReturned() throws Exception {
        Mockito.when(bookRepository.findById(Long.valueOf(1))).thenReturn(Optional.of(bookTest));

        mvc.perform(MockMvcRequestBuilders.delete(API_BOOKS.concat(String.valueOf(Long.valueOf(1))))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void whenUpdateBook_thenBookIsReturned() throws Exception {
        String expected = mapper.writeValueAsString(bookTest);
        String actual = "{\"id\":1,\"title\":\"Misery\",\"subtitle\":\"-\",\"author\":\"Stephen King\",\"genre\":\"Psychological Horror\",\"image\":\"https://i.pinimg.com/originals/92/01/66/920166cd7d561d16dd28e508061d2d6e.jpg\",\"publisher\":\"Viking Press\",\"year\":\"1987\",\"pages\":420,\"isbn\":\"9780582402751\"}\n";

        Mockito.when(bookRepository.findById(Long.valueOf(1))).thenReturn(Optional.of(bookTest));
        Mockito.when(bookRepository.save(any())).thenReturn(bookTest);

        String result = mvc.perform(MockMvcRequestBuilders.put(API_BOOKS.concat(String.valueOf(Long.valueOf(1))))
                .content(actual)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        assertEquals(expected, result);
    }
}
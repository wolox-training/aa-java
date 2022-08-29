package wolox.training.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import wolox.training.models.User;
import wolox.training.repositories.BookRepository;
import wolox.training.repositories.UserRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(value = UserController.class, excludeAutoConfiguration = {SecurityAutoConfiguration.class})
class UserControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private BookRepository bookRepository;

    private User userTest;
    private List<User> listUsers = new ArrayList<>();
    private final ObjectMapper mapper = new ObjectMapper();

    public static final String API_USERS = "/api/users/";

    @BeforeEach
    void SetUp() {
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        userTest = new User();
        userTest.setUsername("Jack");
        userTest.setName("Black");
        userTest.setPassword("123456");
        userTest.setBirthdate(LocalDate.parse("1996-05-10"));

        listUsers.add(userTest);
    }

    @Test
    void whenGetUsers_thenReturnUsersList() throws Exception {
        String expected = mapper.writeValueAsString(listUsers);

        given(userRepository.findAll()).willReturn(listUsers);

        String result = mvc.perform(MockMvcRequestBuilders.get(API_USERS)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        assertEquals(expected, result);
    }

    @Test
    void whenFindByIdWhichExist_thenUserIsReturned() throws Exception {
        String expected = mapper.writeValueAsString(userTest);
        Mockito.when(userRepository.findById(Long.valueOf(1))).thenReturn(Optional.of(userTest));

        String result = mvc.perform(MockMvcRequestBuilders.get(API_USERS.concat(String.valueOf(Long.valueOf(1))))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        assertEquals(expected, result);
    }

    @Test
    void whenCreateUser_thenUserIsReturned() throws Exception {
        String expected = mapper.writeValueAsString(userTest);
        Mockito.when(userRepository.save(any())).thenReturn(userTest);

        String result = mvc.perform(MockMvcRequestBuilders.post(API_USERS)
                .content(mapper.writeValueAsString(userTest))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();
        System.out.println(result);

        assertEquals(expected, result);
    }

    @Test
    void whenDeleteBook_thenSuccessDeleteReturned() throws Exception {
        Mockito.when(userRepository.findById(Long.valueOf(1))).thenReturn(Optional.of(userTest));

        mvc.perform(MockMvcRequestBuilders.delete(API_USERS.concat(String.valueOf(Long.valueOf(1))))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void whenUpdateUser_thenUserIsReturned() throws Exception {
        String expected = mapper.writeValueAsString(userTest);
        String actual = "{\"id\":1,\"username\":\"Jack\",\"name\":\"Black\",\"birthdate\":\"1996-05-10\",\"books\":[]}";

        Mockito.when(userRepository.findById(Long.valueOf(1))).thenReturn(Optional.of(userTest));
        Mockito.when(userRepository.save(any())).thenReturn(userTest);

        String result = mvc.perform(MockMvcRequestBuilders.put(API_USERS.concat(String.valueOf(Long.valueOf(1))))
                .content(actual)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        assertEquals(expected, result);
    }

    @Test
    void whenAddBookToList_thenThrowNotFoundBookException() throws Exception {
        Mockito.when(userRepository.findById(Long.valueOf(1))).thenReturn(Optional.of(userTest));
        Mockito.when(bookRepository.findById(Long.valueOf(1))).thenReturn(Optional.empty());

        mvc.perform(MockMvcRequestBuilders.post(API_USERS.concat(String.valueOf(Long.valueOf(1)).concat("/books/").concat(String.valueOf(Long.valueOf(1)))))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void whenRemoveBookToList_thenThrowNotFoundBookException() throws Exception {
        Mockito.when(userRepository.findById(Long.valueOf(1))).thenReturn(Optional.of(userTest));
        Mockito.when(userRepository.save(any())).thenReturn(userTest);
        Mockito.when(bookRepository.findById(Long.valueOf(1))).thenReturn(Optional.empty());

        mvc.perform(MockMvcRequestBuilders.delete(API_USERS.concat(String.valueOf(Long.valueOf(1)).concat("/books/").concat(String.valueOf(Long.valueOf(1)))))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
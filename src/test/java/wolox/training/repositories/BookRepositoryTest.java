package wolox.training.repositories;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import wolox.training.models.Book;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class BookRepositoryTest {
    @Autowired
    private BookRepository bookRepository;

    @Test
    public void whenFindFirstByAuthor_thenReturnSaveSuccess() {
        Optional<Book> author = bookRepository.findFirstByAuthor("Stephen King");

        assertEquals("Stephen King", author.get().getAuthor());
    }

    @Test
    public void whenFindByTitle_thenNoBookReturn(){
        Optional<Book> book = bookRepository.findByTitle("Misery");

        assertTrue(book.isPresent());
    }

    @Test
    public void whenFindById_thenNoBookReturn(){
        Optional<Book> book = bookRepository.findById(Long.valueOf(1));

        assertFalse(book.isPresent());
    }
}
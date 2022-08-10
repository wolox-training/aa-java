package wolox.training.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import wolox.training.repositories.BookRepository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@DataJpaTest
class BookTest {
    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private BookRepository bookRepository;
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
        bookTest.setIsbn("0-9780-5824-0275-1");

        entityManager.persist(bookTest);
        entityManager.flush();
    }

    @Test
    public void whenCreateBook_thenBookIsPersisted() {
        Book bookPersisted = bookRepository.findByTitle("Misery").orElse(new Book());

        assertThat(bookPersisted.getAuthor().equals(bookTest.getAuthor())).isTrue();
        assertThat(bookPersisted.getPublisher().equals(bookTest.getPublisher())).isTrue();
        assertThat(bookPersisted.getGenre().equals(bookTest.getGenre())).isTrue();
    }
}
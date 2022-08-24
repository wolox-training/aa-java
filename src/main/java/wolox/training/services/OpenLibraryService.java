package wolox.training.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import wolox.training.dto.BookDTO;
import wolox.training.exceptions.BookNotFoundException;
import wolox.training.models.Book;
import wolox.training.repositories.BookRepository;

import java.io.IOException;
import java.util.Collections;
import java.util.Optional;

@Service
public class OpenLibraryService {

    @Autowired
    private BookRepository bookRepository;

    private ObjectMapper mapper;

    private static final String API_BOOKS = "https://openlibrary.org/api/books";

    public OpenLibraryService() {
    }

    public BookDTO buildDTO(JsonNode jsonNode, String isbn) {
        BookDTO book = new BookDTO();
        book.setTitle(jsonNode.path("title").asText());
        book.setSubtitle("No subtitle");
        book.setAuthors(Collections.singletonList(jsonNode.path("authors").findPath("name").asText()));
        book.setPublishDate(jsonNode.path("publish_date").asText());
        book.setPublishers(Collections.singletonList(jsonNode.path("publishers").findPath("name").asText()));
        book.setNumberOfPages(Integer.parseInt(jsonNode.path("number_of_pages").asText()));
        book.setIsbn(isbn);

        return book;
    }

    public BookDTO bookInfo(String isbn) throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();
        String url = API_BOOKS + String.format("?bibkeys=ISBN:" + isbn + "&format=json&jscmd=data");

        ResponseEntity<BookDTO> response = restTemplate.getForEntity(url, BookDTO.class);
        JsonNode result = mapper.readTree(String.valueOf(response.getBody()));

        if (result.isEmpty()) {
            throw new BookNotFoundException();
        }

        return buildDTO(result.path("ISBN:" + isbn), isbn);
    }

    public ResponseEntity<Book> searchBook(String isbn) throws IOException {

        Optional<Book> book = bookRepository.findByIsbn(isbn);

        if (book.isPresent()) {
            return new ResponseEntity<Book>(book.get(), HttpStatus.OK);
        }

        BookDTO bookDto = bookInfo(isbn);

        if (bookDto == null) {
            throw new BookNotFoundException();
        }

        Book newBook = new Book(bookDto.getTitle(), bookDto.getSubtitle(), String.valueOf(bookDto.getAuthors()),
                "No genre",
                "No image",
                String.valueOf(bookDto.getPublishers()),
                bookDto.getPublishDate(),
                bookDto.getNumberOfPages(),
                bookDto.getIsbn());
        bookRepository.save(newBook);

        return new ResponseEntity<Book>(newBook, HttpStatus.CREATED);
    }
}

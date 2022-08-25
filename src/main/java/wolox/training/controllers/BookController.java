package wolox.training.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import wolox.training.exceptions.BookIdMismatchException;
import wolox.training.exceptions.BookNotFoundException;
import wolox.training.models.Book;
import wolox.training.repositories.BookRepository;

import java.util.Optional;

@RestController
@RequestMapping("/api/books")
@Api(tags = "Books")
public class BookController {

    @Autowired
    BookRepository bookRepository;

    /**
     * This method update a Book with the following attributes:
     * @param name: Optional name to show in the greeting view (String)
     * @param model: Supply attributes used for rendering views (Model)
     * @return greeting phrase in the web view
     */
    @GetMapping("/greeting")
    public String greeting(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {
        model.addAttribute("name", name);
        return "greeting";
    }

    /**
     * This method brings all books saved.
     * @return a list of books
     */
    @GetMapping
    @ApiOperation(value = "Returns all books that respond to a filter", response = Book[].class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 401, message = "Not Authorized"),
            @ApiResponse(code = 403, message = "Access forbidden"),
            @ApiResponse(code = 404, message = "Book not found")
    })
    public Iterable findAll() {
        return bookRepository.findAll();
    }

    /**
     * This method searches for a Book with the following attributes:
     * @param title: Title related to the book required (String)
     * @return book searched with the attributes passed as parameters
     */
    @GetMapping("/title/{title}")
    @ApiOperation(value = "Returns one book according to its tittle", response = Book.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 401, message = "Not Authorized"),
            @ApiResponse(code = 403, message = "Access forbidden"),
            @ApiResponse(code = 404, message = "Book Not Found"),
    })
    public Optional<Book> findBookByTitle(@PathVariable String title) {
        return bookRepository.findByTitle(title);
    }

    /**
     * This method searches for a Book with the following attributes:
     * @param id: Id related to the book required (Long)
     * @return book searched with the attributes passed as parameters
     * @throws BookNotFoundException if the repository can't find the object
     */
    @GetMapping("/{id}")
    @ApiOperation(value = "Returns one book according to its id", response = Book.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 401, message = "Not Authorized"),
            @ApiResponse(code = 403, message = "Access forbidden"),
            @ApiResponse(code = 404, message = "Book Not Found"),
    })
    public Book findBookById(@PathVariable Long id) {
        return bookRepository.findById(id)
                .orElseThrow(BookNotFoundException::new);
    }

    /**
     * Creates a new book
     * @param book: The details of the new book (Book)
     * @return the book details after creating
     */
    @PostMapping
    @ApiOperation(value = "Creates a book", response = Book.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 401, message = "Not Authorized"),
            @ApiResponse(code = 403, message = "Access forbidden"),
            @ApiResponse(code = 404, message = "Book Not Found"),
    })
    @ResponseStatus(HttpStatus.CREATED)
    public Book createBook(@RequestBody Book book) {
        return bookRepository.save(book);
    }

    /**
     * This method searches a book then deletes it.
     * @param id: Id related to the book required (Long)
     * @throws BookNotFoundException if the repository can't find the object
     */
    @DeleteMapping("/{id}")
    @ApiOperation(value = "Deletes a book from db according to its id", response = Book.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 401, message = "Not Authorized"),
            @ApiResponse(code = 403, message = "Access forbidden"),
            @ApiResponse(code = 404, message = "Book Not Found"),
    })
    public void deleteBook(@PathVariable Long id) {
        bookRepository.findById(id)
                .orElseThrow(BookNotFoundException::new);
        bookRepository.deleteById(id);
    }

    /**
     * This method update a Book with the following attributes:
     * @param id: Id related to the book required (Long)
     * @param book: The details of the new book (Book)
     * @throws BookIdMismatchException if the method can`t find the id that matches the book updated
     * @throws BookNotFoundException if the repository can't find the object
     * @return book updated with the attributes passed as parameters
     */
    @PutMapping("/{id}")
    @ApiOperation(value = "Updates a book", response = Book.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 401, message = "Not Authorized"),
            @ApiResponse(code = 403, message = "Access forbidden"),
            @ApiResponse(code = 404, message = "Book Not Found"),
    })
    public Book updateBook(@RequestBody Book book, @PathVariable Long id) {
        if (book.getId() != id) {
            throw new BookIdMismatchException();
        }
        bookRepository.findById(id)
                .orElseThrow(BookNotFoundException::new);
        return bookRepository.save(book);
    }
}

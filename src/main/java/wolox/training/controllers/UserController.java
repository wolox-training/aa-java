package wolox.training.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import wolox.training.exceptions.BookNotFoundException;
import wolox.training.exceptions.UserIdMismatchException;
import wolox.training.exceptions.UserNotFoundException;
import wolox.training.models.Book;
import wolox.training.models.User;
import wolox.training.repositories.BookRepository;
import wolox.training.repositories.UserRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    UserRepository userRepository;
    @Autowired
    BookRepository bookRepository;

    /**
     * This method brings all users saved.
     * @return a list of users
     */
    @GetMapping
    public Iterable findAll() {
        return userRepository.findAll();
    }

    /**
     * This method searches for a User with the following attributes:
     * @param id: Id related to the user required (Long)
     * @return user searched with the attributes passed as parameters
     * @throws UserNotFoundException if the repository can't find the object
     */
    @GetMapping("/{id}")
    public User findUserById(@PathVariable Long id) {
        return userRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);
    }

    /**
     * Creates a new user
     * @param user: The details of the new user (User)
     * @return the user details after creating
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User createUser(@RequestBody User user) {
        return userRepository.save(user);
    }

    /**
     * This method searches a user then deletes it.
     * @param id: Id related to the user required (Long)
     * @throws UserNotFoundException if the repository can't find the object
     */
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);
        userRepository.deleteById(id);
    }

    /**
     * This method update a User with the following attributes:
     * @param id: Id related to the user required (Long)
     * @param user: The details of the new user (User)
     * @throws UserIdMismatchException if the method can`t find the id that matches the user updated
     * @throws UserNotFoundException if the repository can't find the object
     * @return user updated with the attributes passed as parameters
     */
    @PutMapping("/{id}")
    public User updateUser(@RequestBody User user, @PathVariable Long id) {
        if (user.getId() != id) {
            throw new UserIdMismatchException();
        }
        userRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);
        return userRepository.save(user);
    }


    /**
     * Methods related to the list of books associated to each user
     */

    /**
     * This method add a book to the list of the user with the following attributes:
     * @param userId: Id related to the user required (Long)
     * @param bookId: Id related to the book required (Long)
     * @throws UserNotFoundException if the repository can't find the object
     * @throws BookNotFoundException if the repository can't find the object
     * @return list of books updated
     */
    @PostMapping("/{userId}/books/{bookId}")
    public List<Book> addBook(@PathVariable Long userId, @PathVariable Long bookId) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        Book book = bookRepository.findById(bookId).orElseThrow(BookNotFoundException::new);
        user.addBook(book);
        userRepository.save(user);
        return user.getBooks();
    }

    /**
     * This method deletes a book to the list of the user with the following attributes:
     * @param userId: Id related to the user required (Long)
     * @param bookId: Id related to the book required (Long)
     * @throws UserNotFoundException if the repository can't find the object
     * @throws BookNotFoundException if the repository can't find the object
     * @return list of books updated
     */

    @DeleteMapping("/{userId}/books/{bookId}")
    public List<Book> removeBook(@PathVariable Long userId, @PathVariable Long bookId) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        Book book = bookRepository.findById(bookId).orElseThrow(BookNotFoundException::new);
        user.removeBook(book);
        userRepository.save(user);
        return user.getBooks();
    }
}

package wolox.training.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import wolox.training.models.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    @Query(value="SELECT * FROM Book ORDER BY anyField DESC LIMIT 1", nativeQuery = true)
    Book findBookByAuthor(String author);
}

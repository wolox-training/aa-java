package wolox.training.repositories;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import wolox.training.models.User;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void whenFindFirstByName_thenReturnSaveSuccess() {
        Optional<User> name = userRepository.findFirstByName("Alexa");

        assertEquals("Alexa", name.get().getName());
    }

    @Test
    public void whenFindById_thenNoBookReturn(){
        Optional<User> user = userRepository.findById(Long.valueOf(1));
        assertTrue(user.isPresent());
    }
}
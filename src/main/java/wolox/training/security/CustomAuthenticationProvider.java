package wolox.training.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;
import wolox.training.models.User;
import wolox.training.repositories.UserRepository;

import java.util.ArrayList;
import java.util.Optional;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    public UserRepository userRepository;


    @Override
    public Authentication authenticate (Authentication authentication) throws AuthenticationException {
        String name = authentication.getName();
        Optional<User> user = userRepository.findByUsername(name);
        String password = authentication.getCredentials().toString();

        if (user.isPresent() && BCrypt.checkpw(password, user.get().getPassword())) {
            return new UsernamePasswordAuthenticationToken(
                    user.get().getUsername(), password, new ArrayList<>());
        } else {
            throw new BadCredentialsException("Wrong user or password.");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}

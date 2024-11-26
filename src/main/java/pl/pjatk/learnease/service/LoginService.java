package pl.pjatk.learnease.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import pl.pjatk.learnease.configure.exception.BusinessException;
import pl.pjatk.learnease.entity.user.Role;
import pl.pjatk.learnease.entity.user.User;
import pl.pjatk.learnease.entity.user.dto.RequestedLogin;
import pl.pjatk.learnease.repository.UserRepository;

import java.time.LocalDate;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final UserRepository userRepository;

    public void createLoginWithDefaultAuthority(RequestedLogin login) {
        Optional<User> foundUser = userRepository.findUserByUsername(login.username());
        if (foundUser.isEmpty()) {
            User createdUser = createUser(login);
            userRepository.save(createdUser);
        } else throw new BusinessException("Cannot create login!");
    }

    private User createUser(RequestedLogin login) {
        User newUser = new User();
        newUser.setUsername(login.username());
        newUser.setEmail(login.email());
        newUser.setPassword(BCrypt.hashpw(login.password(), BCrypt.gensalt()).getBytes());
        newUser.setRole(Role.USER);
        newUser.setDeleted(false);
        newUser.setCreatedAt(LocalDate.now());
        return newUser;
    }

}

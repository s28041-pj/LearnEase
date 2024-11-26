package pl.pjatk.learnease.configure.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.pjatk.learnease.configure.UserContextHolder;
import pl.pjatk.learnease.configure.exception.BusinessException;
import pl.pjatk.learnease.repository.UserRepository;

import java.nio.charset.StandardCharsets;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        pl.pjatk.learnease.entity.user.User user = userRepository.findUserByUsername(username)
                .orElseThrow(() -> new BusinessException(String.format("User not found with username: %s", username)));

        UserContextHolder.setContextUser(user);
        return new CustomUserDetails(user);
    }
}
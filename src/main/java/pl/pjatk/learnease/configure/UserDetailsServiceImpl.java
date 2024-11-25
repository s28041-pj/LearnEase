package pl.pjatk.learnease.configure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.pjatk.learnease.repository.UserRepository;

import java.nio.charset.StandardCharsets;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        pl.pjatk.learnease.entity.user.User user = userRepository.findUserByUsername(username)
                .orElseThrow(() -> new RuntimeException(String.format("User not found with username: %s", username)));

        String password = new String(user.getPassword(), StandardCharsets.UTF_8);
        return new User(user.getUsername(),
            password,
            true, 
            true, 
            true, 
            !user.isDeleted(),
            AuthorityUtils.createAuthorityList(String.valueOf(user.getRole())));
    }
}
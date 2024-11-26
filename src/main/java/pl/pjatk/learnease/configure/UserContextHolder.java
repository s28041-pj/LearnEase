package pl.pjatk.learnease.configure;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import pl.pjatk.learnease.configure.exception.BusinessException;
import pl.pjatk.learnease.configure.security.CustomUserDetails;
import pl.pjatk.learnease.entity.user.User;

@Component
public class UserContextHolder {

    public static User getContextUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof CustomUserDetails) {
                return ((CustomUserDetails) principal).getUser();
            }
        }
        throw new BusinessException("No authenticated user found");
    }

    public static void setContextUser(User user) {
        if (user != null) {
            CustomUserDetails userDetails = new CustomUserDetails(user);
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    userDetails,
                    null,
                    userDetails.getAuthorities()
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
    }
}

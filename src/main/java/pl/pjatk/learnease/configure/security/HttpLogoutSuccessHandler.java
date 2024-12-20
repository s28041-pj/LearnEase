package pl.pjatk.learnease.configure.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static pl.pjatk.learnease.configure.UserContextHolder.setContextUser;

@Component
public class HttpLogoutSuccessHandler implements LogoutSuccessHandler {

    @Override
    public void onLogoutSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        setContextUser(null);
        response.setStatus(HttpServletResponse.SC_OK);
        response.sendRedirect("/login");
        response.getWriter().flush();
    }
}

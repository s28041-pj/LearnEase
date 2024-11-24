package pl.pjatk.learnease.configure;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static pl.pjatk.learnease.configure.AuthorityList.adminAuthority;
import static pl.pjatk.learnease.configure.AuthorityList.anyAuthority;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final HttpLoginSuccessHandler httpLoginSuccessHandler;
    private final HttpLoginFailureHandler httpLoginFailureHandler;
    private final HttpLogoutSuccessHandler httpLogoutSuccessHandler;
    private final UserDetailsServiceImpl userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        return http
                .userDetailsService(userDetailsService)
                .csrf(CsrfConfigurer::disable)
                .exceptionHandling(exceptionHandling -> exceptionHandling.accessDeniedHandler(new CustomAccessDeniedHandler()))
                .authorizeHttpRequests(customizer -> customizer

                //panels
                .requestMatchers("/user-panel.html").hasAnyAuthority(anyAuthority)
                .requestMatchers("/admin-panel.html").hasAuthority(adminAuthority)

                //add login
                .requestMatchers("/add-login.html").permitAll()
                .requestMatchers(HttpMethod.POST,"/add-login").permitAll()
                .anyRequest().authenticated()
                )

                .formLogin(customizer -> customizer
                        .loginPage("/login.html")
                        .loginProcessingUrl("/login")
                        .permitAll()
                        .successHandler(httpLoginSuccessHandler)
                        .failureHandler(httpLoginFailureHandler))

                .logout(customizer -> customizer
                        .logoutUrl("/logout")
                        .logoutSuccessHandler(httpLogoutSuccessHandler)
                        .invalidateHttpSession(true))

                .build();
    }
}
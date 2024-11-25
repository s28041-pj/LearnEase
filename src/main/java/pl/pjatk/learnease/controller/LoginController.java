package pl.pjatk.learnease.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.pjatk.learnease.entity.user.dto.RequestedLogin;
import pl.pjatk.learnease.service.LoginService;

@RestController
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @PostMapping(value = "/add-login")
    public void addLogin(@RequestBody RequestedLogin requestedLogin) {
        loginService.createLoginWithDefaultAuthority(requestedLogin);
    }

}

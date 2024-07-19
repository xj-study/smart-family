package net.tunie.sf.module.login.controller;

import jakarta.annotation.Resource;
import net.tunie.sf.common.domain.ResponseDTO;
import net.tunie.sf.module.login.domain.LoginForm;
import net.tunie.sf.module.login.domain.LoginResultVo;
import net.tunie.sf.module.login.domain.RegisterForm;
import net.tunie.sf.module.login.service.LoginService;
import net.tunie.sf.module.user.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @Resource
    private LoginService loginService;

    @Resource
    private UserService userService;

    @PostMapping("/login")
    public ResponseDTO<LoginResultVo> login(@RequestBody LoginForm loginForm) {
        return loginService.login(loginForm);
    }

    @PostMapping("/register")
    public ResponseDTO<String> register(@RequestBody RegisterForm registerForm) {
        return userService.addUser(registerForm);
    }
    @GetMapping("/login/logout")
    public ResponseDTO<String> logout() {
        return loginService.logout();
    }
}

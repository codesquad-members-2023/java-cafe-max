package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.dto.LoginForm;
import kr.codesqaud.cafe.domain.User;
import kr.codesqaud.cafe.service.LoginService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/login")
public class LoginController {

    private final LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @GetMapping
    public String login() {
        return "user/login";
    }

    @PostMapping
    public String login(LoginForm loginForm, HttpServletRequest request) {
        User loginUser = loginService.login(loginForm.getUserId(), loginForm.getPassword());
        HttpSession session = request.getSession();

        if (loginUser == null) {
            return "user/login";
        }
        session.setAttribute("userId", loginUser.getUserId());
        return "redirect:/";
    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        if(session != null) {
            session.invalidate();
        }
        return "redirect:/";
    }
}

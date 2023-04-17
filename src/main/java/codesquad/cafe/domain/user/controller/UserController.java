package codesquad.cafe.domain.user.controller;

import codesquad.cafe.domain.user.domain.User;
import codesquad.cafe.domain.user.dto.UserLoginRequestDto;
import codesquad.cafe.domain.user.dto.UserRequestDto;
import codesquad.cafe.domain.user.dto.UserResponseDto;
import codesquad.cafe.domain.user.dto.UserUpdateRequestDto;
import codesquad.cafe.domain.user.service.UserService;
import codesquad.cafe.global.exception.CustomException;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

import static codesquad.cafe.global.exception.ErrorCode.*;

@Controller
@RequestMapping("/users")
public class UserController {
    private final String LOGIN_USER = "loginUser";
    private Log log = LogFactory.getLog(UserController.class);

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public String registerUser(@ModelAttribute @Valid final UserRequestDto userRequestDto) {
        userService.join(userRequestDto);
        return "redirect:/users";
    }

    @GetMapping
    public String showUsers(final Model model) {
        List<UserResponseDto> users = userService.showUsers();
        model.addAttribute("users", users);
        return "user/list";
    }

    @GetMapping("/{userId}")
    public String showProfile(@PathVariable String userId, final Model model) {
        UserResponseDto user = userService.findUser(userId);
        model.addAttribute("user", user);
        return "user/profile";
    }

    @GetMapping("/{userId}/form")
    public String showUpdateForm(@PathVariable String userId, HttpSession session) {
        User sessionUser = findSessionUser(session);
        userService.validateUpdateUser(sessionUser, userId);
        return "user/updateForm";
    }

    @PutMapping("/{userId}/update")
    public String updateUser(@PathVariable String userId,
                             @ModelAttribute @Valid UserUpdateRequestDto userUpdateRequestDto,
                             HttpSession session) {
        User sessionUser = findSessionUser(session);
        userService.updateUser(userId, userUpdateRequestDto, sessionUser);
        return "redirect:/users";
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "user/login";
    }

    @PostMapping("/login")
    public String loginUser(final @ModelAttribute UserLoginRequestDto userLoginRequestDto,
                            HttpServletRequest request) {
        User user = userService.login(userLoginRequestDto);
        log.info("로그인 성공");
        HttpSession session = request.getSession();
        session.setAttribute(LOGIN_USER, user);
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logoutUser(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.invalidate();
        log.info("로그아웃 성공");
        return "redirect:/";
    }

    private User findSessionUser(final HttpSession session) {
        Object value = session.getAttribute(LOGIN_USER);
        if (value == null) {
            throw new CustomException(NOT_FOUND_SESSION);
        }
        return (User) value;
    }
}

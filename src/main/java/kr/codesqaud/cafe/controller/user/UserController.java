package kr.codesqaud.cafe.controller.user;

import kr.codesqaud.cafe.domain.User;
import kr.codesqaud.cafe.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping("/create")
    public String create(UserForm form){
        User user = new User(form.getUserId(), form.getPassword(), form.getName(), form.getEmail());
        userService.join(user);
        return "redirect:/users";
    }

    @GetMapping("")
    public String list(Model model){
        List<UserResponse> users = userService.findUsers();
        model.addAttribute("users", users);
        return "user/list";
    }

    @GetMapping("/{userId}")
    public String profile(@PathVariable String userId, Model model){
        UserResponse userResponse = userService.findByUserId(userId).get();
        model.addAttribute("user", userResponse);
        return "user/profile";
    }

}

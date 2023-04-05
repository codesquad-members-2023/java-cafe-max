package kr.codesqaud.cafe.account;

import static kr.codesqaud.cafe.utils.FiledName.*;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.codesqaud.cafe.account.form.JoinForm;
import kr.codesqaud.cafe.account.form.LoginForm;
import kr.codesqaud.cafe.account.form.ProfileForm;
import kr.codesqaud.cafe.account.form.ProfileSettingForm;
import kr.codesqaud.cafe.account.form.UserForm;

@Controller
@RequestMapping("/users")
public class UserController {

	private final UserService userService;
	private final JoinFormValidator joinFormValidator;

	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	public UserController(UserService userService,
		JoinFormValidator joinFormValidator) {
		this.userService = userService;
		this.joinFormValidator = joinFormValidator;
	}

	@InitBinder("joinForm")
	public void joinFormInitBinder(WebDataBinder webDataBinder) {
		webDataBinder.addValidators(joinFormValidator);
	}

	@GetMapping("/login")
	public String showLoginPage(@ModelAttribute LoginForm loginForm) {
		return "account/login";
	}

	@PostMapping("/login")
	public String login(@Valid LoginForm loginForm, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			loggingError(bindingResult);
			return "account/login";
		}
		Optional<User> userOptional = userService.findByEmail(loginForm.getEmail());
		if (userOptional.isEmpty()) {
			loggingError(bindingResult);
			bindingResult.rejectValue(EMAIL, "error.email.notExist");
			return "account/login";
		}
		User user = userOptional.get();
		if (!user.getPassword().equals(loginForm.getPassword())) {
			loggingError(bindingResult);
			bindingResult.rejectValue(PASSWORD, "error.password.notMatch");
			return "account/login";
		}
		return "redirect:/users/" + user.getId();
	}

	@GetMapping("/join")
	public String showJoinPage(@ModelAttribute JoinForm joinForm) {
		return "account/join";
	}

	@PostMapping
	public String addUser(@Valid JoinForm joinForm, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			loggingError(bindingResult);
			return "account/join";
		}
		int userId = userService.createNewUser(joinForm);
		return "redirect:/users/" + userId;
	}

	@GetMapping
	public String showUsers(Model model) {
		List<UserForm> allUserForm = userService.getAllUsersForm();
		model.addAttribute(USERS, allUserForm);
		return "account/members";
	}

	@GetMapping("/{userId}")
	public String showUser(Model model, @PathVariable Long userId) {
		User user = userService.findById(userId);
		ProfileForm profileForm = ProfileForm.from(user);

		model.addAttribute(PROFILE_FORM, profileForm);
		model.addAttribute(USER_ID, userId);
		return "account/profile";
	}

	@GetMapping("/{userId}/update")
	public String showUserProfile(Model model, @PathVariable Long userId) {
		User user = userService.findById(userId);
		ProfileSettingForm profileSettingForm = ProfileSettingForm.from(user);

		model.addAttribute(USER_ID, userId);
		model.addAttribute(PROFILE_SETTING_FORM, profileSettingForm);
		return "account/profileUpdate";
	}

	@PutMapping("/{userId}/update")
	public String setUserProfile(@Valid ProfileSettingForm profileSettingForm, BindingResult bindingResult,
		@PathVariable Long userId
	) {
		if (bindingResult.hasErrors()) {
			loggingError(bindingResult);
			return "account/profileUpdate";
		}
		User user = userService.findById(userId);
		if (userService.isDuplicateEmail(user.getEmail(), profileSettingForm.getEmail())) {
			loggingError(bindingResult);
			bindingResult.rejectValue(EMAIL, "error.email.duplicate");
			return "account/profileUpdate";
		}
		if (!userService.checkPassword(profileSettingForm.getPassword(), user.getPassword())) {
			loggingError(bindingResult);
			bindingResult.rejectValue(PASSWORD, "error.password.notMatch");
			return "account/profileUpdate";
		}
		userService.update(profileSettingForm, userId);
		return "redirect:/users/{userId}";
	}

	private static void loggingError(BindingResult bindingResult) {
		bindingResult.getAllErrors()
			.forEach(error -> logger.error("[ Name = {} ][ Message = {} ]", error.getObjectName(),
				error.getDefaultMessage()));
	}
}

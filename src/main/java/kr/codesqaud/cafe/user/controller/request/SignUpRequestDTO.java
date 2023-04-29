package kr.codesqaud.cafe.user.controller.request;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import kr.codesqaud.cafe.user.domain.UserEntity;

public class SignUpRequestDTO {
	@NotBlank(message = "아이디를 입력해 주세요.")
	@Pattern(regexp = "^[a-zA-z0-9]{4,20}$"
		, message = "아이디는 영문, 숫자 조합 4 ~ 20자를 입력해 주세요.")
	private final String userId;
	@NotBlank(message = "비밀번호를 입력해 주세요.")
	@Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*?&]).{8,32}$"
		, message = "비밀번호는 영어 소문자, 숫자 조합 8 ~ 32자를 입력해 주세요.")
	private final String password;
	@NotBlank(message = "비밀번호 확인란을 입력해 주세요.")
	private final String passwordCheck;
	@NotBlank(message = "이름를 입력해 주세요.")
	@Pattern(regexp = "^[a-zA-Z가-힣][\\sa-zA-Z가-힣]{2,49}$"
		, message = "이름은 영어, 한글 3~50글자 사이로 입력해 주세요.")
	private final String name;
	@NotBlank(message = "이메일를 입력해 주세요.")
	@Email(message = "올바른 형식의 이메일을 입력해 주세요.")
	private final String email;

	@AssertTrue(message = "비밀번호와 비밀번호 확인란이 일치하지 않습니다.")
	public boolean isPasswordsMatch() {
		return password.equals(passwordCheck);
	}

	public SignUpRequestDTO(String userId, String password, String passwordCheck, String name, String email) {
		this.userId = userId;
		this.password = password;
		this.passwordCheck = passwordCheck;
		this.name = name;
		this.email = email;
	}

	public String getUserId() {
		return userId;
	}

	public String getPassword() {
		return password;
	}

	public String getPasswordCheck() {
		return passwordCheck;
	}

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	public UserEntity toEntity() {
		return new UserEntity(userId, password, name, email);
	}

}

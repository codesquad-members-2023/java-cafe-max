package kr.codesqaud.cafe.dto.member;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import kr.codesqaud.cafe.domain.Member;
import org.hibernate.validator.constraints.Length;

public class ProfileEditRequest {

    private Long id;

    @NotBlank
    @Email
    private final String email;

    @NotBlank
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[a-z])[a-zA-Z0-9]{8,32}$")
    private final String password;

    @NotBlank
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[a-z])[a-zA-Z0-9]{8,32}$")
    private final String newPassword;

    @NotBlank
    @Length(min = 2, max = 10)
    private final String nickName;

    public ProfileEditRequest(Long id, String email, String password, String newPassword,
        String nickName) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.newPassword = newPassword;
        this.nickName = nickName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public String getNickName() {
        return nickName;
    }

    public Member toMember() {
        return Member.builder()
            .id(id)
            .email(email)
            .password(newPassword)
            .nickName(nickName)
            .build();
    }

    public static ProfileEditRequest from(MemberResponse memberResponse) {
        return new ProfileEditRequest(memberResponse.getId(), memberResponse.getEmail(),
            null, null, memberResponse.getNickName());
    }
}

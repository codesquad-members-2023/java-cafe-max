package kr.codesqaud.cafe.user.dto;

public class ResponseProfile {
    //회원 프로필 내용 DTO

    private final String name;

    private final String email;

    public ResponseProfile(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

}

package kr.codesqaud.cafe.dto.user;

import kr.codesqaud.cafe.domain.User;

public class UserResponse {
    private String userId;
    private String name;
    private String email;

    private UserResponse(String userId, String name, String email) {
        this.userId = userId;
        this.name = name;
        this.email = email;
    }

    public static UserResponse from(User user) { // Entity → DTO
        return new UserResponse(user.getUserId(), user.getName(), user.getEmail());
    }

    public String getUserId() { return userId; }
    public String getName() { return name; }
    public String getEmail() { return email; }
}

package kr.codesqaud.cafe.domain;

public class User {
    private String userId;
    private String password;
    private String userName;
    private String email;

    public void setWithUserForm(UserForm userForm) {
        this.userId = userForm.getUserId();
        this.password = userForm.getPassword();
        this.userName = userForm.getUserName();
        this.email = userForm.getEmail();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

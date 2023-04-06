package kr.codesqaud.cafe.exception.user;

import kr.codesqaud.cafe.controller.dto.UserUpdateDto;

public class UserUpdateFailedException extends RuntimeException{
    private final UserExceptionType userExceptionType;
    private final UserUpdateDto userUpdateDto;

    public UserUpdateFailedException(UserExceptionType userExceptionType, UserUpdateDto userUpdateDto) {
        this.userExceptionType = userExceptionType;
        this.userUpdateDto = userUpdateDto;
    }

    public UserExceptionType getUserExceptionType() {
        return userExceptionType;
    }

    public UserUpdateDto getUserUpdateDto() {
        return userUpdateDto;
    }
}

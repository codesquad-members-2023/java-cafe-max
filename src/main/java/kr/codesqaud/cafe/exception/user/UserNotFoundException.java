package kr.codesqaud.cafe.exception.user;

import kr.codesqaud.cafe.exception.BaseException;
import kr.codesqaud.cafe.exception.BaseExceptionType;

public class UserNotFoundException extends BaseException {

    public UserNotFoundException(BaseExceptionType exceptionType) {
        super(exceptionType);
    }
}

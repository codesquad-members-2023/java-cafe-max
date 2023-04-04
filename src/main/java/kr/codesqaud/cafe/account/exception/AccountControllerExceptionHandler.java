package kr.codesqaud.cafe.account.exception;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class AccountControllerExceptionHandler {

	public static final String ERROR_DETAILS_FORMAT = "[ ErrorStatus : %s ][ ErrorMessage : %s ][ RequestURL : %s ]";
	private static Logger logger = LoggerFactory.getLogger(AccountControllerExceptionHandler.class);

	@ExceptionHandler(AccountException.class)
	public ModelAndView defaultErrorHandler(HttpServletRequest request, AccountException e) {
		ErrorCode errorCode = e.getErrorCode();
		logger.error(
			String.format(ERROR_DETAILS_FORMAT,
				errorCode.getStatus().toString(),
				errorCode.getMessage(),
				request.getRequestURI()));
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setStatus(errorCode.getStatus());
		modelAndView.addObject("errorCode", errorCode);
		modelAndView.setViewName(errorCode.getViewName());
		return modelAndView;
	}
}

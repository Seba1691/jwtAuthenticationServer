package dges.customer.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import dges.customer.error.exception.InvalidCredentialsException;
import dges.customer.error.exception.UserAlredyExistsException;
import dges.customer.error.exception.UserNotFoundException;

@ControllerAdvice
public class CustomerControllerExceptionHandler {

	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<ErrorMessage> handleUserNotFoundException(UserNotFoundException exception) {
		ErrorMessage error = new ErrorMessage(HttpStatus.NOT_FOUND.getReasonPhrase(), exception.getMessage());
		return new ResponseEntity<ErrorMessage>(error, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(UserAlredyExistsException.class)
	public ResponseEntity<ErrorMessage> handleUserAlredyExistsException(UserAlredyExistsException exception) {
		ErrorMessage error = new ErrorMessage(HttpStatus.BAD_REQUEST.getReasonPhrase(), exception.getMessage());
		return new ResponseEntity<ErrorMessage>(error, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(InvalidCredentialsException.class)
	public ResponseEntity<ErrorMessage> handleInvalidCredentialsException(InvalidCredentialsException exception) {
		ErrorMessage error = new ErrorMessage(HttpStatus.FORBIDDEN.getReasonPhrase(), exception.getMessage());
		return new ResponseEntity<ErrorMessage>(error, HttpStatus.FORBIDDEN);
	}
	
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<ErrorMessage> handleJsonParseExceptionException(HttpMessageNotReadableException exception) {
		ErrorMessage error = new ErrorMessage(HttpStatus.BAD_REQUEST.getReasonPhrase(), exception.getMessage());
		return new ResponseEntity<ErrorMessage>(error, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorMessage> handleGenericException(Exception exception) {
		ErrorMessage error = new ErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
				"Something went wrong");
		return new ResponseEntity<ErrorMessage>(error, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}

package dges.customer.error.exception;

public class InvalidCredentialsException extends RuntimeException {

	private static final long serialVersionUID = -5314901359011185746L;

	public String getMessage(){
		return "Invalid Credentials";
	}
}

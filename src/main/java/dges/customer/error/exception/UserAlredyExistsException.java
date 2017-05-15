package dges.customer.error.exception;

public class UserAlredyExistsException extends RuntimeException {

	private static final long serialVersionUID = 6417916339022436521L;

	private String username;
	
	public UserAlredyExistsException(String username) {
		super();
		this.username = username;
	}
	
	public String getMessage(){
		return "User "+username+" already exists";
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}

package dges.customer.error.exception;

public class UserNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -7518855505503595680L;

	private String username;

	public UserNotFoundException(String username) {
		super();
		this.setUsername(username);
	}
	
	public String getMessage(){
		return "User "+username+" was not found";
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}

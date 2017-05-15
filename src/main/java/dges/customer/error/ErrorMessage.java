package dges.customer.error;

public class ErrorMessage {

	private String message;
	private String error;
	
	public ErrorMessage() {
	}
	
	public ErrorMessage(String error, String message){
		this.error = error;
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public ErrorMessage setMessage(String message) {
		this.message = message;
		return this;
	}

	public String getError() {
		return error;
	}

	public ErrorMessage setError(String error) {
		this.error = error;
		return this;
	}
}

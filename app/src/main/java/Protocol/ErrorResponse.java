package Protocol;

/**
 * Created by Sergiu on 19-Jan-17.
 */
public class ErrorResponse implements Response {
	private String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}

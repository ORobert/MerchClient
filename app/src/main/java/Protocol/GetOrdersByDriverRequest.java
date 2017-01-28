package Protocol;

import Models.User;

/**
 * Created by Sergiu on 27-Jan-17.
 */
public class GetOrdersByDriverRequest implements Request {
	private User user;

	public GetOrdersByDriverRequest(User user) {
		this.user = user;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}

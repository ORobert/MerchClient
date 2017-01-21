package Protocol;

import Models.User;

/**
 * Created by orobe on 19/01/2017.
 */
public class LoginResponse implements Response {
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}

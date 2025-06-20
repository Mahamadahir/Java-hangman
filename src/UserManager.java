import java.util.*;

public class UserManager {
    private final List<User> users = new ArrayList<>();
    private User currentUser;

    public void addUser(String username) {
        User user = new User(username);
        users.add(user);
        currentUser = user;
    }

    public List<User> getAllUsers() {
        return users;
    }

    public void selectUser(User user) {
        this.currentUser = user;
    }

    public User getCurrentUser() {
        return currentUser;
    }
}




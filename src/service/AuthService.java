package service;

import model.User;
import util.PasswordUtils;

import java.util.HashMap;
import java.util.Map;

public class AuthService {
    private Map<String, User> users = new HashMap<>();

    public void register(String username, String plainPassword) {
        String hash = PasswordUtils.hashPassword(plainPassword);
        users.put(username, new User(username, hash));
    }

    public boolean login(String username, String plainPassword) {
        User user = users.get(username);
        if (user == null) return false;
        return PasswordUtils.checkPassword(plainPassword, user.getPasswordHash());
    }
}

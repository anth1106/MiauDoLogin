package service;

import dao.UserDao;
import model.User;
import util.PasswordUtils;

public class AuthService {
    private UserDao userDao;

    public AuthService(UserDao userDao) {
        this.userDao = userDao;
    }

    public void register(String username, String plainPassword) {
        String hash = PasswordUtils.hashPassword(plainPassword);
        userDao.saveUser(username, hash);
    }

    public boolean login(String username, String plainPassword) {
        User user = userDao.findByUsername(username);
        if (user == null) return false;
        return PasswordUtils.checkPassword(plainPassword, user.getPasswordHash());
    }
}

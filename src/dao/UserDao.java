package dao;

import model.User;

import java.io.*;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class UserDao {
    private Map<String, User> users = new HashMap<>();
    private String csvPath;

    public UserDao(String csvPath) {
        this.csvPath = csvPath;
        loadUsersFromCsv(csvPath);
    }

    private void loadUsersFromCsv(String csvPath) {
        try (BufferedReader br = new BufferedReader(new FileReader(csvPath))) {
            String line;
            boolean firstLine = true;
            while ((line = br.readLine()) != null) {
                if (firstLine) { firstLine = false; continue; }
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    String username = parts[0].trim();
                    String hash = parts[1].replace("\"", "").trim();
                    users.put(username, new User(username, hash));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public User findByUsername(String username) {
        return users.get(username);
    }

    public Collection<User> getAllUsers() {
        return users.values();
    }

    public void saveUser(String username, String hash) {
        User user = new User(username, hash);
        users.put(username, user);

        try (FileWriter fw = new FileWriter(csvPath, true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
            out.println(username + "," + hash);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

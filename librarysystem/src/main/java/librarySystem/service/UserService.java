package librarySystem.service;

import librarySystem.DAO.UserDAO;
import librarySystem.DTO.UserDTO;
import librarySystem.Model.User;
import librarySystem.Enum.enRole;
import org.mindrot.jbcrypt.BCrypt;

import java.util.List;
import java.util.stream.Collectors;

public class UserService {

    public enum Mode { Add, Update }

    private Mode mode = Mode.Add;
    private User user;

    public UserService() {
        this.user = new User();
        this.mode = Mode.Add;
    }

    public UserService(User user) {
        this.user = user;
        this.mode = (user != null && (user.getPersonId() != -1 && user.getPersonId() != 0)) ? Mode.Update : Mode.Add;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    private void _addNewUser(String plainPassword) {
        if (plainPassword != null && !plainPassword.trim().isEmpty()) {
            String hashedPassword = BCrypt.hashpw(plainPassword, BCrypt.gensalt());
            user.setPassword(hashedPassword);
        }

        if (user.getRole() == null) {
            user.setRole(enRole.STAFF);
        }

        int newId = UserDAO.addUser(this.user);
        if (newId != -1) {
            this.user.setPersonId(newId);
        } else {
            throw new RuntimeException("Failed to add user to database");
        }
    }

    private void _updateUser() {
        boolean updated = UserDAO.updateUser(this.user);
        if (!updated) {
            throw new RuntimeException("Failed to update user in database");
        }
    }

          private static final java.util.regex.Pattern EMAIL_PATTERN = 
    java.util.regex.Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$");

public static boolean isValidEmail(String email) {
    if (email == null) return false;
    return EMAIL_PATTERN.matcher(email.trim()).matches();
}

    public void save(String plainPassword) {
        if (user == null) {
            throw new IllegalArgumentException("Invalid user data");
        }

        if (user.getUsername() == null || user.getUsername().trim().isEmpty()) {
            throw new IllegalArgumentException("Username is required");
        }

        if (user.getEmail() == null || user.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("Email is required");
        }

        if (!isValidEmail(user.getEmail())) {
            throw new IllegalArgumentException("Invalid email format");
        }

        switch (mode) {
            case Add:
                if (plainPassword == null || plainPassword.trim().isEmpty()) {
                    throw new IllegalArgumentException("Password is required for new users");
                }
                if (UserDAO.isUsernameExists(user.getUsername().trim())) {
                    throw new IllegalArgumentException("Username is already taken! Please choose another one");
                }
                if (UserDAO.isEmailExists(user.getEmail().trim())) {
                    throw new IllegalArgumentException("Email is already registered with another account");
                }
                

                _addNewUser(plainPassword);
                mode = Mode.Update;
                break;

            case Update:
                _updateUser();
                break;
        }
    }

    public void changePassword(String newPlainPassword) {
        if (mode != Mode.Update || user.getPersonId() == -1) {
            throw new IllegalArgumentException("Cannot change password for a non-existing user");
        }

        if (newPlainPassword == null || newPlainPassword.trim().isEmpty()) {
            throw new IllegalArgumentException("New password cannot be empty");
        }

        String hashedNewPassword = BCrypt.hashpw(newPlainPassword, BCrypt.gensalt());
        user.setPassword(hashedNewPassword);

        boolean updated = UserDAO.updateUserPassword(user.getPersonId(), hashedNewPassword);
        if (!updated) {
            throw new RuntimeException("Failed to update user password in database");
        }
    }

    public static User find(int personId) {
        if (personId <= 0) {
            throw new IllegalArgumentException("Invalid personId");
        }
        return UserDAO.getUserById(personId);
    }

    public static List<UserDTO> getAllUsers() {
        try {
            return UserDAO.getAllUsers().stream()
                    .map(UserDTO::fromUser)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch users list: " + e.getMessage());
        }
    }

    public static void delete(int personId) {
        if (personId <= 0) {
            throw new IllegalArgumentException("Invalid personId");
        }
        boolean deleted = UserDAO.deleteUser(personId);
        if (!deleted) {
            throw new RuntimeException("Failed to delete user");
        }
    }
}
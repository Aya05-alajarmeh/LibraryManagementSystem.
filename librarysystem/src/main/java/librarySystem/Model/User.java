
package librarySystem.Model;
import librarySystem.Enum.enRole;

public class User extends Person {
    private String username;
    private String password;
    private enRole role; // STAFF, ADMIN

    public User() {
        super();
        this.role = enRole.STAFF;
    }

    public User(int personId, String firstName, String secondName, String thirdName, String lastName, String email, String phoneNumber, boolean isDeleted, String username, String password, enRole role) {
        super(personId, firstName, secondName, thirdName, lastName, email, phoneNumber, isDeleted);
        this.username = username;
        this.password = password;
        this.role = role != null ? role : enRole.STAFF;
    }

    // Getters and Setters
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public enRole getRole() { return role; }
    public void setRole(enRole role) { this.role = role; }
}
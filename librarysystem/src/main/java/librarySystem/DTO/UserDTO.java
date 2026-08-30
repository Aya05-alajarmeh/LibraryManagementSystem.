
package librarySystem.DTO;
import librarySystem.Enum.enRole;
import librarySystem.Model.User;

public class UserDTO {
    private int personId;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private enRole role;

    public UserDTO() {}

    public UserDTO(int personId, String username, String firstName, String lastName, String email, enRole role) {
        this.personId = personId;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.role = role;
    }

    // Getters and Setters
    public int getPersonId() { return personId; }
    public void setPersonId(int personId) { this.personId = personId; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public enRole getRole() { return role; }
    public void setRole(enRole role) { this.role = role; }

    public static UserDTO fromUser(User user) {
        if (user == null) {
            return null;
        }
        return new UserDTO(
            user.getPersonId(),
            user.getUsername(),
            user.getFirstName(),
            user.getLastName(),
            user.getEmail(),
            user.getRole()
        );
    }


}
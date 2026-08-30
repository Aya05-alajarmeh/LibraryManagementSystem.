package librarySystem.service;
import librarySystem.Global;
import librarySystem.DAO.AuthDAO;
import librarySystem.DTO.UserDTO;
import org.mindrot.jbcrypt.BCrypt;

public class AuthService {

    public AuthService() {
    }

    public UserDTO login(String username, String password) {
        

        System.out.println(username +" "+password);
        if (username == null || username.trim().isEmpty() || password == null || password.trim().isEmpty()) {
           throw new IllegalArgumentException("Error: Username and password cannot be blank!");
            
        }

         Global.user = AuthDAO.getUserByUsername(username.trim());

        if (Global.user == null) {
            throw new IllegalArgumentException("Error: Invalid username or password.");
            
        }

        if (Global.user.isDeleted()) {
            throw new IllegalArgumentException("Error: This account has been deactivated.");
            
        }

        boolean isPasswordMatch = BCrypt.checkpw(password, Global.user.getPassword());
        if (!isPasswordMatch) {
            throw new IllegalArgumentException("Error: Invalid password.");
        }

       UserDTO userDTO = new UserDTO(
            Global.user.getPersonId(),
            Global.user.getUsername(),
            Global.user.getFirstName(),
            Global.user.getLastName(),
            Global.user.getEmail(),
            Global.user.getRole()
        );

        return userDTO;
    }

  
}
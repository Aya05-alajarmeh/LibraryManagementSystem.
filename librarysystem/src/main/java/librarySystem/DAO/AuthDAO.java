package librarySystem.DAO;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import librarySystem.Enum.enRole;
import librarySystem.Model.User;

public class AuthDAO {

    public static User getUserByUsername(String username) {
        String sql = "{CALL sp_LoginUser(?)}";
        User user = null;

        try (Connection conn = DBConnection.getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setString(1, username);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    user = new User();
                    user.setPersonId(rs.getInt("person_id"));
                    user.setFirstName(rs.getString("first_name"));
                    user.setSecondName(rs.getString("second_name"));
                    user.setThirdName(rs.getString("third_name"));
                    user.setLastName(rs.getString("last_name"));
                    user.setEmail(rs.getString("email"));
                    user.setPhoneNumber(rs.getString("phone_number"));
                    user.setDeleted(rs.getBoolean("is_deleted"));
                    
                    user.setUsername(rs.getString("username"));
                    user.setPassword(rs.getString("password")); 
                    
                    String roleStr = rs.getString("role");
                    try {
                        user.setRole(enRole.valueOf(roleStr));
                    } catch (IllegalArgumentException e) {
                        user.setRole(enRole.STAFF); 
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user; 
    }
}
package librarySystem.DAO;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import librarySystem.Enum.enRole;
import librarySystem.Model.User;

public class UserDAO {
    
public static int addUser(User user) {
        String sql = "{call sp_AddNewLibraryUser(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";
        int generatedId = -1;

        try (Connection conn = DBConnection.getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {
            stmt.setString(1, user.getFirstName());
            stmt.setString(2, user.getSecondName());
            stmt.setString(3, user.getThirdName());
            stmt.setString(4, user.getLastName());
            stmt.setString(5, user.getEmail());
            stmt.setString(6, user.getPhoneNumber());
            stmt.setString(7, user.getUsername());
            stmt.setString(8, user.getPassword()); 
            stmt.setString(9, user.getRole().name());
            
            stmt.registerOutParameter(10, java.sql.Types.INTEGER); 
            
            stmt.execute();
            
            generatedId = stmt.getInt(10); 
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return generatedId;
    }
  
  
    public static boolean updateUser(User user) {
        String sql = "{CALL sp_UpdateUser(?, ?, ?, ?, ?, ?, ?)}";
        try (Connection conn = DBConnection.getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {
            stmt.setInt(1, user.getPersonId());
            stmt.setString(2, user.getFirstName());
            stmt.setString(3, user.getSecondName());
            stmt.setString(4, user.getThirdName());
            stmt.setString(5, user.getLastName());
            stmt.setString(6, user.getEmail());
            stmt.setString(7, user.getPhoneNumber());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            
        e.printStackTrace();
            return false;
        }
    }

    public static boolean updateUserPassword(int userId, String hashedNewPassword) {
        String sql = "{CALL sp_UpdateUserPassword(?, ?)}";
        try (Connection conn = DBConnection.getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {
            stmt.setInt(1, userId);
            stmt.setString(2, hashedNewPassword);
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean deleteUser(int userId) {
        String sql = "{CALL sp_DeleteUser(?)}";
        try (Connection conn = DBConnection.getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {
            stmt.setInt(1, userId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static User getUserById(int userId) {
        String sql = "{CALL sp_GetUserById(?)}";
        User user = null;
        try (Connection conn = DBConnection.getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                user = new User();
                user.setPersonId(rs.getInt("person_id"));
                user.setFirstName(rs.getString("first_name"));
                user.setSecondName(rs.getString("second_name"));
                user.setThirdName(rs.getString("third_name"));
                user.setLastName(rs.getString("last_name"));
                user.setEmail(rs.getString("email"));
                user.setPhoneNumber(rs.getString("phone_number"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setRole(enRole.valueOf(rs.getString("role")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    public static List<User> getAllUsers() {
        String sql = "{CALL sp_GetAllUsers()}";
        List<User> users = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             CallableStatement stmt = conn.prepareCall(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                User user = new User();
                user.setPersonId(rs.getInt("person_id"));
                user.setFirstName(rs.getString("FirstName"));
                user.setSecondName(rs.getString("SecondName"));
                user.setThirdName(rs.getString("ThirdName"));
                user.setLastName(rs.getString("LastName"));
                user.setEmail(rs.getString("Email"));
                user.setPhoneNumber(rs.getString("PhoneNumber"));
                user.setUsername(rs.getString("Username"));
                user.setPassword(rs.getString("Password"));
                user.setRole(enRole.valueOf(rs.getString("Role")));
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public static boolean isEmailExists(String email) {
        String sql = "{CALL sp_IsEmailExists(?, ?)}";
        boolean exists = false;

        try (Connection conn = DBConnection.getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setString(1, email);
           stmt.registerOutParameter(2, java.sql.Types.INTEGER);

            stmt.execute();

           int result = stmt.getInt(2);
            exists = (result == 1);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return exists;
    }

    public static boolean isUsernameExists(String username) {
        String sql = "{CALL sp_IsUsernameExists(?, ?)}";
        boolean exists = false;

        try (Connection conn = DBConnection.getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setString(1, username);
            stmt.registerOutParameter(2, java.sql.Types.INTEGER);

            stmt.execute();

            int result = stmt.getInt(2);
            exists = (result == 1);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return exists;
    }


}
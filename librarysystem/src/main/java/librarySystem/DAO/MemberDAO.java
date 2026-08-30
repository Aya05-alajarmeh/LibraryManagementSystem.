package librarySystem.DAO;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import librarySystem.Model.Member;

public class MemberDAO {

   public static int addMember(Member member) {
        String sql = "{CALL sp_AddMember(?, ?, ?, ?, ?, ?, ?)}";
        int newPersonId = -1;

        try (Connection conn = DBConnection.getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setString(1, member.getFirstName());
            stmt.setString(2, member.getSecondName());
            stmt.setString(3, member.getThirdName());
            stmt.setString(4, member.getLastName());
            stmt.setString(5, member.getEmail());
            stmt.setString(6, member.getPhoneNumber());
            stmt.registerOutParameter(7, java.sql.Types.INTEGER);

            stmt.execute();

            newPersonId = stmt.getInt(7);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return newPersonId;
    }
   
    public static boolean deleteMember(int personId) {
        String sql = "{CALL sp_DeleteMember(?)}";
        
        try (Connection conn = DBConnection.getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setInt(1, personId);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean updateMember(Member member) {
        String sql = "{CALL sp_UpdateMember(?, ?, ?, ?, ?, ?, ?)}";
        try (Connection conn = DBConnection.getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {
            stmt.setInt(1, member.getPersonId());
            stmt.setString(2, member.getFirstName());
            stmt.setString(3, member.getSecondName());
            stmt.setString(4, member.getThirdName());
            stmt.setString(5, member.getLastName());
            stmt.setString(6, member.getEmail());
            stmt.setString(7, member.getPhoneNumber());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static Member getMemberById(int personId) {
        String sql = "{CALL sp_GetMemberById(?)}";
        Member member = null;
        try (Connection conn = DBConnection.getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {
            stmt.setInt(1, personId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    member = new Member();
                    member.setPersonId(rs.getInt("person_id"));
                    member.setFirstName(rs.getString("first_name"));
                    member.setSecondName(rs.getString("second_name"));
                    member.setThirdName(rs.getString("third_name"));
                    member.setLastName(rs.getString("last_name"));
                    member.setEmail(rs.getString("email"));
                    member.setPhoneNumber(rs.getString("phone_number"));                    
                    if (rs.getDate("registration_date") != null) {
                        member.setRegistrationDate(rs.getDate("registration_date").toLocalDate());
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return member;
    }

    public static List<Member> getAllMembers() {
        String sql = "{CALL sp_GetAllMembers()}";
        List<Member> members = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             CallableStatement stmt = conn.prepareCall(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Member member = new Member();
                member.setPersonId(rs.getInt("person_id"));
                member.setFirstName(rs.getString("first_name"));
                member.setSecondName(rs.getString("second_name"));
                member.setThirdName(rs.getString("third_name"));
                member.setLastName(rs.getString("last_name"));
                member.setEmail(rs.getString("email"));
                member.setPhoneNumber(rs.getString("phone_number"));
                
                if (rs.getDate("registration_date") != null) {
                    member.setRegistrationDate(rs.getDate("registration_date").toLocalDate());
                }
                members.add(member);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return members;
    }


}
package librarySystem.DAO;
import librarySystem.Model.DashboardStats;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;

public class DashboardStatsDAO {

public DashboardStats getDashboardStats() {
    DashboardStats stats = new DashboardStats();
    String query = "{CALL sp_GetDashboardStats()}"; 

    try (Connection conn = DBConnection.getConnection(); 
         CallableStatement stmt = conn.prepareCall(query);
         ResultSet rs = stmt.executeQuery()) {

        if (rs.next()) {
            stats.setTotalBookCopies(rs.getInt("totalBookCopies"));
            stats.setTotalMembers(rs.getInt("totalMembers"));
            stats.setAvailableBooks(rs.getInt("availableBooks"));
            stats.setBorrowedBooks(rs.getInt("borrowedBooks"));
            stats.setTotalCategories(rs.getInt("totalCategories"));
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return stats;
}
}
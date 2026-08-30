package librarySystem.DAO;

import librarySystem.Model.Category;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryDAO {

   public static int addCategory(Category category) {
    String sql = "{CALL sp_AddCategory(?, ?, ?)}"; 
    int generatedId = -1;
    
    try (Connection conn = DBConnection.getConnection();
         CallableStatement stmt = conn.prepareCall(sql)) {
        
        stmt.setString(1, category.getCategoryName());
        stmt.setString(2, category.getDescription());
        
       stmt.registerOutParameter(3, java.sql.Types.INTEGER);
        
        stmt.execute();
        
       generatedId = stmt.getInt(3);
        
    } catch (SQLException e) {
        e.printStackTrace();
    }
    
    return generatedId;
}
    public static boolean updateCategory(Category category) {
        String sql = "{CALL sp_UpdateCategory(?, ?, ?)}";
        try (Connection conn = DBConnection.getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {
            stmt.setInt(1, category.getCategoryId());
            stmt.setString(2, category.getCategoryName());
            stmt.setString(3, category.getDescription());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean deleteCategory(int categoryId) {
        String sql = "{CALL sp_DeleteCategory(?)}";
        try (Connection conn = DBConnection.getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {
            stmt.setInt(1, categoryId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static List<Category> getAllCategories() {
        String sql = "{CALL sp_GetAllCategories()}";
        List<Category> categories = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             CallableStatement stmt = conn.prepareCall(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Category cat = new Category();
                cat.setCategoryId(rs.getInt("category_id"));
                cat.setCategoryName(rs.getString("category_name"));
                cat.setDescription(rs.getString("description"));
                categories.add(cat);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categories;
    }
}
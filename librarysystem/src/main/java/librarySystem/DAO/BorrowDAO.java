package librarySystem.DAO;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import librarySystem.Model.Book;
import librarySystem.DTO.BorrowDTO;
import librarySystem.Model.Category;

public class BorrowDAO {

    public static void borrowBook(int bookId, int memberId, int userId) throws SQLException {
        String sql = "{CALL sp_BorrowBookByBookId(?, ?, ?)}";
        try (Connection conn = DBConnection.getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {
            stmt.setInt(1, bookId);
            stmt.setInt(2, memberId);
            stmt.setInt(3, userId);
            stmt.execute();
        }
    }

    public static void returnBook(int borrowId) throws SQLException {
        String sql = "{CALL sp_ReturnBook(?)}";
        try (Connection conn = DBConnection.getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {
            stmt.setInt(1, borrowId);
            stmt.execute();
        }
    }

    public static List<Book> getAvailableBooksForBorrow() {
        List<Book> books = new ArrayList<>();
        String sql = "{CALL sp_GetAvailableBooksForBorrow()}";
        try (Connection conn = DBConnection.getConnection();
             CallableStatement stmt = conn.prepareCall(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Book book = new Book();
                book.setBookId(rs.getInt("book_id"));
                book.setTitle(rs.getString("title"));
                book.setAuthor(rs.getString("author"));
                book.setIsbn(rs.getString("isbn"));
                
                Category category = new Category();
                category.setCategoryId(rs.getInt("category_id"));
                category.setCategoryName(rs.getString("category_name"));
                book.setCategory(category);     
              //  book.setCount(rs.getInt("count"));

                books.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }
   
    public static List<BorrowDTO> getActiveBorrows() throws SQLException {
    List<BorrowDTO> borrowList = new ArrayList<>();
    String sql = "{CALL sp_GetActiveBorrows()}";
    
    try (Connection conn = DBConnection.getConnection();
         CallableStatement stmt = conn.prepareCall(sql);
         ResultSet rs = stmt.executeQuery()) {
        
        while (rs.next()) {
            BorrowDTO dto = new BorrowDTO();
            dto.setBorrowId(rs.getInt("borrow_id"));
            dto.setBookTitle(rs.getString("book_title"));
            dto.setBarcode(rs.getString("barcode"));
            dto.setMemberName(rs.getString("member_name"));
            
            if (rs.getDate("borrow_date") != null) {
                dto.setBorrowDate(rs.getDate("borrow_date").toLocalDate());
            }
            if (rs.getDate("due_date") != null) {
                dto.setDueDate(rs.getDate("due_date").toLocalDate());
            }
            
            dto.setStatus(rs.getString("status"));

            borrowList.add(dto);
        }
    }
    return borrowList;
}
}
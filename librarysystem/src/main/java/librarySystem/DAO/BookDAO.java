package librarySystem.DAO;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import librarySystem.Model.Book;

public class BookDAO {

  
     public static int addBookWithCopies(Book book,String barcode, int quantity) {
        String sql = "{CALL sp_AddBookWithMultipleCopies(?, ?, ?, ?, ?, ?)}";
        int bookId = -1;
        try (Connection conn = DBConnection.getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {
            stmt.setString(1, book.getTitle());
            stmt.setString(2, book.getAuthor());
            stmt.setInt(3, book.getCategory().getCategoryId());
            stmt.setString(4, book.getIsbn());
            stmt.setString(5, barcode);
            stmt.setInt(6, quantity);            
            
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                bookId = rs.getInt(1); 
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookId;
    }

     public static boolean deleteBook(int bookId) {
        String sql = "{CALL sp_DeleteBook(?)}";
        try (Connection conn = DBConnection.getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {
            stmt.setInt(1, bookId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

     public static boolean deleteCopy(int copyId) {
        String sql = "{CALL sp_DeleteCopy(?)}";
        try (Connection conn = DBConnection.getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {
            stmt.setInt(1, copyId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

     public static boolean isIsbnExists(String isbn) {
    String sql = "{CALL sp_IsIsbnExists(?, ?)}"; 
    boolean exists = false;
    try (Connection conn = DBConnection.getConnection();
         CallableStatement stmt = conn.prepareCall(sql)) {
        stmt.setString(1, isbn);
        stmt.registerOutParameter(2, java.sql.Types.INTEGER);
        stmt.execute();
        exists = (stmt.getInt(2) == 1);
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return exists;
}

     public static java.util.List<librarySystem.DTO.BookWithCopiesDTO> getAllBooksWithCopies() {
        String sql = "{CALL sp_GetAllBooksWithAllCopies()}";
        java.util.Map<Integer, librarySystem.DTO.BookWithCopiesDTO> bookMap = new java.util.LinkedHashMap<>();

        try (Connection conn = DBConnection.getConnection();
             CallableStatement stmt = conn.prepareCall(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int bookId = rs.getInt("book_id");

                librarySystem.DTO.BookWithCopiesDTO bookDTO = bookMap.get(bookId);
                if (bookDTO == null) {
                    bookDTO = new librarySystem.DTO.BookWithCopiesDTO();
                    bookDTO.setBookId(bookId);
                    bookDTO.setTitle(rs.getString("title"));
                    bookDTO.setAuthor(rs.getString("author"));
                    bookDTO.setIsbn(rs.getString("isbn"));
                    bookDTO.setCategoryName(rs.getString("category_name"));
                    bookDTO.setCopies(new java.util.ArrayList<>());
                    bookDTO.setCount(rs.getInt("total_copies_count"));
                    bookMap.put(bookId, bookDTO);
                }

                int copyId = rs.getInt("copy_id");
                if (!rs.wasNull()) {
                    librarySystem.Model.BookCopy copy = new librarySystem.Model.BookCopy();
                    copy.setCopyId(copyId);
                    copy.setBarcode(rs.getString("barcode"));
                    copy.setStatus(librarySystem.Enum.enBookCopyStatus.valueOf(rs.getString("status").toUpperCase()));
                    bookDTO.getCopies().add(copy);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return new java.util.ArrayList<>(bookMap.values());
    }


}

package librarySystem.DAO;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import librarySystem.DTO.FineDTO;
public class FineDAO {

    public static void payFine(int fineId) throws SQLException {
        String sql = "{CALL sp_PayFine(?)}";
        try (Connection conn = DBConnection.getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {
            stmt.setInt(1, fineId);
            stmt.execute();
        }
    }

    public static List<FineDTO> getUnpaidFines() throws SQLException {
        List<FineDTO> fineList = new ArrayList<>();
        String sql = "{CALL sp_GetUnpaidFines()}";
        
        try (Connection
             conn = DBConnection.getConnection();
             CallableStatement stmt = conn.prepareCall(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                FineDTO dto = new FineDTO();
                dto.setFineId(rs.getInt("fine_id"));
                dto.setBorrowId(rs.getInt("borrow_id"));
                dto.setMemberName(rs.getString("member_name"));
                dto.setAmount(rs.getBigDecimal("amount"));
                
                if (rs.getDate("fine_date") != null) {
                    dto.setFineDate(rs.getDate("fine_date").toLocalDate());
                }
                
                dto.setStatus(rs.getString("status"));

                fineList.add(dto);
            }
        }
        return fineList;
    
}
}
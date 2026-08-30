package librarySystem.service;

import librarySystem.DAO.FineDAO;
import librarySystem.DTO.FineDTO;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

public class FineService {

 
    public FineService() {
      
    }

    public static boolean payFine(int fineId) {
        if (fineId <= 0) {
            throw new IllegalArgumentException("Validation Error: Invalid Fine ID.");
           
        }

        try {
            FineDAO.payFine(fineId);
            return true;
        } catch (SQLException e) {
            throw new RuntimeException("Database Error while paying fine: " + e.getMessage());
         
        }
    }

    public static List<FineDTO> getUnpaidFines() {
        try {
            return FineDAO.getUnpaidFines();
        } catch (SQLException e) {
            throw new RuntimeException("Database Error while fetching unpaid fines: " + e.getMessage());
        
        }
    }
}
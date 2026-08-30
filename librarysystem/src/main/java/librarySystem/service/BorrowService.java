package librarySystem.service;

import librarySystem.DAO.BorrowDAO;
import librarySystem.DTO.BookDTO;
import librarySystem.DTO.BorrowDTO;
import librarySystem.Model.Book;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

public class BorrowService {

  
    public BorrowService() {
        }

    public static boolean borrowBook(int bookId, int memberId, int userId) {
        if (bookId <= 0 || memberId <= 0 || userId <= 0) {
            throw new IllegalArgumentException("Validation Error: Invalid IDs provided for borrowing.");
        }

        try {
            BorrowDAO.borrowBook(bookId, memberId, userId);
            return true;
        } catch (SQLException e) {
            throw new RuntimeException("Database Error during borrowing: " + e.getMessage(), e);
        }
    }

    public static boolean returnBook(int borrowId) {
        if (borrowId <= 0) {
            throw new IllegalArgumentException("Validation Error: Invalid Borrow ID.");
           
        }

        try {
            BorrowDAO.returnBook(borrowId);
            System.out.println("Success: Book returned successfully.");
            return true;
        } catch (SQLException e) {
            throw new RuntimeException("Database Error during returning book: " + e.getMessage(), e);
        }
    }

    public static List<BookDTO> getAvailableBooksForBorrow() {
        try {
            List<Book> books = BorrowDAO.getAvailableBooksForBorrow();
            
           return books.stream()
                        .map(BookDTO::fromBook)
                        .collect(java.util.stream.Collectors.toList());
                        
        } catch (Exception e) {
            throw new RuntimeException("Error while fetching available books: " + e.getMessage(), e);
        }
    }

    public static List<BorrowDTO> getActiveBorrows() {
        try {
            return BorrowDAO.getActiveBorrows();
        } catch (SQLException e) {
            throw new RuntimeException("Database Error while fetching active borrows: " + e.getMessage(), e);
        }
    }
}
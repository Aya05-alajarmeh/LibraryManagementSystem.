package librarySystem.DTO;

import java.time.LocalDate;

public class BorrowDTO {
    private int borrowId;
    private String bookTitle;
    private String barcode;
    private String memberName;
    private LocalDate borrowDate;
    private LocalDate dueDate;
    private String status;

    public BorrowDTO() {}

    public BorrowDTO(int borrowId, String bookTitle, String barcode, String memberName, LocalDate borrowDate, LocalDate dueDate, String status) {
        this.borrowId = borrowId;
        this.bookTitle = bookTitle;
        this.barcode = barcode;
        this.memberName = memberName;
        this.borrowDate = borrowDate;
        this.dueDate = dueDate;
        this.status = status;
    }

    // Getters and Setters
    public int getBorrowId() { return borrowId; }
    public void setBorrowId(int borrowId) { this.borrowId = borrowId; }

    public String getBookTitle() { return bookTitle; }
    public void setBookTitle(String bookTitle) { this.bookTitle = bookTitle; }

    public String getBarcode() { return barcode; }
    public void setBarcode(String barcode) { this.barcode = barcode; }

    public String getMemberName() { return memberName; }
    public void setMemberName(String memberName) { this.memberName = memberName; }

    public LocalDate getBorrowDate() { return borrowDate; }
    public void setBorrowDate(LocalDate borrowDate) { this.borrowDate = borrowDate; }

    public LocalDate getDueDate() { return dueDate; }
    public void setDueDate(LocalDate dueDate) { this.dueDate = dueDate; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
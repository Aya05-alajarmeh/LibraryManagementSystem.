package librarySystem.DTO;

import java.math.BigDecimal;
import java.time.LocalDate;

public class FineDTO {
    private int fineId;
    private int borrowId;
    private String memberName;
    private BigDecimal amount;
    private LocalDate fineDate;
    private String status;

    public FineDTO() {}

    public FineDTO(int fineId, int borrowId, String memberName, BigDecimal amount, LocalDate fineDate, String status) {
        this.fineId = fineId;
        this.borrowId = borrowId;
        this.memberName = memberName;
        this.amount = amount;
        this.fineDate = fineDate;
        this.status = status;
    }



    
    // Getters and Setters
    public int getFineId() { return fineId; }
    public void setFineId(int fineId) { this.fineId = fineId; }

    public int getBorrowId() { return borrowId; }
    public void setBorrowId(int borrowId) { this.borrowId = borrowId; }

    public String getMemberName() { return memberName; }
    public void setMemberName(String memberName) { this.memberName = memberName; }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public LocalDate getFineDate() { return fineDate; }
    public void setFineDate(LocalDate fineDate) { this.fineDate = fineDate; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
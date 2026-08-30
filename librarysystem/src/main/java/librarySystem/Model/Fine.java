
package librarySystem.Model;
import java.math.BigDecimal;
import java.time.LocalDate;

import librarySystem.Enum.enFineStatus;

public class Fine {
    private int fineId;
    private Borrow borrow;
    private BigDecimal amount;
    private enFineStatus status; // Unpaid, Paid
    private LocalDate fineDate;

    public Fine() {
        this.status = enFineStatus.UNPAID;
        this.fineDate = LocalDate.now();
    }

    public Fine(int fineId, Borrow borrow, BigDecimal amount, enFineStatus status, LocalDate fineDate) {
        this.fineId = fineId;
        this.borrow = borrow;
        this.amount = amount;
        this.status = status != null ? status : enFineStatus.UNPAID;
        this.fineDate = fineDate != null ? fineDate : LocalDate.now();
    }

    // Getters and Setters
    public int getFineId() { return fineId; }
    public void setFineId(int fineId) { this.fineId = fineId; }

    public Borrow getBorrow() { return borrow; }
    public void setBorrow(Borrow borrow) { this.borrow = borrow; }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public enFineStatus getStatus() { return status; }
    public void setStatus(enFineStatus status) { this.status = status; }

    public LocalDate getFineDate() { return fineDate; }
    public void setFineDate(LocalDate fineDate) { this.fineDate = fineDate; }
}
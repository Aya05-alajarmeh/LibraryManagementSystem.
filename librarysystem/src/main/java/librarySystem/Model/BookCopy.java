
package librarySystem.Model;
import librarySystem.Enum.enBookCopyStatus;

public class BookCopy {
    private int copyId;
    private Book book; 
    private String barcode;
    private enBookCopyStatus status; // Available, Borrowed, Damaged
    private boolean isDeleted;

    public BookCopy() {
        this.status = enBookCopyStatus.AVAILABLE;
    }

    public BookCopy(int copyId, Book book, String barcode, enBookCopyStatus status, boolean isDeleted) {
        this.copyId = copyId;
        this.book = book;
        this.barcode = barcode;
        this.status = status != null ? status : enBookCopyStatus.AVAILABLE;
        this.isDeleted = isDeleted;
    }

    // Getters and Setters
    public int getCopyId() { return copyId; }
    public void setCopyId(int copyId) { this.copyId = copyId; }

    public Book getBook() { return book; }
    public void setBook(Book book) { this.book = book; }

    public String getBarcode() { return barcode; }
    public void setBarcode(String barcode) { this.barcode = barcode; }

    public enBookCopyStatus getStatus() { return status; }
    public void setStatus(enBookCopyStatus status) { this.status = status; }

    public boolean isDeleted() { return isDeleted; }
    public void setDeleted(boolean deleted) { isDeleted = deleted; }
}
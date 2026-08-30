

package librarySystem.Model;import java.time.LocalDate;

import librarySystem.Enum.enBorrowStatus;

public class Borrow {
    private int borrowId;
    private BookCopy bookCopy;
    private Member member;
    private User user;
    private LocalDate borrowDate;
    private LocalDate dueDate;
    private LocalDate returnDate;
    private enBorrowStatus status; // Borrowed, Returned, Overdue

    public Borrow() {
        this.borrowDate = LocalDate.now();
        this.status = enBorrowStatus.BORROWED;
    }

    public Borrow(int borrowId, BookCopy bookCopy, Member member, User user, LocalDate borrowDate, LocalDate dueDate, LocalDate returnDate, enBorrowStatus status) {
        this.borrowId = borrowId;
        this.bookCopy = bookCopy;
        this.member = member;
        this.user = user;
        this.borrowDate = borrowDate != null ? borrowDate : LocalDate.now();
        this.dueDate = dueDate;
        this.returnDate = returnDate;
        this.status = status != null ? status : enBorrowStatus.BORROWED;
    }

    // Getters and Setters
    public int getBorrowId() { return borrowId; }
    public void setBorrowId(int borrowId) { this.borrowId = borrowId; }

    public BookCopy getBookCopy() { return bookCopy; }
    public void setBookCopy(BookCopy bookCopy) { this.bookCopy = bookCopy; }

    public Member getMember() { return member; }
    public void setMember(Member member) { this.member = member; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public LocalDate getBorrowDate() { return borrowDate; }
    public void setBorrowDate(LocalDate borrowDate) { this.borrowDate = borrowDate; }

    public LocalDate getDueDate() { return dueDate; }
    public void setDueDate(LocalDate dueDate) { this.dueDate = dueDate; }

    public LocalDate getReturnDate() { return returnDate; }
    public void setReturnDate(LocalDate returnDate) { this.returnDate = returnDate; }

    public enBorrowStatus getStatus() { return status; }
    public void setStatus(enBorrowStatus status) { this.status = status; }
}
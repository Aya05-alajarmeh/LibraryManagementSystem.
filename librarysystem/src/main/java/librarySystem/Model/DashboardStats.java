package librarySystem.Model;

public class DashboardStats {
    private int totalBookCopies;
    private int totalMembers;
    private int availableBooks;
    private int borrowedBooks;
    private int totalCategories;

    // Getters and Setters
    public int getTotalBookCopies() { return totalBookCopies; }
    public void setTotalBookCopies(int totalBookCopies) { this.totalBookCopies = totalBookCopies; }

    public int getTotalMembers() { return totalMembers; }
    public void setTotalMembers(int totalMembers) { this.totalMembers = totalMembers; }

    public int getAvailableBooks() { return availableBooks; }
    public void setAvailableBooks(int availableBooks) { this.availableBooks = availableBooks; }

    public int getBorrowedBooks() { return borrowedBooks; }
    public void setBorrowedBooks(int borrowedBooks) { this.borrowedBooks = borrowedBooks; }

    public int getTotalCategories() { return totalCategories; }
    public void setTotalCategories(int totalCategories) { this.totalCategories = totalCategories; }
}
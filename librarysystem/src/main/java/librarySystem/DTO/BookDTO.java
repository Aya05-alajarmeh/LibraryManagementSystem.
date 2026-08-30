package librarySystem.DTO;
import librarySystem.Model.Book;
public class BookDTO {
    private int bookId;
    private String title;
    private String author;
    private String categoryName;
    private String isbn;
    private int count;
    public int getCount() {
        return count;
    }
    public void setCount(int count) {
        this.count = count;
    }

    
    public BookDTO() {}

    public BookDTO(int bookId, String title, String author,String categoryName, String isbn) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.categoryName = categoryName;
        this.isbn = isbn;
    }

    public static BookDTO fromBook(Book book) {
        if (book == null) {
            return null;
        }
        int catId = 0;
        String catName = null;
        if (book.getCategory() != null) {
            catId = book.getCategory().getCategoryId();
            catName = book.getCategory().getCategoryName();
        }
        return new BookDTO(
            book.getBookId(),
            book.getTitle(),
            book.getAuthor(),
            catName,
            book.getIsbn()
        );
    }

    // Getters and Setters
    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
   
    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
}
package librarySystem.DTO;

import librarySystem.Model.BookCopy;
import java.util.List;

public class BookWithCopiesDTO extends BookDTO {
   
    private List<BookCopy> copies;

    public BookWithCopiesDTO() {}

    public BookWithCopiesDTO(int bookId, String title, String author, String isbn, String categoryName, List<BookCopy> copies) {
        super(bookId, title, author, categoryName, isbn);
        this.copies = copies;
    }

    // Getters and Setters
  
    public List<BookCopy> getCopies() { return copies; }
    public void setCopies(List<BookCopy> copies) { this.copies = copies; }
}
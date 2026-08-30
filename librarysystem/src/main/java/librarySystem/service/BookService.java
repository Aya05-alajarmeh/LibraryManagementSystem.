package librarySystem.service;

import librarySystem.DAO.BookDAO;
import librarySystem.Model.Book;

public class BookService {

   
    private Book book;
    private String barcode;
    private int quantity;

    public BookService() {
        this.book = new Book();
    }

    public BookService(Book book) {
        this.book = book;
      }

    public BookService(Book book, String barcode, int quantity) {
        this.book = book;
        this.barcode = barcode;
        this.quantity = quantity;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public  int saveWithCopies() {
        if (book == null) throw new IllegalArgumentException("Error: Book Data is not valid.");

        if (book.getTitle() == null || book.getTitle().trim().isEmpty()) {
            throw new IllegalArgumentException("Error: Book title is required.");
        }

        if (book.getIsbn() != null && !book.getIsbn().trim().isEmpty()) {
            if (BookDAO.isIsbnExists(book.getIsbn())) {
                throw new IllegalArgumentException("Error: ISBN already exists.");
            }
        }
        
        int bookId = BookDAO.addBookWithCopies(this.book, this.barcode, this.quantity);
        if (bookId != -1) {
            this.book.setBookId(bookId);
        }else
        {
            throw new IllegalArgumentException("Error: Failed to save the book.");
        }
        return bookId;
    }

    public static boolean deleteBook(int bookId) {
      if(bookId <= 0) throw new IllegalArgumentException("Error: Invalid book ID.");
      
      if(! BookDAO.deleteBook(bookId))
      {
          throw new IllegalArgumentException("Error: Failed to delete the book.");
      }
      return true;
    }

    public static boolean deleteCopy(int copyId) {
        if(copyId <= 0) throw new IllegalArgumentException("Error: Invalid copy ID.");
     
        if(! BookDAO.deleteCopy(copyId))
       {
           throw new IllegalArgumentException("Error: Failed to delete the copy.");
       }
       return true;
    }
  
    public static java.util.List<librarySystem.DTO.BookWithCopiesDTO> getAllBooksWithCopies() {
        return BookDAO.getAllBooksWithCopies();
    }
}
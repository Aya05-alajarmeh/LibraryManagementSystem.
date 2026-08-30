
package librarySystem.controller;

import com.google.gson.Gson;
import librarySystem.DTO.BookWithCopiesDTO;
import librarySystem.Model.Book;
import librarySystem.service.BookService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

@WebServlet("/api/books/*")
public class BookServlet extends HttpServlet {
    private final Gson gson = new Gson();

    private <T> T parseJsonBody(HttpServletRequest request, Class<T> clazz) throws IOException {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = request.getReader()) {
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        }
        return gson.fromJson(sb.toString(), clazz);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        String pathInfo = request.getPathInfo();

        if ("/add".equals(pathInfo)) {
            try {
                com.google.gson.JsonObject jsonBody = parseJsonBody(request, com.google.gson.JsonObject.class);

                String title = jsonBody.has("title") ? jsonBody.get("title").getAsString() : null;
                String author = jsonBody.has("author") ? jsonBody.get("author").getAsString() : null;
                String isbn = jsonBody.has("isbn") ? jsonBody.get("isbn").getAsString() : null;
                String barcode = jsonBody.has("barcode") ? jsonBody.get("barcode").getAsString() : null;
                int quantity = jsonBody.has("quantity") ? jsonBody.get("quantity").getAsInt() : 1;
                
                librarySystem.Model.Category category = null;
                if (jsonBody.has("category")) {
                    category = gson.fromJson(jsonBody.get("category"), librarySystem.Model.Category.class);
                }

                Book book = new Book();
                book.setTitle(title);
                book.setAuthor(author);
                book.setIsbn(isbn);
                book.setCategory(category);

                BookService bookService = new BookService(book, barcode, quantity);
                int newBookId = bookService.saveWithCopies(); 

                response.setStatus(HttpServletResponse.SC_OK);
                response.getWriter().write("{\"message\": \"Book added successfully\", \"bookId\": " + newBookId + "}");

            } catch (IllegalArgumentException e) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("{\"error\": \"" + e.getMessage() + "\"}");
            } catch (Exception e) {
                e.printStackTrace();
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("{\"error\": \"Invalid JSON payload: " + e.getMessage() + "\"}");
            }
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.getWriter().write("{\"error\": \"Endpoint not found\"}");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        String pathInfo = request.getPathInfo();

        if (pathInfo == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\": \"Invalid endpoint\"}");
            return;
        }

        switch (pathInfo) {
            case "/delete":
                try {
                    int bookId = Integer.parseInt(request.getParameter("bookId"));
                    BookService.deleteBook(bookId);
                    response.getWriter().write("{\"message\": \"Book deleted successfully\"}");
                } catch (IllegalArgumentException e) {
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    response.getWriter().write("{\"error\": \"" + e.getMessage() + "\"}");
                } catch (Exception e) {
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    response.getWriter().write("{\"error\": \"Invalid bookId format\"}");
                }
                break;

            case "/copy/delete":
                try {
                    int copyId = Integer.parseInt(request.getParameter("copyId"));
                    BookService.deleteCopy(copyId);
                    response.getWriter().write("{\"message\": \"Book copy deleted successfully\"}");
                } catch (IllegalArgumentException e) {
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    response.getWriter().write("{\"error\": \"" + e.getMessage() + "\"}");
                } catch (Exception e) {
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    response.getWriter().write("{\"error\": \"Invalid copyId format\"}");
                }
                break;

            default:
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.getWriter().write("{\"error\": \"Endpoint not found\"}");
                break;
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        List<BookWithCopiesDTO> allBooks = BookService.getAllBooksWithCopies();
        response.getWriter().write(gson.toJson(allBooks));
    }
}
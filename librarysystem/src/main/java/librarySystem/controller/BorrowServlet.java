package librarySystem.controller;

import com.google.gson.Gson;
import librarySystem.DTO.BookDTO;
import librarySystem.DTO.BorrowDTO;
import librarySystem.service.BorrowService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@WebServlet("/api/borrows/*")
public class BorrowServlet extends HttpServlet {
    private final Gson gson = new com.google.gson.GsonBuilder()
        .registerTypeAdapter(java.time.LocalDate.class, (com.google.gson.JsonSerializer<java.time.LocalDate>) (src, typeOfSrc, context) -> 
            new com.google.gson.JsonPrimitive(src.toString()))
        .registerTypeAdapter(java.time.LocalDate.class, (com.google.gson.JsonDeserializer<java.time.LocalDate>) (json, typeOfT, context) -> 
            java.time.LocalDate.parse(json.getAsString()))
        .create();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        String pathInfo = request.getPathInfo();

        if (pathInfo == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\": \"Invalid endpoint\"}");
            return;
        }

        try {
            switch (pathInfo) {
                case "/available-books":
                    List<BookDTO> availableBooks = BorrowService.getAvailableBooksForBorrow();
                    response.getWriter().write(gson.toJson(availableBooks));
                    break;

                case "/active":
                    List<BorrowDTO> activeBorrows = BorrowService.getActiveBorrows();
                    response.getWriter().write(gson.toJson(activeBorrows));
                    break;

                default:
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    response.getWriter().write("{\"error\": \"Endpoint not found\"}");
                    break;
            }
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        String pathInfo = request.getPathInfo();

        if ("/borrow".equals(pathInfo)) {
            try {
                Map<String, Object> data = parseJsonBody(request);
                int bookId = ((Number) data.get("bookId")).intValue();
                int personId = ((Number) data.get("memberId")).intValue();
                int userId = ((Number) data.get("userId")).intValue();

                BorrowService.borrowBook(bookId, personId, userId);
                response.getWriter().write("{\"message\": \"Book borrowed successfully\"}");

            } catch (IllegalArgumentException e) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("{\"error\": \"" + e.getMessage() + "\"}");
            } catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("{\"error\": \"Invalid JSON or parameters: " + e.getMessage() + "\"}");
            }
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.getWriter().write("{\"error\": \"Endpoint not found\"}");
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        String pathInfo = request.getPathInfo();

        if ("/return".equals(pathInfo)) {
            try {
                Map<String, Object> data = parseJsonBody(request);
                int borrowId = ((Number) data.get("borrowId")).intValue();

                BorrowService.returnBook(borrowId);
                response.getWriter().write("{\"message\": \"Book returned successfully\"}");

            } catch (IllegalArgumentException e) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("{\"error\": \"" + e.getMessage() + "\"}");
            } catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("{\"error\": \"Invalid JSON or borrowId: " + e.getMessage() + "\"}");
            }
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.getWriter().write("{\"error\": \"Endpoint not found\"}");
        }
    }

    private Map<String, Object> parseJsonBody(HttpServletRequest request) throws IOException {
        StringBuilder buffer = new StringBuilder();
        try (BufferedReader reader = request.getReader()) {
            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
        }
        return gson.fromJson(buffer.toString(), Map.class);
    }
}
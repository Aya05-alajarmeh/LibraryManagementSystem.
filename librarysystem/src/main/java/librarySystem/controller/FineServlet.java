package librarySystem.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import librarySystem.DTO.FineDTO;
import librarySystem.service.FineService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/api/fines/*")
public class FineServlet extends HttpServlet {
 
    private final Gson gson = new GsonBuilder()
    .registerTypeAdapter(java.time.LocalDate.class, new com.google.gson.JsonSerializer<java.time.LocalDate>() {
        @Override
        public com.google.gson.JsonElement serialize(java.time.LocalDate src, java.lang.reflect.Type typeOfSrc, com.google.gson.JsonSerializationContext context) {
            return new com.google.gson.JsonPrimitive(src.toString());
        }
    })
    .create();
   
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        String pathInfo = request.getPathInfo();

        if ("/unpaid".equals(pathInfo)) {
            try {
                List<FineDTO> unpaidFines = FineService.getUnpaidFines();
                response.getWriter().write(gson.toJson(unpaidFines));
            } catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.getWriter().write("{\"error\": \"" + e.getMessage() + "\"}");
            }
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.getWriter().write("{\"error\": \"Endpoint not found\"}");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        String pathInfo = request.getPathInfo();

        if ("/pay".equals(pathInfo)) {
            try {
                int fineId = Integer.parseInt(request.getParameter("fineId"));
                boolean success = FineService.payFine(fineId);
                
                if (success) {
                    response.getWriter().write("{\"message\": \"Fine paid successfully\"}");
                } else {
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    response.getWriter().write("{\"error\": \"Failed to pay fine\"}");
                }
            } catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("{\"error\": \"" + e.getMessage() + "\"}");
            }
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.getWriter().write("{\"error\": \"Endpoint not found\"}");
        }
    }
}
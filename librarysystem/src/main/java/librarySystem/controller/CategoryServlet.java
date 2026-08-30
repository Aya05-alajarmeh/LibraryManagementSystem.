

package librarySystem.controller;

import com.google.gson.Gson;
import librarySystem.DTO.CategoryDTO;
import librarySystem.Model.Category;
import librarySystem.service.CategoryService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/api/categories")
public class CategoryServlet extends HttpServlet {
    private final Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        try {
            List<CategoryDTO> categories = CategoryService.getAllCategories();
            response.getWriter().write(gson.toJson(categories));
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }
   
   @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
      
        try {
            String name = request.getParameter("categoryName");
            String desc = request.getParameter("description");

            Category category = new Category();
            category.setCategoryName(name);
            category.setDescription(desc);

            CategoryService service = new CategoryService(category);
            service.save();

            response.getWriter().write("{\"message\": \"Category added successfully\"}");
        } catch ( RuntimeException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\": \"" + e.getMessage() + "\"}");
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\": \"Invalid parameters: " + e.getMessage() + "\"}");
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
      
        try {
            int id = Integer.parseInt(request.getParameter("categoryId"));
            
            Category category = new Category();
            category.setCategoryId(id);
            category.setCategoryName(request.getParameter("categoryName"));
            category.setDescription(request.getParameter("description")); 

            CategoryService service = new CategoryService(category);
            service.save();

            response.getWriter().write("{\"message\": \"Category updated successfully\"}");
        } catch ( RuntimeException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\": \"" + e.getMessage() + "\"}");
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\": \"Invalid categoryId or parameters: " + e.getMessage() + "\"}");
        }
    }
 
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
      
        try {
            int id = Integer.parseInt(request.getParameter("categoryId"));
            CategoryService.delete(id);
            response.getWriter().write("{\"message\": \"Category deleted successfully\"}");
        } catch (RuntimeException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\": \"" + e.getMessage() + "\"}");
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\": \"Invalid categoryId format\"}");
        }
    }
}
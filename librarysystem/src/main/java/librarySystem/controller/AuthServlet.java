package librarySystem.controller;
import com.google.gson.Gson;
import librarySystem.DTO.UserDTO;
import librarySystem.service.AuthService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;

@WebServlet("/api/auth/login")
public class AuthServlet extends HttpServlet {
    private final AuthService authService = new AuthService();
    private final Gson gson = new Gson();

    private static class LoginRequest {
        private String username;
        private String password;

        public String getUsername() { return username; }
        public String getPassword() { return password; }
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        try {
           StringBuilder sb = new StringBuilder();
            try (BufferedReader reader = request.getReader()) {
                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
            }

             LoginRequest loginReq = gson.fromJson(sb.toString(), LoginRequest.class);

            if (loginReq == null || loginReq.getUsername() == null || loginReq.getPassword() == null) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("{\"error\": \"Invalid request payload\"}");
                return;
            }

            String username = loginReq.getUsername();
            String password = loginReq.getPassword();

            UserDTO loggedInUser = authService.login(username, password);

            if (loggedInUser != null) {
                HttpSession session = request.getSession();
                session.setAttribute("user", loggedInUser);
            
                response.setStatus(HttpServletResponse.SC_OK);
                response.getWriter().write(gson.toJson(loggedInUser));
            } 
        } catch (IllegalArgumentException e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401
            response.getWriter().write("{\"error\": \"" + e.getMessage() + "\"}");

        } catch (IllegalStateException e) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN); // 403
            response.getWriter().write("{\"error\": \"" + e.getMessage() + "\"}");

        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\": \"An error occurred while processing your request\"}");
        }
    }
}
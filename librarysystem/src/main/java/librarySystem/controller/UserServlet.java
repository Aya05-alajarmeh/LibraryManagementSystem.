package librarySystem.controller;

import com.google.gson.Gson;
import librarySystem.DTO.UserDTO;
import librarySystem.Model.User;
import librarySystem.Enum.enRole;
import librarySystem.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

@WebServlet("/api/users/*")
public class UserServlet extends HttpServlet {
    private final Gson gson = new Gson();

    private boolean isAdmin(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("{\"error\": \"Unauthorized\"}");
            return false;
        }
        UserDTO user = (UserDTO) session.getAttribute("user");
        if (user.getRole() != enRole.ADMIN) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.getWriter().write("{\"error\": \"Admins only access\"}");
            return false;
        }
        return true;
    }

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
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        if (!isAdmin(request, response)) return;

        try {
            String idParam = request.getParameter("personId");

            if (idParam != null && !idParam.trim().isEmpty()) {
                int personId = Integer.parseInt(idParam);
                User user = UserService.find(personId);
                
                if (user != null) {
                    response.getWriter().write(gson.toJson(user));
                } else {
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    response.getWriter().write("{\"error\": \"User not found\"}");
                }
            } else {
                List<UserDTO> users = UserService.getAllUsers();
                response.getWriter().write(gson.toJson(users));
            }
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\": \"Invalid personId format\"}");
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        if (!isAdmin(request, response)) return;

        try {
            User requestUser = parseJsonBody(request, User.class);
            
            User user = new User();
            user.setPersonId(-1);
            user.setFirstName(requestUser.getFirstName());
            user.setSecondName(requestUser.getSecondName());
            user.setThirdName(requestUser.getThirdName());
            user.setLastName(requestUser.getLastName());
            user.setEmail(requestUser.getEmail());
            user.setPhoneNumber(requestUser.getPhoneNumber());
            user.setUsername(requestUser.getUsername());
            user.setRole(requestUser.getRole());

            UserService userService = new UserService(user);
            userService.save(requestUser.getPassword());

            response.getWriter().write("{\"message\": \"User added successfully\"}");
        } catch (RuntimeException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\": \"" + e.getMessage() + "\"}");
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\": \"Invalid JSON payload: " + e.getMessage() + "\"}");
        }
    } 
    
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        if (!isAdmin(request, response)) return;

        String action = request.getParameter("action");

        if ("changePassword".equalsIgnoreCase(action)) {
            try {
                HttpSession session = request.getSession(false);
                UserDTO currentUser = (UserDTO) session.getAttribute("user");
                
                User fullUser = UserService.find(currentUser.getPersonId());
                if (fullUser == null) {
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    response.getWriter().write("{\"error\": \"User not found\"}");
                    return;
                }

                String newPassword = request.getParameter("newPassword");
                UserService userService = new UserService(fullUser);
                userService.changePassword(newPassword);

                response.getWriter().write("{\"message\": \"Password changed successfully\"}");
            } catch (RuntimeException e) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("{\"error\": \"" + e.getMessage() + "\"}");
            } catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("{\"error\": \"Failed to change password: " + e.getMessage() + "\"}");
            }
        } else {
            try {
                User requestUser = parseJsonBody(request, User.class);
                int id = requestUser.getPersonId();
                
                User user = UserService.find(id);
                if (user != null) {
                    user.setFirstName(requestUser.getFirstName());
                    user.setSecondName(requestUser.getSecondName());
                    user.setThirdName(requestUser.getThirdName());
                    user.setLastName(requestUser.getLastName());
                    user.setEmail(requestUser.getEmail());
                    user.setPhoneNumber(requestUser.getPhoneNumber());

                    UserService userService = new UserService(user);
                    userService.save(user.getPassword());

                    response.getWriter().write("{\"message\": \"User updated successfully\"}");
                } else {
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    response.getWriter().write("{\"error\": \"User not found\"}");
                }
            } catch (RuntimeException e) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("{\"error\": \"" + e.getMessage() + "\"}");
            } catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("{\"error\": \"Update failed or invalid parameters: " + e.getMessage() + "\"}");
            }
        }
    }
 
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        if (!isAdmin(request, response)) return;

        try {
            int id = Integer.parseInt(request.getParameter("personId"));
            UserService.delete(id);
            response.getWriter().write("{\"message\": \"User deleted successfully\"}");
        } catch (RuntimeException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\": \"" + e.getMessage() + "\"}");
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\": \"Invalid ID format\"}");
        }
    }
}
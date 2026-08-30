package librarySystem.controller;

import com.google.gson.Gson;
import librarySystem.DTO.MemberDTO;
import librarySystem.Model.Member;
import librarySystem.service.MemberService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

@WebServlet("/api/members")
public class MemberServlet extends HttpServlet {

    private final Gson gson = new com.google.gson.GsonBuilder()
        .registerTypeAdapter(java.time.LocalDate.class, (com.google.gson.JsonSerializer<java.time.LocalDate>) (src, typeOfSrc, context) -> 
            new com.google.gson.JsonPrimitive(src.toString()))
        .registerTypeAdapter(java.time.LocalDate.class, (com.google.gson.JsonDeserializer<java.time.LocalDate>) (json, typeOfT, context) -> 
            java.time.LocalDate.parse(json.getAsString()))
        .create();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
      
        try {
            String idParam = request.getParameter("personId");

            if (idParam != null && !idParam.trim().isEmpty()) {
                int personId = Integer.parseInt(idParam);
                Member member = MemberService.find(personId);
                
                if (member != null) {
                    response.getWriter().write(gson.toJson(member));
                } else {
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    response.getWriter().write("{\"error\": \"Member not found\"}");
                }
            } else {
                List<MemberDTO> members = MemberService.getAllMembers();
                response.getWriter().write(gson.toJson(members));
            }
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\": \"Invalid personId format\"}");
        } catch (RuntimeException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\": \"" + e.getMessage() + "\"}");
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\": \"" + e.getMessage() + "\"}");
        }
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
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        
        try {
            Member requestMember = parseJsonBody(request, Member.class);
            
            Member member = new Member();
            member.setPersonId(-1);
            member.setFirstName(requestMember.getFirstName());
            member.setSecondName(requestMember.getSecondName());
            member.setThirdName(requestMember.getThirdName());
            member.setLastName(requestMember.getLastName());
            member.setEmail(requestMember.getEmail());
            member.setPhoneNumber(requestMember.getPhoneNumber());

            MemberService memberService = new MemberService(member);
            memberService.save();

            response.getWriter().write("{\"message\": \"Member added successfully\", \"personId\": " + member.getPersonId() + "}");
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
      
        try {
            Member requestMember = parseJsonBody(request, Member.class);
            
            String idParam = request.getParameter("personId");
            int id = -1;
            
            if (idParam != null && !idParam.trim().isEmpty()) {
                id = Integer.parseInt(idParam);
            } else if (requestMember.getPersonId() > 0) {
                id = requestMember.getPersonId();
            }

            if (id == -1) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("{\"error\": \"personId is required for update\"}");
                return;
            }

            Member member = MemberService.find(id);
            
            if (member != null) {
                member.setFirstName(requestMember.getFirstName());
                member.setSecondName(requestMember.getSecondName());
                member.setThirdName(requestMember.getThirdName());
                member.setLastName(requestMember.getLastName());
                member.setEmail(requestMember.getEmail());
                member.setPhoneNumber(requestMember.getPhoneNumber());

                MemberService memberService = new MemberService(member);
                memberService.save();
                
                response.getWriter().write("{\"message\": \"Member updated successfully\"}");
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.getWriter().write("{\"error\": \"Member not found\"}");
            }
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\": \"Invalid personId format\"}");
        } catch (RuntimeException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\": \"" + e.getMessage() + "\"}");
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\": \"Invalid JSON payload or update failed: " + e.getMessage() + "\"}");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
      
        String idParam = request.getParameter("personId");
        if (idParam == null || idParam.trim().isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\": \"personId parameter is required\"}");
            return;
        }

        try {
            int id = Integer.parseInt(idParam);
            MemberService.delete(id);
            response.getWriter().write("{\"message\": \"Member deleted successfully\"}");
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\": \"Invalid personId format\"}");
        } catch (RuntimeException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\": \"" + e.getMessage() + "\"}");
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }
}

package librarySystem.service;

import librarySystem.DAO.MemberDAO;
import librarySystem.DAO.UserDAO;
import librarySystem.DTO.MemberDTO; 
import librarySystem.Model.Member;
import java.util.List;

public class MemberService {

    public enum Mode { Add, Update }

    private Mode mode = Mode.Add;
    private Member member; 

    public MemberService() {
        this.member = new Member();
        this.mode = Mode.Add;
    }

    public MemberService(Member member) {
        this.member = member;
        this.mode = (member != null && member.getPersonId() != -1 && member.getPersonId() != 0 ) ? Mode.Update : Mode.Add;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    private void _addNewMember() {
        int newId = MemberDAO.addMember(this.member);
        if (newId != -1) {
            this.member.setPersonId(newId);
        } else {
            throw new RuntimeException("Failed to add member to database");
        }
    }

    private void _updateMember() {
        boolean updated = MemberDAO.updateMember(this.member);
        if (!updated) {
            throw new RuntimeException("Failed to update member in database");
        }
    }
    
      private static final java.util.regex.Pattern EMAIL_PATTERN = 
    java.util.regex.Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$");

   public static boolean isValidEmail(String email) {
    if (email == null) return false;
    return EMAIL_PATTERN.matcher(email.trim()).matches();
}

    public void save() {



        if (member == null) {
            throw new IllegalArgumentException("Invalid member data");
        }

        if (!isValidEmail(member.getEmail())) {
        throw new IllegalArgumentException("Invalid email format");
    }

        switch (mode) {
            case Add:
                  if (member.getEmail() != null && !member.getEmail().trim().isEmpty()) {
            if (UserDAO.isEmailExists(member.getEmail().trim())) {
                throw new IllegalArgumentException("Email is already registered with another account");
            }
        }
        
                _addNewMember();
                mode = Mode.Update;
                break;

            case Update:
                _updateMember();
                break;
        }
    }

    public static Member find(int personId) {
        if (personId <= 0) {
            throw new IllegalArgumentException("Invalid personId");
        }
        return MemberDAO.getMemberById(personId);
    }

    public static List<MemberDTO> getAllMembers() {
        try {
            return MemberDAO.getAllMembers().stream().map(MemberDTO::fromMember)
                     .collect(java.util.stream.Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch members list: " + e.getMessage());
        }
    }

    public static void delete(int personId) {
        if (personId <= 0) {
            throw new IllegalArgumentException("Invalid personId");
        }
        boolean deleted = MemberDAO.deleteMember(personId);
        if (!deleted) {
            throw new RuntimeException("Delete failed or member not found");
        }
    }
}
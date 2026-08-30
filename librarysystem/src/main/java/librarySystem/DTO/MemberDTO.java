package librarySystem.DTO;

import librarySystem.Model.Member;
import java.time.LocalDate;

public class MemberDTO {
    private int personId;
    private String fullName;
    private String email;
    private String phoneNumber;
    private LocalDate registrationDate;

    public MemberDTO() {}

    public MemberDTO(int personId, String fullName, String email, String phoneNumber, LocalDate registrationDate) {
        this.personId = personId;
        this.fullName = fullName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.registrationDate = registrationDate;
    }

    // Getters and Setters
    public int getPersonId() { return personId; }
    public void setPersonId(int personId) { this.personId = personId; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public LocalDate getRegistrationDate() { return registrationDate; }
    public void setRegistrationDate(LocalDate registrationDate) { this.registrationDate = registrationDate; }

    public static MemberDTO fromMember(Member member) {
        if (member == null) return null;

        String fullName = member.getFirstName() + " " + member.getLastName();

        return new MemberDTO(
            member.getPersonId(),
            fullName.trim(),
            member.getEmail(),
            member.getPhoneNumber(),
            member.getRegistrationDate()
        );
    }
}
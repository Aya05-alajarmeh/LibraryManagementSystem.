
package librarySystem.Model;
import java.time.LocalDate;

public class Member extends Person {
    private LocalDate registrationDate;
   
    public Member() {
        super();
        this.registrationDate = LocalDate.now();

    }



    public Member(int personId, String firstName, String secondName, String thirdName, String lastName, String email, String phoneNumber, boolean isDeleted, LocalDate registrationDate) {
        super(personId, firstName, secondName, thirdName, lastName, email, phoneNumber, isDeleted);
        this.registrationDate = registrationDate != null ? registrationDate : LocalDate.now();
    }

    // Getters and Setters
    public LocalDate getRegistrationDate() { return registrationDate; }
    public void setRegistrationDate(LocalDate registrationDate) { this.registrationDate = registrationDate; }
 
}

package librarySystem.Model;
public class Person {
    private int personId;
    private String firstName;
    private String secondName;
    private String thirdName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private boolean isDeleted;

    // Default Constructor
    public Person() {}

    // Constructor
    public Person(int personId, String firstName, String secondName, String thirdName, String lastName, String email, String phoneNumber, boolean isDeleted) {
        this.personId = personId;
        this.firstName = firstName;
        this.secondName = secondName;
        this.thirdName = thirdName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.isDeleted = isDeleted;
    }

    // Getters and Setters
    public int getPersonId() { return personId; }
    public void setPersonId(int personId) { this.personId = personId; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getSecondName() { return secondName; }
    public void setSecondName(String secondName) { this.secondName = secondName; }

    public String getThirdName() { return thirdName; }
    public void setThirdName(String thirdName) { this.thirdName = thirdName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public boolean isDeleted() { return isDeleted; }
    public void setDeleted(boolean deleted) { isDeleted = deleted; }
}
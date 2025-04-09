
import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class Person {
    protected static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    private String personId;
    private String fullName;
    private Date dateOfBirth;
    private String contactInfo;

    public Person(String personId, String fullName, Date dob, String contactInfo) {
        this.personId = personId;
        this.fullName = fullName;
        this.dateOfBirth = dob;
        this.contactInfo = contactInfo;
    }

    public String getPersonId() { return personId; }
    public String getFullName() { return fullName; }
    public Date getDateOfBirth() { return dateOfBirth; }
    public String getContactInfo() { return contactInfo; }

    public String toString() {
        return fullName + " (ID: " + personId + ")";
    }
}


import java.util.Date;

public abstract class Staff extends Person {
    private String staffId;

    public Staff(String personId, String fullName, Date dob, String contactInfo, String staffId) {
        super(personId, fullName, dob, contactInfo);
        this.staffId = staffId;
    }

    public String getStaffId() { return staffId; }

    public String toString() {
        return getFullName() + " (Staff ID: " + staffId + ")";
    }
}

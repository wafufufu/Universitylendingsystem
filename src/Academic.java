import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Academic extends Staff {
    private String expertise;
    private List<Student> supervisedStudents;

    public Academic(String personId, String fullName, Date dob, String contactInfo, String staffId, String expertise) {
        super(personId, fullName, dob, contactInfo, staffId);
        this.expertise = expertise;
        this.supervisedStudents = new ArrayList<>();
    }

    public String getExpertise() {
        return expertise;
    }

    public void addSupervisedStudent(Student student) {
        supervisedStudents.add(student);
    }

    public String toString() {
        return super.toString() + " - Academic (Expertise: " + expertise + ")";
    }

    public String toDataString() {
        return String.format("%s|%s|%s|%s|%s|%s",
                getPersonId(), getFullName(), Person.DATE_FORMAT.format(getDateOfBirth()),
                getContactInfo(), getStaffId(), expertise);
    }

    public static Academic fromDataString(String line) {
        try {
            String[] parts = line.split("\\|");
            String personId = parts[0];
            String fullName = parts[1];
            Date dob = Person.DATE_FORMAT.parse(parts[2]);
            String contactInfo = parts[3];
            String staffId = parts[4];
            String expertise = parts[5];
            return new Academic(personId, fullName, dob, contactInfo, staffId, expertise);
        } catch (Exception e) {
            System.out.println("Failed to load academic: " + line);
            return null;
        }
    }
}

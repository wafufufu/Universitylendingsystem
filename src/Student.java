import java.util.*;

public class Student extends Person {
    private String studentId;
    private List<LendingRecord> lendingRecords;

    public Student(String personId, String fullName, Date dob, String contactInfo, String studentId) {
        super(personId, fullName, dob, contactInfo);
        this.studentId = studentId;
        this.lendingRecords = new ArrayList<>();
    }

    public String getStudentId() {
        return studentId;
    }

    public List<LendingRecord> getLendingRecords() {
        return lendingRecords;
    }

    public void addLendingRecord(LendingRecord record) {
        this.lendingRecords.add(record);
    }

    public String toDataString() {
        return String.format("%s|%s|%s|%s|%s",
                getPersonId(), getFullName(), Person.DATE_FORMAT.format(getDateOfBirth()), getContactInfo(), studentId);
    }

    public static Student fromDataString(String line) {
        try {
            String[] parts = line.split("\\|");
            String personId = parts[0];
            String name = parts[1];
            Date dob = Person.DATE_FORMAT.parse(parts[2]);
            String contact = parts[3];
            String studentId = parts[4];
            return new Student(personId, name, dob, contact, studentId);
        } catch (Exception e) {
            System.out.println("Failed to load student: " + line);
            return null;
        }
    }
}

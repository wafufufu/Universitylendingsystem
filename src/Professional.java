import java.util.Date;

public class Professional extends Staff {
    private String department;

    public Professional(String personId, String fullName, Date dob, String contactInfo, String staffId, String department) {
        super(personId, fullName, dob, contactInfo, staffId);
        this.department = department;
    }

    public String getDepartment() {
        return department;
    }

    @Override
    public String toString() {
        return super.toString() + " - Professional (Department: " + department + ")";
    }

    // Converts Professional object to a line for saving
    public String toDataString() {
        return String.format("%s|%s|%s|%s|%s|%s",
                getPersonId(),
                getFullName(),
                Person.DATE_FORMAT.format(getDateOfBirth()),
                getContactInfo(),
                getStaffId(),
                department);
    }

    // Parses line from professionals.txt into Professional object
    public static Professional fromDataString(String line) {
        try {
            String[] parts = line.split("\\|");
            String personId = parts[0];
            String fullName = parts[1];
            Date dob = Person.DATE_FORMAT.parse(parts[2]);
            String contactInfo = parts[3];
            String staffId = parts[4];
            String department = parts[5];
            return new Professional(personId, fullName, dob, contactInfo, staffId, department);
        } catch (Exception e) {
            System.out.println("Failed to load professional: " + line);
            return null;
        }
    }
}

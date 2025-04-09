import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class LendingRecord {
    private String recordId;
    private Person borrower;
    private Equipment equipment;
    private Date lendingDate;
    private Date returnDate;
    private String status; // e.g., Borrowed, Returned, Overdue

    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    public LendingRecord(String recordId, Person borrower, Equipment equipment, Date lendingDate, Date returnDate, String status) {
        this.recordId = recordId;
        this.borrower = borrower;
        this.equipment = equipment;
        this.lendingDate = lendingDate;
        this.returnDate = returnDate;
        this.status = status;
    }

    // --- Getters ---
    public String getRecordId() {
        return recordId;
    }

    public Person getBorrower() {
        return borrower;
    }

    public Equipment getEquipment() {
        return equipment;
    }

    public Date getLendingDate() {
        return lendingDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public String getStatus() {
        return status;
    }

    // --- Setters (Added) ---
    public void setBorrower(Person borrower) {
        this.borrower = borrower;
    }

    public void setEquipment(Equipment equipment) {
        this.equipment = equipment;
    }

    public void setLendingDate(Date lendingDate) {
        this.lendingDate = lendingDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String toString() {
        return String.format("Record ID: %s, Borrower: %s, Equipment: %s, Lending Date: %s, Return Date: %s, Status: %s",
                recordId,
                borrower.getFullName(),
                equipment.getName(),
                DATE_FORMAT.format(lendingDate),
                returnDate != null ? DATE_FORMAT.format(returnDate) : "Not Returned",
                status);
    }

    public String toDataString() {
        return String.format("%s|%s|%s|%s|%s|%s",
                recordId,
                borrower.getPersonId(),
                equipment.getEquipmentId(),
                DATE_FORMAT.format(lendingDate),
                returnDate != null ? DATE_FORMAT.format(returnDate) : "null",
                status);
    }

    public static LendingRecord fromDataString(String line, List<Student> students, List<Staff> staff, InventoryService inventoryService) {
        try {
            String[] parts = line.split("\\|");
            String recordId = parts[0];
            String borrowerId = parts[1];
            String equipmentId = parts[2];
            Date lendingDate = DATE_FORMAT.parse(parts[3]);
            Date returnDate = parts[4].equals("null") ? null : DATE_FORMAT.parse(parts[4]);
            String status = parts[5];

            Person borrower = findPersonById(borrowerId, students, staff);
            Equipment equipment = inventoryService.findEquipmentById(equipmentId);

            if (borrower != null && equipment != null) {
                return new LendingRecord(recordId, borrower, equipment, lendingDate, returnDate, status);
            }
        } catch (Exception e) {
            System.out.println("Error parsing lending record: " + line);
        }
        return null;
    }

    private static Person findPersonById(String id, List<Student> students, List<Staff> staff) {
        for (Student s : students) {
            if (s.getPersonId().equals(id)) return s;
        }
        for (Staff s : staff) {
            if (s.getPersonId().equals(id)) return s;
        }
        return null;
    }
}

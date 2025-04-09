import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

public class LendingService {
    private List<LendingRecord> lendingRecords = new ArrayList<>();
    private List<LendingRecord> records = new ArrayList<>();
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    public void loadLendingRecords(String path, List<Student> students, List<Staff> staff, InventoryService inventoryService) {
        records = FileUtil.loadLendingRecords(path, students, staff, inventoryService);
    }

    public List<LendingRecord> getAllRecords() {
        return records;
    }

    public LendingRecord findRecordById(String id) {
        for (LendingRecord r : records) {
            if (r.getRecordId().equalsIgnoreCase(id)) {
                return r;
            }
        }
        return null;
    }

    public void addLendingRecord(Scanner scanner, List<Student> students, List<Staff> staff, InventoryService inventoryService) {
        System.out.print("Enter borrower ID: ");
        String borrowerId = scanner.nextLine();
        Person borrower = findPersonById(borrowerId, students, staff);
        if (borrower == null) {
            System.out.println("Borrower not found.");
            return;
        }

        System.out.print("Enter equipment ID: ");
        String equipmentId = scanner.nextLine();
        Equipment equipment = inventoryService.findEquipmentById(equipmentId);
        if (equipment == null) {
            System.out.println("Equipment not found.");
            return;
        }

        System.out.print("Enter lending date (yyyy-MM-dd): ");
        Date lendingDate = readDate(scanner);
        System.out.print("Enter return date (yyyy-MM-dd or blank if unknown): ");
        String returnDateStr = scanner.nextLine();
        Date returnDate = returnDateStr.isEmpty() ? null : parseDate(returnDateStr);

        String recordId = "R" + new Random().nextInt(10000);
        LendingRecord newRecord = new LendingRecord(recordId, borrower, equipment, lendingDate, returnDate, "Borrowed");
        records.add(newRecord);
        System.out.println("Lending record added.");
    }

    public void updateLendingRecord(Scanner scanner, List<Student> students, List<Staff> staff, InventoryService inventoryService) {
        System.out.print("Enter record ID to update: ");
        String recordId = scanner.nextLine();
        LendingRecord record = findRecordById(recordId);
        if (record == null) {
            System.out.println("Record not found.");
            return;
        }

        System.out.println("Leave field blank to keep current value.");

        // Borrower update
        System.out.print("Enter new borrower ID (" + record.getBorrower().getPersonId() + "): ");
        String newBorrowerId = scanner.nextLine();
        if (!newBorrowerId.isEmpty()) {
            Person newBorrower = findPersonById(newBorrowerId, students, staff);
            if (newBorrower != null) {
                record.setBorrower(newBorrower);
            } else {
                System.out.println("Borrower not found. Keeping original.");
            }
        }

        // Equipment update
        System.out.print("Enter new equipment ID (" + record.getEquipment().getEquipmentId() + "): ");
        String newEquipmentId = scanner.nextLine();
        if (!newEquipmentId.isEmpty()) {
            Equipment newEquipment = inventoryService.findEquipmentById(newEquipmentId);
            if (newEquipment != null) {
                record.setEquipment(newEquipment);
            } else {
                System.out.println("Equipment not found. Keeping original.");
            }
        }

        // Lending date update
        System.out.print("Enter new lending date (" + DATE_FORMAT.format(record.getLendingDate()) + "): ");
        String lendingDateStr = scanner.nextLine();
        if (!lendingDateStr.isEmpty()) {
            record.setLendingDate(parseDate(lendingDateStr));
        }

        // Return date update
        System.out.print("Enter new return date (" + (record.getReturnDate() != null ? DATE_FORMAT.format(record.getReturnDate()) : "null") + "): ");
        String returnDateStr = scanner.nextLine();
        if (!returnDateStr.isEmpty()) {
            record.setReturnDate(parseDate(returnDateStr));
        }

        // Status update
        System.out.print("Enter new status (Borrowed/Returned/Overdue) [" + record.getStatus() + "]: ");
        String newStatus = scanner.nextLine();
        if (!newStatus.isEmpty()) {
            record.setStatus(newStatus);
        }

        System.out.println("Lending record updated.");
    }

    public void deleteLendingRecord(Scanner scanner) {
        System.out.print("Enter record ID to delete: ");
        String recordId = scanner.nextLine();
        LendingRecord record = findRecordById(recordId);
        if (record != null) {
            records.remove(record);
            System.out.println("Record deleted.");
        } else {
            System.out.println("Record not found.");
        }
    }

    private Person findPersonById(String id, List<Student> students, List<Staff> staff) {
        for (Student s : students) {
            if (s.getPersonId().equalsIgnoreCase(id)) return s;
        }
        for (Staff s : staff) {
            if (s.getPersonId().equalsIgnoreCase(id)) return s;
        }
        return null;
    }

    private Date parseDate(String dateStr) {
        try {
            return DATE_FORMAT.parse(dateStr);
        } catch (Exception e) {
            System.out.println("Invalid date format. Setting today's date.");
            return new Date();
        }
    }

    private Date readDate(Scanner scanner) {
        while (true) {
            try {
                return DATE_FORMAT.parse(scanner.nextLine());
            } catch (Exception e) {
                System.out.print("Invalid format. Try again (yyyy-MM-dd): ");
            }
        }
    }
    public void saveLendingRecords(String path) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
            for (LendingRecord record : records) {  // ✅ CORRECT LIST
                writer.write(record.toDataString());
                writer.newLine();
            }
            System.out.println("Lending records saved successfully.");
        } catch (IOException e) {
            System.err.println("Failed to save lending records: " + e.getMessage());
        }
    }
}

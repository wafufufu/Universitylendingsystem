import java.util.*;

public class AdminService {
    private InventoryService inventoryService;
    private LendingService lendingService;
    private List<Student> students;
    private List<Staff> staff;

    public AdminService(InventoryService inventoryService, LendingService lendingService, List<Student> students, List<Staff> staff) {
        this.inventoryService = inventoryService;
        this.lendingService = lendingService;
        this.students = students;
        this.staff = staff;
    }

    public void viewAllEquipment() {
        inventoryService.getAllEquipments().forEach(System.out::println);
    }

    public void viewAllLendingRecords() {
        lendingService.getAllRecords().forEach(System.out::println);
    }

    public void viewAllStudents() {
        students.forEach(System.out::println);
    }

    public void viewAllStaff() {
        staff.forEach(System.out::println);
    }

    public void addEquipment(Scanner scanner) {
        System.out.print("Name: ");
        String name = scanner.nextLine();
        System.out.print("Status (Available/Borrowed): ");
        String status = scanner.nextLine();
        System.out.print("Condition (Brand New, Good, Needs Maintenance, Damaged, Out of Service): ");
        String condition = scanner.nextLine();

        String equipmentId = "E" + new Random().nextInt(10000);
        Equipment equipment = new Equipment(equipmentId, name, status, new Date(), condition);
        inventoryService.addEquipment(equipment);
        System.out.println("Equipment added successfully.");
    }

    public void editEquipment(Scanner scanner) {
        System.out.print("Enter Equipment ID to edit: ");
        String id = scanner.nextLine();

        Equipment equipment = inventoryService.findEquipmentById(id);
        if (equipment == null) {
            System.out.println("Equipment not found.");
            return;
        }

        System.out.println("Current Status: " + equipment.getStatus());
        System.out.print("Enter new Status (Available/Borrowed): ");
        String status = scanner.nextLine();
        equipment.setStatus(status);

        System.out.println("Current Condition: " + equipment.getCondition());
        System.out.print("Enter new Condition: ");
        String condition = scanner.nextLine();
        equipment.setCondition(condition);

        System.out.println("Equipment updated successfully.");
    }

    public void removeOutOfServiceEquipment() {
        List<Equipment> toRemove = new ArrayList<>();
        for (Equipment e : inventoryService.getAllEquipments()) {
            if (e.getCondition().equalsIgnoreCase("Out of Service")) {
                toRemove.add(e);
            }
        }
    
        toRemove.forEach(e -> inventoryService.removeEquipment(e.getEquipmentId()));
    
        // ✅ Save the updated equipment list to file
        inventoryService.saveEquipments("data/equipments.txt");
    
        System.out.println(toRemove.size() + " out-of-service equipment removed.");
    }
    
        

    public void addLendingRecord(Scanner scanner) {
        lendingService.addLendingRecord(scanner, students, staff, inventoryService);
    }

    public void updateLendingRecord(Scanner scanner) {
        lendingService.updateLendingRecord(scanner, students, staff, inventoryService);
    }

    public void deleteLendingRecord(Scanner scanner) {
        lendingService.deleteLendingRecord(scanner);
    }
    public void manageLendingRecords(Scanner scanner) {
        while (true) {
            System.out.println("\n--- Manage Lending Records ---");
            System.out.println("1. Add Lending Record");
            System.out.println("2. Update Lending Record");
            System.out.println("3. Delete Lending Record");
            System.out.println("4. Back to Main Menu");
            System.out.print("Choose an option: ");
            String input = scanner.nextLine();
    
            switch (input) {
                case "1":
                    addLendingRecord(scanner);
                    break;
                case "2":
                    updateLendingRecord(scanner);
                    break;
                case "3":
                    deleteLendingRecord(scanner);
                    break;
                case "4":
                    return; // Exit back to main menu
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }
    
    
}

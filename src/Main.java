import java.util.*;

public class Main {
    private static List<Student> students = new ArrayList<>();
    private static List<Staff> staff = new ArrayList<>();
    private static InventoryService inventoryService = new InventoryService();
    private static LendingService lendingService = new LendingService();
    private static AdminService adminService;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        loadAllData();
        adminService = new AdminService(inventoryService, lendingService, students, staff);

        boolean exit = false;
        while (!exit) {
            System.out.println("\n==== University Equipment Lending Management System ====");
            System.out.println("1. View All Students");
            System.out.println("2. View All Staff");
            System.out.println("3. View All Equipment");
            System.out.println("4. View All Lending Records");
            System.out.println("5. Add Equipment");
            System.out.println("6. Remove Out of Service Equipment");
            System.out.println("7. Edit Equipment (Status/Condition)");
            System.out.println("8. Manage Lending Records");
            System.out.println("9. Exit");
            System.out.print("Enter your choice: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    adminService.viewAllStudents();
                    break;
                case "2":
                    adminService.viewAllStaff();
                    break;
                case "3":
                    adminService.viewAllEquipment();
                    break;
                case "4":
                    adminService.viewAllLendingRecords();
                    break;
                case "5":
                    adminService.addEquipment(scanner);
                    break;
                case "6":
                    adminService.removeOutOfServiceEquipment();
                    break;
                case "7":
                    adminService.editEquipment(scanner);
                    break;
                case "8":
                    adminService.manageLendingRecords(scanner);
                    break;
                case "9":
                    // Save everything before exit
                    inventoryService.saveEquipments("data/equipments.txt");
                    lendingService.saveLendingRecords("data/lending_records.txt");
                    System.out.println("Exiting system...");
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        }
        scanner.close();
    }

    private static void loadAllData() {
        students = FileUtil.loadStudents("data/students.txt");
        staff = FileUtil.loadStaff("data/academics.txt", "data/professionals.txt");
        inventoryService.loadEquipments("data/equipments.txt");
        lendingService.loadLendingRecords("data/lending_records.txt", students, staff, inventoryService);
    }
}

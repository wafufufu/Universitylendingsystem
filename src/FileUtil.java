import java.io.*;
import java.util.*;

public class FileUtil {

    public static List<Student> loadStudents(String path) {
        List<Student> list = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                Student s = Student.fromDataString(line);
                if (s != null) list.add(s);
            }
        } catch (IOException e) {
            System.out.println("Error loading students: " + e.getMessage());
        }
        return list;
    }

    public static void saveStudents(String path, List<Student> students) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(path))) {
            for (Student s : students) {
                bw.write(s.toDataString());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving students: " + e.getMessage());
        }
    }

    public static List<Staff> loadStaff(String academicPath, String professionalPath) {
        List<Staff> list = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(academicPath))) {
            String line;
            while ((line = br.readLine()) != null) {
                Academic a = Academic.fromDataString(line);
                if (a != null) list.add(a);
            }
        } catch (IOException e) {
            System.out.println("Error loading academic staff: " + e.getMessage());
        }

        try (BufferedReader br = new BufferedReader(new FileReader(professionalPath))) {
            String line;
            while ((line = br.readLine()) != null) {
                Professional p = Professional.fromDataString(line);
                if (p != null) list.add(p);
            }
        } catch (IOException e) {
            System.out.println("Error loading professional staff: " + e.getMessage());
        }

        return list;
    }

    public static void saveStaff(String academicPath, String professionalPath, List<Staff> staff) {
        try (BufferedWriter abw = new BufferedWriter(new FileWriter(academicPath));
             BufferedWriter pbw = new BufferedWriter(new FileWriter(professionalPath))) {

            for (Staff s : staff) {
                if (s instanceof Academic) {
                    abw.write(((Academic) s).toDataString());
                    abw.newLine();
                } else if (s instanceof Professional) {
                    pbw.write(((Professional) s).toDataString());
                    pbw.newLine();
                }
            }
        } catch (IOException e) {
            System.out.println("Error saving staff: " + e.getMessage());
        }
    }

    public static List<Equipment> loadEquipments(String path) {
        List<Equipment> list = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                Equipment e = Equipment.fromDataString(line);
                if (e != null) list.add(e);
            }
        } catch (IOException e) {
            System.out.println("Error loading equipment: " + e.getMessage());
        }
        return list;
    }

    public static void saveEquipments(String path, List<Equipment> equipmentList) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
            for (Equipment equipment : equipmentList) {
                writer.write(equipment.toDataString());
                writer.newLine();
            }
            System.out.println("Equipments saved successfully to " + path);
        } catch (IOException e) {
            System.err.println("Failed to save equipments to " + path + ": " + e.getMessage());
        }
    }
    
    public static void saveLendingRecords(String path, List<LendingRecord> records) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(path))) {
            for (LendingRecord record : records) {
                bw.write(record.toDataString());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving lending records: " + e.getMessage());
        }
    }

    public static List<LendingRecord> loadLendingRecords(String path, List<Student> students, List<Staff> staff, InventoryService inventoryService) {
        List<LendingRecord> list = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                LendingRecord record = LendingRecord.fromDataString(line, students, staff, inventoryService);
                if (record != null) list.add(record);
            }
        } catch (IOException e) {
            System.out.println("Error loading lending records: " + e.getMessage());
        }
        return list;
    }

    public static void exportToFile(String filename, List<String> lines) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename))) {
            for (String line : lines) {
                bw.write(line);
                bw.newLine();
            }
            System.out.println("Report exported to " + filename);
        } catch (IOException e) {
            System.out.println("Error exporting report: " + e.getMessage());
        }
    }
}


import java.util.List;

public interface InventoryManager {
    void addEquipment(Equipment equipment);
    void removeEquipment(String equipmentId);
    List<Equipment> getAllEquipments();
}

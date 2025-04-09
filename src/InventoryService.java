import java.util.*;

public class InventoryService implements InventoryManager {
    private Map<String, Equipment> equipmentMap = new HashMap<>();

    public void addEquipment(Equipment eq) {
        equipmentMap.put(eq.getEquipmentId(), eq);
    }

    public void removeEquipment(String equipmentId) {
        equipmentMap.remove(equipmentId);
    }

    public List<Equipment> getAllEquipments() {
        return new ArrayList<>(equipmentMap.values());
    }

    public void loadEquipments(String path) {
        List<Equipment> loaded = FileUtil.loadEquipments(path);
        for (Equipment e : loaded) {
            equipmentMap.put(e.getEquipmentId(), e);
        }
    }

    public Equipment findEquipmentById(String id) {
        return equipmentMap.get(id);
    }

    // ✅ Add this to fix the error in AdminService
    public List<Equipment> getAvailableEquipments() {
        List<Equipment> available = new ArrayList<>();
        for (Equipment e : equipmentMap.values()) {
            if (e.getStatus().equalsIgnoreCase("Available")) {
                available.add(e);
            }
        }
        return available;
    }
    public void saveEquipments(String filePath) {
        FileUtil.saveEquipments(filePath, getAllEquipments());
    }
    
}

import java.util.Date;

public class Equipment {
    private String equipmentId;
    private String name;
    private String status;       // e.g., Available, Borrowed
    private Date purchaseDate;
    private String condition;    // e.g., Good, Needs Maintenance

    public Equipment(String equipmentId, String name, String status, Date purchaseDate, String condition) {
        this.equipmentId = equipmentId;
        this.name = name;
        this.status = status;
        this.purchaseDate = purchaseDate;
        this.condition = condition;
    }

    public String getEquipmentId() {
        return equipmentId;
    }

    public String getName() {
        return name;
    }

    public String getStatus() {
        return status;
    }

    public Date getPurchaseDate() {
        return purchaseDate;
    }

    public String getCondition() {
        return condition;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    @Override
    public String toString() {
        return String.format("Equipment ID: %s, Name: %s, Status: %s, Purchase Date: %s, Condition: %s",
                equipmentId, name, status,
                Person.DATE_FORMAT.format(purchaseDate), condition);
    }

    public String toDataString() {
        return String.format("%s|%s|%s|%s|%s",
                equipmentId, name, status,
                Person.DATE_FORMAT.format(purchaseDate), condition);
    }

    public static Equipment fromDataString(String line) {
        try {
            String[] parts = line.split("\\|");
            String id = parts[0];
            String name = parts[1];
            String status = parts[2];
            Date purchaseDate = Person.DATE_FORMAT.parse(parts[3]);
            String condition = parts[4];
            return new Equipment(id, name, status, purchaseDate, condition);
        } catch (Exception e) {
            System.out.println("Failed to load equipment: " + line);
            return null;
        }
    }
}

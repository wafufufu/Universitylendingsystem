
import java.util.List;

public interface LendingManager {
    void addRecord(LendingRecord record);
    void deleteRecord(String recordId);
    List<LendingRecord> getAllRecords();
}

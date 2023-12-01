package somethingrandom.dataaccess.local;

import com.fasterxml.jackson.databind.ObjectMapper;
import somethingrandom.entity.Item;
import somethingrandom.entity.ReferenceItem;
import somethingrandom.entity.DelayedItem;
import somethingrandom.entity.ActionableItem;
import somethingrandom.usecase.DataAccessException;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class FileUserDataAccessObject {
    private final File dataFile;
    private final Map<UUID, Map<String, String>> items;

    public FileUserDataAccessObject(String dataFilePath) throws DataAccessException {
        dataFile = new File(dataFilePath);
        items = new HashMap<>();
        save();
    }

    /**
     * Helper function to save items
     *
     */
    private void saveItemHelper(Map<String, String> itemData, Item item) {
        itemData.put("name", item.getName());
        itemData.put("id", item.getID().toString());
        itemData.put("creationDate", item.getCreationDate().toString());
        itemData.put("itemKind", item.getItemKind());
    }

    public void save(ActionableItem item) throws DataAccessException {
        Map<String, String> itemData = new HashMap<>();
        saveItemHelper(itemData, item);
        if (item.getNeededTime() == null){
            itemData.put("neededTime", "null");
        } else {
            itemData.put("neededTime", item.getNeededTime().toString());
        }
        items.put(item.getID(), itemData);
        this.save();
    }

    public void save(DelayedItem item) throws DataAccessException {
        Map<String, String> itemData = new HashMap<>();
        saveItemHelper(itemData, item);
        itemData.put("remindDate", item.getReminderDate().toString());
        items.put(item.getID(), itemData);
        this.save();
    }

    public void save(ReferenceItem item) throws DataAccessException {
        Map<String, String> itemData = new HashMap<>();
        saveItemHelper(itemData, item);
        itemData.put("description", item.getDescription());
        items.put(item.getID(), itemData);
        this.save();
    }

    private void save() throws DataAccessException {
        try{
            BufferedWriter writer = new BufferedWriter(new FileWriter(dataFile));
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(writer, items);
        } catch (IOException e) {
            throw new DataAccessException("Error while saving data");
        }
    }
}

package somethingrandom.dataaccess.local;

import org.json.JSONException;
import org.json.JSONWriter;
import somethingrandom.entity.*;
import somethingrandom.usecase.DataAccessException;

import java.io.*;
import java.time.Duration;
import java.time.Instant;
import java.util.*;

public class FileUserDataAccessObject {
    private final File dataFile;
    private final Map<UUID, Map<String, String>> items;
    private final ItemFactory factory;

    public FileUserDataAccessObject(String dataFilePath, ItemFactory factory) throws DataAccessException {
        dataFile = new File(dataFilePath);
        items = new HashMap<>();
        this.factory = factory;
        if (dataFile.length() == 0) {
            save();
        } else {
            load();
        }
    }

    private void load(){
        try {

        } catch ()
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
            itemData.put("neededTime", "");
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
            JSONWriter jsonWriter = new JSONWriter(writer);
            jsonWriter.object();
            for (UUID key: items.keySet()) {
                jsonWriter.key(key.toString());
                jsonWriter.value(items.get(key));
            }
            jsonWriter.endObject();
            writer.close();
        } catch (IOException e) {
            throw new DataAccessException("Error while saving data");
        }
    }

    public Collection<Item> getAllItems() throws DataAccessException {
        Collection<Item> all = new ArrayList<>();
        for (UUID key: items.keySet()) {
            if(items.get(key).get("itemKind").equals("ACTIONABLE")){
                all.add(toItem(items.get(key).get("name"), UUID.fromString(items.get(key).get("id")), Instant.parse(items.get(key).get("creationDate")), Duration.parse(items.get(key).get("neededTime"))));
            } else if (items.get(key).get("itemKind").equals("DELAYED")){
                all.add(toItem(items.get(key).get("name"), UUID.fromString(items.get(key).get("id")), Instant.parse(items.get(key).get("creationDate")), Instant.parse(items.get(key).get("remindDate"))));
            } else if (items.get(key).get("itemKind").equals("REFERENCE")){
                all.add(toItem(items.get(key).get("name"), UUID.fromString(items.get(key).get("id")), Instant.parse(items.get(key).get("creationDate")), items.get(key).get("description")));
            }
        }
        return all;
    }

    private ActionableItem toItem(String name, UUID id, Instant creationDate, Duration neededTime) {
        return new ActionableItem(name, id, creationDate, neededTime);
    }

    private DelayedItem toItem(String name, UUID id, Instant creationDate, Instant remindDate) {
        return new DelayedItem(name, id, creationDate, remindDate);
    }

    private ReferenceItem toItem(String name, UUID id, Instant creationDate, String description) {
        return new ReferenceItem(name, id, creationDate, description);
    }
}

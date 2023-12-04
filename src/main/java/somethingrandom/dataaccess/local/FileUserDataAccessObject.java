package somethingrandom.dataaccess.local;

import org.json.JSONObject;
import org.json.JSONWriter;
import somethingrandom.entity.*;
import somethingrandom.usecase.DataAccessException;
import somethingrandom.usecase.delete.DeleteItemDataAccessInterface;

import java.io.*;
import java.time.Duration;
import java.time.Instant;
import java.util.*;

public class FileUserDataAccessObject implements DeleteItemDataAccessInterface {
    private final File dataFile;
    private final Map<UUID, Map<String, String>> items;
    private final ItemFactory factory;

    public FileUserDataAccessObject(String dataFilePath, ItemFactory factory) throws DataAccessException {
        dataFile = new File(dataFilePath);
        this.factory = factory;
        this.items = new HashMap<>();
        if (dataFile.length() == 0){
            save();
        }
        load(this.items);
    }

    private void loadHelper(Map<String, String> itemData, JSONObject item) {
        itemData.put("name", item.getString("name"));
        itemData.put("id", item.getString("id"));
        itemData.put("creationDate", item.getString("creationDate"));
        itemData.put("itemKind", item.getString("itemKind"));
    }
    private void load(Map<UUID, Map<String, String>> items) throws DataAccessException {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(dataFile));
            JSONObject data = new JSONObject(reader.readLine());
            reader.close();

            for (String key: data.keySet()) {
                if(!items.containsKey(UUID.fromString(key))) {
                    Map<String, String> itemData = new HashMap<>();
                    JSONObject item = data.getJSONObject(key);
                    loadHelper(itemData, item);

                    if (itemData.get("itemKind").equals("ACTIONABLE")) {
                        itemData.put("neededTime", item.getString("neededTime"));
                    } else if (itemData.get("itemKind").equals("DELAYED")) {
                        itemData.put("remindDate", item.getString("remindDate"));
                    } else if (itemData.get("itemKind").equals("REFERENCE")) {
                        itemData.put("description", item.getString("description"));
                    }
                    items.put(UUID.fromString(key), itemData);
                }
            }
        } catch (IOException e) {
            throw new DataAccessException(e);
        }
    }

    public boolean delete(UUID id) throws DataAccessException {
        if(items.remove(id) == null){
            return false;
        } else {
            items.remove(id, items.get(id));
            save();
            return true;
        }
    }

    /**
     * Helper function to save items
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
            throw new DataAccessException(e);
        }
    }

    public Collection<Item> getAllItems() {
        Collection<Item> all = new ArrayList<>();

        for (Map<String, String> item : this.items.values()) {
            if(item.get("itemKind").equals("ACTIONABLE")){
                all.add(toItem(item.get("name"), UUID.fromString(item.get("id")), Instant.parse(item.get("creationDate")), Duration.parse(item.get("neededTime"))));
            } else if (item.get("itemKind").equals("DELAYED")){
                all.add(toItem(item.get("name"), UUID.fromString(item.get("id")), Instant.parse(item.get("creationDate")), Instant.parse(item.get("remindDate"))));
            } else if (item.get("itemKind").equals("REFERENCE")){
                all.add(toItem(item.get("name"), UUID.fromString(item.get("id")), Instant.parse(item.get("creationDate")), item.get("description")));
            }
        }
        return all;
    }

    private ActionableItem toItem(String name, UUID id, Instant creationDate, Duration neededTime) {
        return factory.createActionableItem(name, id, creationDate, neededTime);
    }

    private DelayedItem toItem(String name, UUID id, Instant creationDate, Instant remindDate) {
        return factory.createDelayedItem(name, id, creationDate, remindDate);
    }

    private ReferenceItem toItem(String name, UUID id, Instant creationDate, String description) {
        return factory.createReferenceItem(name, id, creationDate, description);
    }
}

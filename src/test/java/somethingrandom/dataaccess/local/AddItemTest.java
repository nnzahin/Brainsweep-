package somethingrandom.dataaccess.local;

import org.json.JSONObject;
import somethingrandom.entity.*;
import somethingrandom.usecase.DataAccessException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.junit.Assert.assertEquals;

public class AddItemTest {
    private final ActionableItem item1;
    private final DelayedItem item2;
    private final ReferenceItem item3;
    public AddItemTest() throws IOException {
        ItemFactory factory = new CommonItemFactory();
        UUID id1 = UUID.randomUUID();
        UUID id2 = UUID.randomUUID();
        UUID id3 = UUID.randomUUID();

        Instant now = Instant.now();
        Duration time = Duration.of(1, ChronoUnit.HOURS);
        Instant later = now.plus(7, ChronoUnit.DAYS);

        ActionableItem item1 = factory.createActionableItem("item1",id1, now, time);
        DelayedItem item2 = factory.createDelayedItem("item2", id2, now, later);
        ReferenceItem item3 = factory.createReferenceItem("item2", id3, now, "description");

        this.item1 = item1;
        this.item2 = item2;
        this.item3 = item3;

        try {
            FileUserDataAccessObject dao = new FileUserDataAccessObject("./data.json", factory);
            dao.save(item1);
            dao.save(item2);
            dao.save(item3);
        } catch (DataAccessException e) {
            throw new IOException(e);
        }
    }

    /**
     *
     * Test that the items are saved to the file as expected.
     */

    @org.junit.Test
    public void testActionableItemSavedProperly() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("./data.json"));
        JSONObject data = new JSONObject(reader.readLine());
        reader.close();
        JSONObject item = data.getJSONObject(item1.getID().toString());

        Map<String, String> itemData = new HashMap<>();

        for (String itemKey : item.keySet()) {
            if (!itemKey.equals("empty")) {
                itemData.put(itemKey, item.getString(itemKey));
            }
        }

        assertEquals(itemData.get("name"), item1.getName());
        assertEquals(itemData.get("id"), item1.getID().toString());
        assertEquals(itemData.get("creationDate"), item1.getCreationDate().toString());
        assertEquals(itemData.get("itemKind"), item1.getItemKind());
        assertEquals(itemData.get("neededTime"), item1.getNeededTime().toString());

    }

    @org.junit.Test
    public void testDelayedItemSavedProperly() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("./data.json"));
        JSONObject data = new JSONObject(reader.readLine());
        reader.close();
        JSONObject item = data.getJSONObject(item2.getID().toString());
        Map<String, String> itemData = new HashMap<>();

        for (String itemKey : item.keySet()) {
            if (!itemKey.equals("empty")) {
                itemData.put(itemKey, item.getString(itemKey));
            }
        }

        assertEquals(itemData.get("name"), item2.getName());
        assertEquals(itemData.get("id"), item2.getID().toString());
        assertEquals(itemData.get("creationDate"), item2.getCreationDate().toString());
        assertEquals(itemData.get("itemKind"), item2.getItemKind());
        assertEquals(itemData.get("remindDate"), item2.getReminderDate().toString());
    }

    @org.junit.Test
    public void testReferenceItemSavedProperly() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("./data.json"));
        JSONObject data = new JSONObject(reader.readLine());
        reader.close();
        JSONObject item = data.getJSONObject(item3.getID().toString());
        Map<String, String> itemData = new HashMap<>();

        for (String itemKey : item.keySet()) {
            if (!itemKey.equals("empty")) {
                itemData.put(itemKey, item.getString(itemKey));
            }
        }

        assertEquals(itemData.get("name"), item3.getName());
        assertEquals(itemData.get("id"), item3.getID().toString());
        assertEquals(itemData.get("creationDate"), item3.getCreationDate().toString());
        assertEquals(itemData.get("itemKind"), item3.getItemKind());
        assertEquals(itemData.get("description"), item3.getDescription());
    }

    @org.junit.Test
    public void testDataPersists() throws IOException, DataAccessException {
        ItemFactory factory = new CommonItemFactory();
        FileUserDataAccessObject dao = new FileUserDataAccessObject("./data.json", factory);
        testActionableItemSavedProperly();
        testReferenceItemSavedProperly();
        testDelayedItemSavedProperly();
    }
}

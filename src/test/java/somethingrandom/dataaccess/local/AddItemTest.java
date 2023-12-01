package somethingrandom.dataaccess.local;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
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

public class AddItemTest {
    private final Map<String, String> item1Data;
    private final Map<String, String> item2Data;
    private final Map<String, String> item3Data;
    public AddItemTest(){
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

        this.item1Data = new HashMap<>();
        this.item2Data = new HashMap<>();
        this.item3Data = new HashMap<>();

        item1Data.put("name", item1.getName());
        item1Data.put("id", item1.getID().toString());
        item1Data.put("creationDate", item1.getCreationDate().toString());
        item1Data.put("itemKind", item1.getItemKind());
        item1Data.put("neededTime", item1.getNeededTime().toString());

        item2Data.put("name", item2.getName());
        item2Data.put("id", item2.getID().toString());
        item2Data.put("creationDate", item2.getCreationDate().toString());
        item2Data.put("itemKind", item2.getItemKind());
        item2Data.put("remindDate", item2.getReminderDate().toString());

        item3Data.put("name", item3.getName());
        item3Data.put("id", item3.getID().toString());
        item3Data.put("creationDate", item3.getCreationDate().toString());
        item3Data.put("itemKind", item3.getItemKind());
        item3Data.put("description", item3.getDescription());

        try {
            FileUserDataAccessObject dao = new FileUserDataAccessObject("./data.json");
            dao.save(item1);
            dao.save(item2);
            dao.save(item3);
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     *
     * Test that the items are saved to the file as expected.
     */

    @org.junit.Test
    public void testActionableItemSavedProperly() {
        try{
            BufferedReader reader = new BufferedReader(new FileReader("./data.json"));
            ObjectMapper mapper = new ObjectMapper();
            JsonNode parser = mapper.readTree(reader);

            assert item1Data.get("name").equals(parser.path(item1Data.get("id")).path("name").asText());
            assert item1Data.get("id").equals(parser.path(item1Data.get("id")).path("id").asText());
            assert item1Data.get("creationDate").equals(parser.path(item1Data.get("id")).path("creationDate").asText());
            assert item1Data.get("itemKind").equals(parser.path(item1Data.get("id")).path("itemKind").asText());
            assert item1Data.get("neededTime").equals(parser.path(item1Data.get("id")).path("neededTime").asText());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @org.junit.Test
    public void testDelayedItemSavedProperly() {
        try{
            BufferedReader reader = new BufferedReader(new FileReader("./data.json"));
            ObjectMapper mapper = new ObjectMapper();
            JsonNode parser = mapper.readTree(reader);

            assert item2Data.get("name").equals(parser.path(item2Data.get("id")).path("name").asText());
            assert item2Data.get("id").equals(parser.path(item2Data.get("id")).path("id").asText());
            assert item2Data.get("creationDate").equals(parser.path(item2Data.get("id")).path("creationDate").asText());
            assert item2Data.get("itemKind").equals(parser.path(item2Data.get("id")).path("itemKind").asText());
            assert item2Data.get("remindDate").equals(parser.path(item2Data.get("id")).path("remindDate").asText());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @org.junit.Test
    public void testReferenceItemSavedProperly() {
        try{
            BufferedReader reader = new BufferedReader(new FileReader("./data.json"));
            ObjectMapper mapper = new ObjectMapper();
            JsonNode parser = mapper.readTree(reader);

            assert item3Data.get("name").equals(parser.path(item3Data.get("id")).path("name").asText());
            assert item3Data.get("id").equals(parser.path(item3Data.get("id")).path("id").asText());
            assert item3Data.get("creationDate").equals(parser.path(item3Data.get("id")).path("creationDate").asText());
            assert item3Data.get("itemKind").equals(parser.path(item3Data.get("id")).path("itemKind").asText());
            assert item3Data.get("description").equals(parser.path(item3Data.get("id")).path("description").asText());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

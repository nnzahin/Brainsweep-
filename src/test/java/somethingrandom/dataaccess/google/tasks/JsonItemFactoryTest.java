package somethingrandom.dataaccess.google.tasks;

import org.json.JSONObject;
import org.junit.Test;
import somethingrandom.entity.Item;
import somethingrandom.usecase.DataAccessException;

import java.time.Instant;
import java.util.UUID;

import static org.junit.Assert.*;

public class JsonItemFactoryTest {
    @Test
    public void shouldCreateUnknownItemFromValidJSON() throws DataAccessException {
        final String source = """
        {
            "kind": "tasks#task",
            "id": "ABC",
            "title": "Title",
            "notes": "Item kind: BLAH\\nItem: Alternate\\nCreation Date: 2023-12-02T14:23:00-04:00"
        }""";

        JSONObject obj = new JSONObject(source);
        UUID uuid = UUID.fromString("36f45672-e3ba-46a7-a339-ff1999a37e30");
        Item parsed = JsonItemFactory.createItem(uuid, obj);
        assertEquals(uuid, parsed.getID());
        assertEquals("BLAH", parsed.getItemKind());
        assertEquals("Title", parsed.getName());
        assertEquals(Instant.parse("2023-12-02T14:23:00-04:00"), parsed.getCreationDate());
    }

    @Test
    public void shouldCheckTaskKindIsValid() throws DataAccessException {
        final String source = """
        {
            "kind": "tasks#notright",
            "id": "ABC",
            "title": "Title",
            "notes": "Item kind: BLAH\\nItem: Alternate\\nCreation Date: 2023-12-02T14:23:00-04:00"
        }""";

        JSONObject obj = new JSONObject(source);
        assertThrows(DataAccessException.class, () -> {
            JsonItemFactory.createItem(UUID.fromString("36f45672-e3ba-46a7-a339-ff1999a37e30"), obj);
        });
    }

    @Test
    public void shouldShowDataAccessExceptionWithInvalidDate() throws DataAccessException {
        final String source = """
        {
            "kind": "tasks#task",
            "id": "ABC",
            "title": "Title",
            "notes": "Item kind: BLAH\\nItem: Alternate\\nCreation Date: 2023-who-cares"
        }""";

        JSONObject obj = new JSONObject(source);
        assertThrows(DataAccessException.class, () -> {
            JsonItemFactory.createItem(UUID.fromString("36f45672-e3ba-46a7-a339-ff1999a37e30"), obj);
        });
    }

    @Test
    public void shouldShowDataAccessExceptionWithMissingFields() throws DataAccessException {
        final String source = """
        {
            "kind": "tasks#task",
            "notes": "Item kind: BLAH\\nItem: Alternate\\nCreation date: 2023-12-02T14:51:00-04:00"
        }""";

        JSONObject obj = new JSONObject(source);
        assertThrows(DataAccessException.class, () -> {
            JsonItemFactory.createItem(UUID.fromString("36f45672-e3ba-46a7-a339-ff1999a37e30"), obj);
        });
    }
}

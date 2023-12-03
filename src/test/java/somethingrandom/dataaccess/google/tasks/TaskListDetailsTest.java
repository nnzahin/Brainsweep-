package somethingrandom.dataaccess.google.tasks;

import org.jetbrains.annotations.Nullable;
import org.json.JSONObject;
import org.junit.Test;
import somethingrandom.dataaccess.google.APIProvider;
import somethingrandom.dataaccess.google.APIRequestBody;
import somethingrandom.dataaccess.google.auth.AuthenticationException;
import somethingrandom.entity.Item;
import somethingrandom.usecase.DataAccessException;

import java.io.IOException;
import java.time.Instant;
import java.util.UUID;

import static org.junit.Assert.*;

public class TaskListDetailsTest {
    @Test
    public void shouldRequestCorrectEndpoint() throws IOException, DataAccessException {
        final String[] given = {null}; // allow use in the APIProvider
        TaskList tl = new TaskList(new APIProvider() {
            @Nullable
            @Override
            public JSONObject request(APIRequestBody body, String url) {
                given[0] = url;
                return null;
            }
        }, "Test", "LISTID");

        UUID uuid = UUID.fromString("8abe0dd8-f944-4df6-9315-eb8df82eb52b");
        tl.addUUIDPair(uuid, "ITEMID");
        tl.getItem(uuid);

        assertEquals("https://tasks.googleapis.com/tasks/v1/lists/LISTID/tasks/ITEMID", given[0]);
    }

    @Test
    public void shouldReturnEmptyOptionalOn404() throws IOException, DataAccessException {
        TaskList tl = new TaskList(new APIProvider.NotFound(), "Test", "LISTID");

        UUID uuid = UUID.fromString("8abe0dd8-f944-4df6-9315-eb8df82eb52b");
        tl.addUUIDPair(uuid, "ITEMID");
        assertTrue(tl.getItem(uuid).isEmpty());
    }

    @Test
    public void shouldReturnItemWithValidJSON() throws IOException, DataAccessException {
        final String source = """
        {
            "kind": "tasks#task",
            "id": "ITEMID",
            "title": "Title Here",
            "notes": "Creation Date: 2023-12-03T13:52:00-04:00\\nItem kind: SOMETHING"
        }
        """;
        TaskList tl = new TaskList(new APIProvider.Constant(source), "Test", "LISTID");

        UUID uuid = UUID.fromString("8abe0dd8-f944-4df6-9315-eb8df82eb52b");
        tl.addUUIDPair(uuid, "ITEMID");
        Item item = tl.getItem(uuid).orElseThrow();

        assertEquals("SOMETHING", item.getItemKind());
        assertEquals("Title Here", item.getName());
        assertEquals(uuid, item.getID());
        assertEquals(Instant.parse("2023-12-03T13:52:00-04:00"), item.getCreationDate());
    }
}

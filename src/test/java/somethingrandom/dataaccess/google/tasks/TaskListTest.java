package somethingrandom.dataaccess.google.tasks;

import org.json.JSONObject;
import org.junit.Test;
import somethingrandom.dataaccess.google.auth.Token;
import somethingrandom.usecase.DataAccessException;

import java.util.Iterator;

import static org.junit.Assert.*;

public class TaskListTest {
    private final Token token = Token.constant("token");

    @Test
    public void iterateListsThrowsOnIncorrectRootKind() {
        assertThrows(DataAccessException.class, () -> {
            TaskList.iterateLists(token, new JSONObject("{\"kind\": \"not the right kind\"}"));
        });
    }

    @Test
    public void shouldThrowIfNoItemsKeyPresent() {
        // TODO We should ideally wrap this ourselves.
        assertThrows(Exception.class, () -> {
            TaskList.iterateLists(token, new JSONObject("{\"kind\": \"tasks#taskLists\"}"));
        });
    }

    @Test
    public void shouldHandleEmptyItemsList() throws DataAccessException {
        assertFalse(TaskList.iterateLists(token, new JSONObject("{\"kind\": \"tasks#taskLists\", \"items\": []}")).hasNext());
    }

    @Test
    public void shouldTestEveryItemHasCorrectKind() {
        final String DATA = """
        {
            "kind": "tasks#taskLists",
            "items": [
                { "kind": "not correct" }
            ]
        }
        """;

        assertThrows(DataAccessException.class, () -> {
            TaskList.iterateLists(token, new JSONObject(DATA));
        });
    }

    @Test
    public void shouldTestIdIsPresent() {
        final String DATA = """
        {
            "kind": "tasks#taskLists",
            "items": [
                {
                    "kind": "tasks#taskList"
                }
            ]
        }
        """;

        assertThrows(DataAccessException.class, () -> {
            TaskList.iterateLists(token, new JSONObject(DATA));
        });
    }

    @Test
    public void shouldCreateTaskListForEachItem() throws DataAccessException {
        final String DATA = """
        {
            "kind": "tasks#taskLists",
            "items": [
                {
                    "kind": "tasks#taskList",
                    "id": "abc"
                },
                {
                    "kind": "tasks#taskList",
                    "id": "def"
                }
            ]
        }
        """;

        Iterator<TaskList> tls = TaskList.iterateLists(token, new JSONObject(DATA));
        assertEquals("abc", tls.next().getIdentifier());
        assertEquals("def", tls.next().getIdentifier());
        assertFalse(tls.hasNext());
    }
}

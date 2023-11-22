package somethingrandom.dataaccess.google.tasks;

import dataaccess.google.tasks.TaskAccount;
import dataaccess.google.tasks.TaskList;
import org.junit.Test;
import dataaccess.google.APIProvider;
import dataaccess.google.auth.Token;
import usecase.DataAccessException;

import java.util.Iterator;

import static org.junit.Assert.*;

public class TaskAccountTest {
    private final Token token = Token.constant("token");

    @Test
    public void iterateListsThrowsOnIncorrectRootKind() {
        assertThrows(DataAccessException.class, () -> {
            new TaskAccount(new APIProvider.Constant("{\"kind\": \"not the right kind\"}")).iterateLists();
        });
    }

    @Test
    public void shouldThrowIfNoItemsKeyPresent() {
        // TODO We should ideally wrap this ourselves.
        assertThrows(Exception.class, () -> {
            new TaskAccount(new APIProvider.Constant("{\"kind\": \"tasks#taskLists\"}")).iterateLists();
        });
    }

    @Test
    public void shouldHandleEmptyItemsList() throws DataAccessException {
        APIProvider provider = new APIProvider.Constant("{\"kind\": \"tasks#taskLists\", \"items\": []}");
        assertFalse(new TaskAccount(provider).iterateLists().hasNext());
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
            new TaskAccount(new APIProvider.Constant(DATA)).iterateLists();
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
            new TaskAccount(new APIProvider.Constant(DATA)).iterateLists();
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

        Iterator<TaskList> tls = new TaskAccount(new APIProvider.Constant(DATA)).iterateLists();
        assertEquals("abc", tls.next().getIdentifier());
        assertEquals("def", tls.next().getIdentifier());
        assertFalse(tls.hasNext());
    }
}

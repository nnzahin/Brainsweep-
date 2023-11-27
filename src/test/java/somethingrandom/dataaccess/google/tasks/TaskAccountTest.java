package somethingrandom.dataaccess.google.tasks;

import org.junit.Test;
import somethingrandom.dataaccess.google.APIProvider;
import somethingrandom.dataaccess.google.auth.Token;
import somethingrandom.usecase.DataAccessException;

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
    public void shouldTestIdAndTitleArePresent() {
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
    public void shouldTestTitleIsPresent() {
        final String DATA = """
        {
            "kind": "tasks#taskLists",
            "items": [
                {
                    "kind": "tasks#taskList",
                    "id": "something"
                }
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
                    "kind": "tasks#taskList",
                    "title": "something"
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
                    "id": "abc",
                    "title": "Tuv"
                },
                {
                    "kind": "tasks#taskList",
                    "id": "def",
                    "title": "Xyz"
                }
            ]
        }
        """;

        Iterator<TaskList> tls = new TaskAccount(new APIProvider.Constant(DATA)).iterateLists();

        TaskList tl = tls.next();
        assertEquals("abc", tl.getIdentifier());
        assertEquals("Tuv", tl.getTitle());

        tl = tls.next();
        assertEquals("def", tl.getIdentifier());
        assertEquals("Xyz", tl.getTitle());

        assertFalse(tls.hasNext());
    }
}

package somethingrandom.dataaccess.google.tasks;

import org.json.JSONException;
import org.json.JSONObject;
import somethingrandom.entity.Item;
import somethingrandom.usecase.DataAccessException;

import java.time.Instant;
import java.time.format.DateTimeParseException;
import java.util.UUID;

public class JsonItemFactory {
    private static class UnknownItem extends Item {
        private final String kind;

        public UnknownItem(String name, UUID id, Instant creationDate, String kind) {
            super(name, id, creationDate);
            this.kind = kind;
        }

        @Override
        public String getItemKind() {
            return kind;
        }
    }

    public static Item createItem(UUID uuid, JSONObject object) throws DataAccessException {
        try {
            if (!object.getString("kind").equals("tasks#task")) {
                throw new DataAccessException("Invalid Task kind (bug at Google?)");
            }

            return createItemHelper(uuid, object);
        } catch (JSONException | DateTimeParseException e) {
            throw new DataAccessException(e);
        }
    }

    private static Item createItemHelper(UUID uuid, JSONObject object) throws DataAccessException {
        String name = object.getString("title");
        String[] notes = object.getString("notes").split("\n");

        Instant createdDate = Instant.now();
        String kind = "UNKNOWN";
        for (String line : notes) {
            if (line.startsWith("Creation Date:")) {
                createdDate = Instant.parse(line.split(" ")[2]);
            }

            if (line.startsWith("Item kind:")) {
                kind = line.split(":")[1].strip();
            }
        }

        return new UnknownItem(name, uuid, createdDate, kind);
    }
}

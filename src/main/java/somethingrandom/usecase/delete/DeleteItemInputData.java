package somethingrandom.usecase.delete;

import java.util.UUID;

/**
 * Represents the item to be deleted.
 */
public class DeleteItemInputData {
    private final UUID id;
    private final String name;

    public DeleteItemInputData(UUID id) {
        this.id = id;
        this.name = null;
    }

    public DeleteItemInputData(String name) {
        this.name = name;
        this.id = null;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}

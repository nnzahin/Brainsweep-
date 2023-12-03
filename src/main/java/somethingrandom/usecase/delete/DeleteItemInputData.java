package somethingrandom.usecase.delete;

import java.util.UUID;

/**
 * Represents the item to be deleted.
 */
public class DeleteItemInputData {
    private final UUID id;

    public DeleteItemInputData(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }
}

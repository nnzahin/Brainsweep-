package somethingrandom.usecase;

import java.time.Instant;
import java.util.UUID;

public class AddDelayedItemInputData {
    private final String name;
    private final UUID id;
    private final Instant creationDate;
    private final Instant remindDate;

    /**
     * Create a new item with the following details.
     *
     * @param name         The name of the item.
     * @param id           The id
     * @param creationDate The creation date of the item.
     * @param remindDate   The reminder date of the item.
     */

    public AddDelayedItemInputData(String name, UUID id, Instant creationDate, Instant remindDate) {
        this.name = name;
        this.id = id;
        this.creationDate = creationDate;
        this.remindDate = remindDate;
    }

    /**
     * Return the name of the item to be created.
     *
     */

    public String getName() {
        return this.name;
    }

    /**
     * Return the id of the item to be created.
     *
     */

    public UUID getId() {
        return this.id;
    }

    /**
     * Return the creation date of the item to be created.
     *
     */

    public Instant getCreationDate() {
        return this.creationDate;
    }

    /**
     * Return the item due date of the item to be created.
     *
     */

    public Instant getRemindDate() {
        return this.remindDate;
    }
}

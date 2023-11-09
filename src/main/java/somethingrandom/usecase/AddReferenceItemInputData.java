package somethingrandom.usecase;

import java.time.Instant;
import java.util.UUID;

public class AddReferenceItemInputData {
    private final String name;
    private final UUID id;
    private final Instant creationDate;
    private final String description;

    /**
     * Create a new item with the following details.
     *
     * @param name          The name of the item.
     * @param id            The id of the item
     * @param creationDate  The creation date of the item.
     * @param description   The description of the item.
     */

    public AddReferenceItemInputData(String name, UUID id, Instant creationDate, String description) {
        this.name = name;
        this.id = id;
        this.creationDate = creationDate;
        this.description = description;
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

    public String getDescription() {
        return this.description;
    }
}

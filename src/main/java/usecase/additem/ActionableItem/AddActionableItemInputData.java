package usecase.additem.ActionableItem;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

public class AddActionableItemInputData {
    private final String name;
    private final UUID id;
    private final Instant creationDate;
    private final Duration neededTime;

    /**
     * Create a new item with the following details.
     *
     * @param name          The name of the item.
     * @param id            The id of the item
     * @param creationDate  The creation date of the item.
     */
    public AddActionableItemInputData(String name, UUID id, Instant creationDate) {
        this.name = name;
        this.id = id;
        this.creationDate = creationDate;
        this.neededTime = null;
    }

    /**
     * Create a new item with the following details.
     *
     * @param name          The name of the item.
     * @param id            The id of the item
     * @param creationDate  The creation date of the item.
     * @param neededTime    The needed time to complete the item.
     */
    public AddActionableItemInputData(String name, UUID id, Instant creationDate, Duration neededTime) {
        this.name = name;
        this.id = id;
        this.creationDate = creationDate;
        this.neededTime = neededTime;
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
     * Return the item needed time of the item to be created.
     *
     */
    public Duration getNeededTime() {
        return this.neededTime;
    }
}

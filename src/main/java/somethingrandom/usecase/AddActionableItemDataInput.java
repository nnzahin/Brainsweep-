package somethingrandom.usecase;

import java.time.Duration;
import java.time.Instant;

public class AddActionableItemDataInput {
    private final String name;
    private final Instant creationDate;
    private final Duration neededTime;

    /**
     * Create a new item with the following details.
     *
     * @param name The name of the item.
     * @param creationDate The creation date of the item.
     */
    public AddActionableItemDataInput(String name, Instant creationDate){
        this.name = name;
        this.creationDate = creationDate;
        this.neededTime = null;
    }
    /**
     * Create a new item with the following details.
     *
     * @param name The name of the item.
     * @param creationDate The creation date of the item.
     * @param neededTime The needed time to complete the item.
     */
    public AddActionableItemDataInput(String name, Instant creationDate, Duration neededTime){
        this.name = name;
        this.creationDate = creationDate;
        this.neededTime = neededTime;
    }
    String getName(){
        return this.name;
    }
    /**
     * Return the creation date of the item to be created.
     *
     */
    Instant getCreationDate(){
        return this.creationDate;
    }
    /**
     * Return the item due date of the item to be created.
     *
     */
    Duration getNeededTime(){
        return this.neededTime;
    }
}

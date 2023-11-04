package somethingrandom.usecase;

import java.time.Instant;

public class AddDelayedItemInputData {
    private final String name;
    private final Instant creationDate;
    private final Instant remindDate;

    /**
     * Create a new item with the following details.
     *
     * @param name The name of the item.
     * @param creationDate The creation date of the item.
     */
    public AddDelayedItemInputData(String name, Instant creationDate, Instant remindDate){
        this.name = name;
        this.creationDate = creationDate;
        this.remindDate = remindDate;
    }
    public String getName(){
        return this.name;
    }
    /**
     * Return the creation date of the item to be created.
     *
     */
    public Instant getCreationDate(){
        return this.creationDate;
    }
    /**
     * Return the item due date of the item to be created.
     *
     */
    public Instant getRemindDate(){
        return this.remindDate;
    }
}

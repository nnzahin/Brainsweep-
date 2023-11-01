package somethingrandom.entity;

import java.time.Instant;
import java.util.UUID;

/**
 * The type Delayed item.
 */
public class DelayedItem extends Item {
    private String name;
    private final Instant creationDate;
    private Instant remindDate;
    private final ItemKind itemKind;

    /**
     * Instantiates a new Delayed item.
     *
     * @param name         the name
     * @param creationDate the creation date
     * @param itemKind     the item kind
     */
    public DelayedItem(String name, Instant creationDate, Instant remindDate, ItemKind itemKind){
        this.name = name;
        this.id = UUID.randomUUID();
        this.creationDate = creationDate;
        this.remindDate = remindDate;
        this.itemKind = itemKind;
    }
    public String getName(){
        return this.name;
    }
    public UUID getID(){
        return this.id;
    }
    public String setName(String name){
        this.name = name;
        return this.name;
    }
    public Instant getCreationDate(){
        return this.creationDate;
    }

    /**
     * Sets remind date.
     *
     * @param remindDate the reminder date
     */
    public void setRemindDate(Instant remindDate) {
        this.remindDate = remindDate;
    }
    public ItemKind getItemKind(){
        return this.itemKind;
    }
}

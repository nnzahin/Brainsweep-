package somethingrandom.entity;

import java.time.Instant;
import java.util.UUID;

/**
 * The type Delayed item.
 */
public class DelayedItem extends Item {

    /**
     * The reminder date
     */
    private Instant remindDate;

    /**
     * Instantiates a new Delayed item.
     *
     * @param name         the name
     * @param id           the id
     * @param creationDate the creation date
     * @param remindDate   the reminder date
     */
    public DelayedItem(String name, UUID id, Instant creationDate, Instant remindDate) {
        super(name, id, creationDate);
        this.remindDate = remindDate;
    }

    /**
     * Returns item kind.
     *
     * @return the item kind
     */
    public String getItemKind() {
        return "DELAYED";
    }

    /**
     * Returns the reminder date.
     *
     * @return the remindDate
     */
    public Instant getReminderDate() {
        return this.remindDate;
    }

    /**
     * Sets remind date.
     *
     * @param remindDate the reminder date
     */
    public void setRemindDate(Instant remindDate) {
        this.remindDate = remindDate;
    }

    /**
     * Get remind date.
     *
     * @return the reminder date
     */
    public Instant getRemindDate() {
        return this.remindDate;
    }

    /**
     * Returns a string representation of the object.
     *
     * @return the string
     */
    public String toString(){
        return super.toString() + "\n Remind Date: " + this.remindDate;
    }
}

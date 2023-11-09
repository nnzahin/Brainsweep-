package somethingrandom.entity;

import java.time.Instant;
import java.util.UUID;

/**
 * The type Delayed item.
 */
public class DelayedItem extends Item {
    private String name;
    private UUID id;
    private Instant creationDate;
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

    public ItemKind getItemKind() {
        return ItemKind.DELAYED;
    }

    /**
     * Sets remind date.
     *
     * @param remindDate the reminder date
     */

    public void setRemindDate(Instant remindDate) {
        this.remindDate = remindDate;
    }
}

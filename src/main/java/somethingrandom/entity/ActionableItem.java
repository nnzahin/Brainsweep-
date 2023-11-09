package somethingrandom.entity;

import org.jetbrains.annotations.Nullable;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

/**
 * The type Actionable item.
 */
public class ActionableItem extends Item {

    /**
     * The Name.
     */
    private String name;

    /**
     * The ID.
     */
    private UUID id;

    /**
     * The Creation date.
     */
    private Instant creationDate;

    /**
     * The amount of time needed to complete the task.
     */
    @Nullable
    private Duration neededTime;

    /**
     * Instantiates a new Actionable item.
     *
     * @param name         the name
     * @param id           the id
     * @param creationDate the creation date
     * @param neededTime   the needed time
     */

    public ActionableItem(String name, UUID id, Instant creationDate, @Nullable Duration neededTime) {
        super(name, id, creationDate);
        this.neededTime = neededTime;
    }

    /**
     * Returns item kind.
     *
     * @return the item kind
     */

    public ItemKind getItemKind() {
        return ItemKind.ACTIONABLE;
    }

    /**
     * Set needed time.
     *
     * @param neededTime the needed time
     */

    public void setNeededTime(Duration neededTime) {
        this.neededTime = neededTime;
    }
}

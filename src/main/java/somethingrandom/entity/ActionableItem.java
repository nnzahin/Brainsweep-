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
    private final UUID id;
    /**
     * The Creation date.
     */

    private final Instant creationDate;
    /**
     * The amount of time needed to complete the task.
     */
    @Nullable
    private Duration neededTime;

    /**
     * Instantiates a new Actionable item.
     *
     * @param name         the name
     * @param creationDate the creation date
     * @param neededTime   the needed time
     */
    public ActionableItem(String name, Instant creationDate, @Nullable Duration neededTime){
        this.name = name;
        this.id = UUID.randomUUID();
        this.creationDate = creationDate;
        this.neededTime = neededTime;
    }
    /**
     * Returns item kind.
     *
     * @return the item kind
     */
    public ItemKind getItemKind(){
        return ItemKind.ACTIONABLE;
    }
    /**
     * Set needed time.
     *
     * @param neededTime the needed time
     */
    public void setNeededTime(Duration neededTime){
        this.neededTime = neededTime;
    }
}

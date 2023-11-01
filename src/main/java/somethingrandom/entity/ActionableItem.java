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
    public String name;
    /**
     * The ID.
     */
    public final UUID id;
    /**
     * The Creation date.
     */

    public final Instant creationDate;
    /**
     * The amount of time needed to complete the task.
     */
    @Nullable
    public Duration neededTime;
    /**
     * The Item kind.
     */
    public final ItemKind itemKind;

    /**
     * Instantiates a new Actionable item.
     *
     * @param name         the name
     * @param creationDate the creation date
     * @param neededTime   the needed time
     * @param itemKind     the item kind
     */
    public ActionableItem(String name, Instant creationDate, @Nullable Duration neededTime, ItemKind itemKind){
        this.name = name;
        this.id = UUID.randomUUID();
        this.creationDate = creationDate;
        this.neededTime = neededTime;
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
     * Set needed time.
     *
     * @param neededTime the needed time
     */
    public void setNeededTime(Duration neededTime){
        this.neededTime = neededTime;
    }
    public ItemKind getItemKind(){
        return this.itemKind;
    }
}

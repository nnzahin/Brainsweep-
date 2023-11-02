package somethingrandom.entity;

import java.time.Duration;
import java.time.Instant;

public class CommonItemFactory implements ItemFactory{
    /**
     * Return a new Actionable item.
     *
     * @param name         the name
     * @param creationDate the creation date
     */
    public ActionableItem createActionableItem(String name, Instant creationDate){
        return new ActionableItem(name, creationDate, null, ItemKind.ACTIONABLE);
    }
    /**
     * Return a new Actionable item.
     *
     * @param name         the name
     * @param creationDate the creation date
     * @param neededTime the needed time
     */
    public ActionableItem createActionableItem(String name, Instant creationDate, Duration neededTime){
        return new ActionableItem(name, creationDate, neededTime, ItemKind.ACTIONABLE);
    }
    /**
     * Return a new Reference item.
     *
     * @param name         the name
     * @param creationDate the creation date
     * @param description  the description
     */
    public ReferenceItem createReferenceItem(String name, Instant creationDate, String description){
        return new ReferenceItem(name, creationDate, description, ItemKind.REFERENCE);
    }
    /**
     * Return a new Delayed item.
     *
     * @param name         the name
     * @param creationDate the creation date
     * @param remindDate   the reminder date
     */
    public DelayedItem createDelayedItem(String name, Instant creationDate, Instant remindDate){
        return new DelayedItem(name, creationDate, remindDate, ItemKind.DELAYED);
    }
}

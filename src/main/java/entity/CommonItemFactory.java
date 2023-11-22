package entity;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

public class CommonItemFactory implements ItemFactory {

    /**
     * Return a new Actionable item.
     *
     * @param name         the name
     * @param id           the id
     * @param creationDate the creation date
     * @param neededTime   the needed time
     */
    public ActionableItem createActionableItem(String name, UUID id, Instant creationDate, Duration neededTime) {
        return new ActionableItem(name, id, creationDate, neededTime);
    }

    /**
     * Return a new Reference item.
     *
     * @param name         the name
     * @param id           the id
     * @param creationDate the creation date
     * @param description  the description
     */
    public ReferenceItem createReferenceItem(String name, UUID id, Instant creationDate, String description) {
        return new ReferenceItem(name, id, creationDate, description);
    }

    /**
     * Return a new Delayed item.
     *
     * @param name         the name
     * @param id           the id
     * @param creationDate the creation date
     * @param remindDate   the reminder date
     */
    public DelayedItem createDelayedItem(String name, UUID id, Instant creationDate, Instant remindDate) {
        return new DelayedItem(name, id, creationDate, remindDate);
    }
}

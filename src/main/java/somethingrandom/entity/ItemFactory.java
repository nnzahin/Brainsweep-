package somethingrandom.entity;

import java.time.Duration;
import java.time.Instant;

public interface ItemFactory {
    ActionableItem createActionableItem(String name, Instant creationDate, Duration neededTime);
    ReferenceItem createReferenceItem(String name, Instant creationDate, String description);
    DelayedItem createDelayedItem(String name, Instant creationDate, Instant remindDate);
}

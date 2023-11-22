package entity;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

public interface ItemFactory {
    ActionableItem createActionableItem(String name, UUID id, Instant creationDate, Duration neededTime);
    ReferenceItem createReferenceItem(String name, UUID id, Instant creationDate, String description);
    DelayedItem createDelayedItem(String name, UUID id, Instant creationDate, Instant remindDate);
}

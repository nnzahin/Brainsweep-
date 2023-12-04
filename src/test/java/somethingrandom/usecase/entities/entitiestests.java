package somethingrandom.usecase.entities;

import org.junit.Test;
import somethingrandom.entity.ActionableItem;
import somethingrandom.entity.CommonItemFactory;
import somethingrandom.entity.DelayedItem;
import somethingrandom.entity.ReferenceItem;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

import static junit.framework.TestCase.assertEquals;

public class entitiesTests {
    @Test
    public void testActionableItemSaved(){
        UUID id1 = UUID.randomUUID();
        String name = "name";
        Duration duration = Duration.ofDays(1);
        Instant now = Instant.now();
        CommonItemFactory factory = new CommonItemFactory();
        ActionableItem item1 = factory.createActionableItem(name, id1, now, duration);

        assertEquals(name, item1.getName());
        assertEquals(id1, item1.getID());
        assertEquals(now, item1.getCreationDate());
        assertEquals(duration, item1.getNeededTime());
    }

    @Test
    public void testDelayedItemSaved(){
        UUID id1 = UUID.randomUUID();
        String name = "name";
        Instant now = Instant.now();
        Duration duration = Duration.ofDays(1);
        Instant later = Instant.now().plus(duration);
        CommonItemFactory factory = new CommonItemFactory();
        DelayedItem item1 = factory.createDelayedItem(name, id1, now, later);

        assertEquals(name, item1.getName());
        assertEquals(id1, item1.getID());
        assertEquals(now, item1.getCreationDate());
        assertEquals(later, item1.getRemindDate());
    }

    @Test
    public void testReferenceItemSaved(){
        UUID id1 = UUID.randomUUID();
        String name = "name";
        String description = "description";
        Instant now = Instant.now();
        CommonItemFactory factory = new CommonItemFactory();
        ReferenceItem item1 = factory.createReferenceItem(name, id1, now, description);
        assertEquals(name, item1.getName());
        assertEquals(id1, item1.getID());
        assertEquals(now, item1.getCreationDate());
        assertEquals(description, item1.getDescription());
    }
}

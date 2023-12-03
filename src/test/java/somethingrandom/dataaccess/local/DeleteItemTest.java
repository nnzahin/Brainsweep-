package somethingrandom.dataaccess.local;

import org.junit.Before;
import org.junit.Test;
import somethingrandom.entity.*;
import somethingrandom.usecase.DataAccessException;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.UUID;

import static org.junit.Assert.assertNotEquals;

public class DeleteItemTest {
    private ActionableItem item1;
    private DelayedItem item2;
    private ReferenceItem item3;

    public DeleteItemTest() {
    }

    @Before
    public void setUp() throws IOException, DataAccessException {
        ItemFactory factory = new CommonItemFactory();
        UUID id1 = UUID.randomUUID();
        UUID id2 = UUID.randomUUID();
        UUID id3 = UUID.randomUUID();

        Instant now = Instant.now();
        Duration time = Duration.of(1, ChronoUnit.HOURS);
        Instant later = now.plus(7, ChronoUnit.DAYS);

        ActionableItem item1 = factory.createActionableItem("item1",id1, now, time);
        DelayedItem item2 = factory.createDelayedItem("item2", id2, now, later);
        ReferenceItem item3 = factory.createReferenceItem("item3", id3, now, "description");
        this.item1 = item1;
        this.item2 = item2;
        this.item3 = item3;
        FileUserDataAccessObject dao = new FileUserDataAccessObject("./data.json", factory);
        dao.save(item1);
        dao.save(item2);
        dao.save(item3);
    }

    /**
     * Test that the items are deleted as expected.
     */
    @Test
    public void testItemsAreDeleted() throws IOException, DataAccessException {
        FileUserDataAccessObject dao = new FileUserDataAccessObject("./data.json", new CommonItemFactory());
        Collection<Item> items = dao.getAllItems();
        dao.delete(item1.getID());
        assertNotEquals(items, dao.getAllItems());
    }
}

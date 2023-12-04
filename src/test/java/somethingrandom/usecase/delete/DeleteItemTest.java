package somethingrandom.usecase.delete;

import org.junit.Before;
import org.junit.Test;
import somethingrandom.entity.ActionableItem;
import somethingrandom.entity.Item;
import somethingrandom.usecase.DataAccessException;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static junit.framework.TestCase.assertEquals;

public class DeleteItemTest {
    private DataAccess dao;
    private UUID id;

    @Before
    public void setUp() {
        Map<UUID, Item> data = new HashMap<>();
        UUID id1 = UUID.randomUUID();

        Instant now = Instant.now();
        Duration time = Duration.of(1, ChronoUnit.HOURS);
        Instant later = now.plus(7, ChronoUnit.DAYS);
        ActionableItem item1 = new ActionableItem("item1",id1, now, time);
        data.put(id1, item1);
        this.dao = new DataAccess(data);
        this.id = id1;
    }

    @Test
    public void testDeleteExistingItem() throws DataAccessException {
        DeleteItemOutputBoundary presenter = new Presenter();
        DeleteItemInputBoundary useCase = new DeleteItemInteractor(this.dao);
        useCase.execute(id);
        assertEquals(new HashMap<>(), dao.getData());
    }
}

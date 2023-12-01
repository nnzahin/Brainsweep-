package somethingrandom.usecase.details;

import org.junit.Before;
import org.junit.Test;
import somethingrandom.entity.Item;
import somethingrandom.entity.ReferenceItem;

import java.time.Instant;
import java.util.UUID;

import static org.junit.Assert.assertEquals;

public class ItemDetailsSuccessTest {
    private DummyDataAccess dataAccess;
    private DummyPresenter presenter;

    @Before
    public void setup() {
        dataAccess = new DummyDataAccess();
        presenter = new DummyPresenter();
    }

    @Test
    public void shouldSetPropertiesForReferenceItem() {
        final UUID uuid = UUID.fromString("634f6f85-7cb8-4af5-9cc3-17fec4dcf0b6");
        final Instant when = Instant.parse("2023-11-30T02:07:00+04:00");
        final Item item = new ReferenceItem("Name", uuid, when, "Description");
        dataAccess.add(uuid, item);

        ItemDetailsInputBoundary usecase = new ItemDetailsInteractor(dataAccess, presenter);
        usecase.showDetailsForId(uuid);
        assertEquals("Name", presenter.details.getTitle());
        assertEquals(when, presenter.details.getCreationTime());
        assertEquals("Description", presenter.details.getDescription());
    }
}

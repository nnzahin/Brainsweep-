package somethingrandom.usecase.details;

import org.junit.Before;
import org.junit.Test;
import somethingrandom.entity.ActionableItem;
import somethingrandom.entity.DelayedItem;
import somethingrandom.entity.Item;
import somethingrandom.entity.ReferenceItem;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class ItemDetailsSuccessTest {
    private DummyDataAccess dataAccess;
    private DummyPresenter presenter;

    private static class FakeItem extends Item {
        public FakeItem(String name, UUID id, Instant creationDate) {
            super(name, id, creationDate);
        }

        @Override
        public String getItemKind() {
            return "FAKE";
        }
    }

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
        assertNull(presenter.details.getExpectedDuration());
        assertNull(presenter.details.getRemindDate());
    }

    @Test
    public void shouldSetPropertiesForActionableItem() {
        final UUID uuid = UUID.fromString("634f6f85-7cb8-4af5-9cc3-17fec4dcf0b6");
        final Instant when = Instant.parse("2023-11-30T02:07:00+04:00");
        final Duration duration = Duration.of(1, ChronoUnit.HOURS);
        final Item item = new ActionableItem("Name", uuid, when, duration);
        dataAccess.add(uuid, item);

        ItemDetailsInputBoundary usecase = new ItemDetailsInteractor(dataAccess, presenter);
        usecase.showDetailsForId(uuid);
        assertEquals("Name", presenter.details.getTitle());
        assertEquals(when, presenter.details.getCreationTime());
        assertEquals(duration, presenter.details.getExpectedDuration());
        assertNull(presenter.details.getDescription());
        assertNull(presenter.details.getRemindDate());
    }

    @Test
    public void shouldSetPropertiesForDelayedItem() {
        final UUID uuid = UUID.fromString("634f6f85-7cb8-4af5-9cc3-17fec4dcf0b6");
        final Instant when = Instant.parse("2023-11-30T02:07:00+04:00");
        final Instant remind = Instant.parse("2023-11-30T02:08:00+04:00");
        final Item item = new DelayedItem("Name", uuid, when, remind);
        dataAccess.add(uuid, item);

        ItemDetailsInputBoundary usecase = new ItemDetailsInteractor(dataAccess, presenter);
        usecase.showDetailsForId(uuid);
        assertEquals("Name", presenter.details.getTitle());
        assertEquals(when, presenter.details.getCreationTime());
        assertEquals(remind, presenter.details.getRemindDate());
        assertNull(presenter.details.getDescription());
        assertNull(presenter.details.getExpectedDuration());
    }

    @Test
    public void shouldPresentCommonPropertiesWithUnknownItemKind() {
        Item item = new FakeItem("Fake", UUID.fromString("634f6f85-7cb8-4af5-9cc3-17fec4dcf0b6"), Instant.MIN);
        ItemDetailsInputBoundary usecase = new ItemDetailsInteractor(uuid -> Optional.of(item), presenter);

        usecase.showDetailsForId(UUID.fromString("634f6f85-7cb8-4af5-9cc3-17fec4dcf0b6"));
        assertEquals("Fake", presenter.details.getTitle());
        assertNull(presenter.details.getDescription());
        assertEquals(Instant.MIN, presenter.details.getCreationTime());
    }
}

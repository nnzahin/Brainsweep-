package somethingrandom.usecase.search;

import org.junit.Before;
import org.junit.Test;
import somethingrandom.entity.ActionableItem;
import somethingrandom.entity.DelayedItem;
import somethingrandom.entity.Item;
import somethingrandom.entity.ReferenceItem;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.*;

public class StringQueryTest {
    private static final Item referenceItem = new ReferenceItem("ABC", UUID.fromString("dfbdaabc-a80e-4baf-9028-db04c7d836d3"), Instant.MIN, "Irrelevant");
    private static final Item delayedItem = new DelayedItem("DEF", UUID.fromString("ab0cc5c6-8755-47e1-9201-f24a11f4370f"), Instant.MIN, Instant.MAX);
    private static final Item actionableItem = new ActionableItem("ABCDEFGHI", UUID.fromString("4b77eb49-d460-4d61-859f-3072918437ce"), Instant.MIN, Duration.of(1, ChronoUnit.SECONDS));

    private static final Instant TIME = Instant.parse("2023-11-30T18:03:00-04:00");
    private static final Clock CLOCK = Clock.fixed(TIME, ZoneId.of("UTC"));

    private DummyPresenter presenter;
    private SearchItemsInputBoundary usecase;

    private static class DummyDataAccessObject implements SearchItemsDataAccessInterface {
        @Override
        public Collection<Item> getAllItems() {
            return List.of(referenceItem, delayedItem, actionableItem);
        }
    }

    private static class DummyPresenter implements SearchItemsOutputBoundary {
        public Collection<SearchItemsOutputData> results = null;

        @Override
        public void presentSearchResults(Collection<SearchItemsOutputData> items) {
            results = items;
        }

        @Override
        public void presentSearchFailure(String message) {
            throw new RuntimeException(message);
        }
    }

    @Before
    public void before() {
        DummyDataAccessObject dao = new DummyDataAccessObject();
        presenter = new DummyPresenter();
        usecase = new SearchItemsInteractor(dao, presenter, CLOCK);
    }

    @Test
    public void shouldReturnAllResultsWithEmptyQuery() {
        usecase.execute(new SearchItemsInputData(""));

        assertNotNull(presenter.results);
        assertEquals(3, presenter.results.size());
        assertResultsMatch(presenter.results, List.of(referenceItem, actionableItem, delayedItem));
    }

    @Test
    public void shouldReturnOnlyResultsWithSubstring() {
        usecase.execute(new SearchItemsInputData("ABC"));

        assertNotNull(presenter.results);
        assertEquals(2, presenter.results.size());
        assertResultsMatch(presenter.results, List.of(referenceItem, actionableItem));
    }

    @Test
    public void shouldReturnSingleResultWithSubstring() {
        usecase.execute(new SearchItemsInputData("dEFg"));

        assertNotNull(presenter.results);
        assertEquals(1, presenter.results.size());
        assertResultsMatch(presenter.results, List.of(actionableItem));
    }

    @Test
    public void shouldReturnNoResultsIfNoneMatch() {
        usecase.execute(new SearchItemsInputData("xyz"));

        assertNotNull(presenter.results);
        assertTrue(presenter.results.isEmpty());
    }

    @Test
    public void shouldBeCaseInsensitive() {
        usecase.execute(new SearchItemsInputData("aBc"));

        assertNotNull(presenter.results);
        assertEquals(2, presenter.results.size());
        assertResultsMatch(presenter.results, List.of(referenceItem, actionableItem));
    }

    private static void assertResultsMatch(Iterable<SearchItemsOutputData> results, Iterable<Item> items) {
        outer: for (SearchItemsOutputData result : results) {
            for (Item item : items) {
                if (item.getID().equals(result.getUUID())) {
                    assertEquals(item.getName(), result.getName());

                    if (item instanceof DelayedItem) {
                        assertEquals(((DelayedItem) item).getRemindDate(), result.getRelevantInstant());
                    }

                    if (item instanceof ActionableItem) {
                        assertEquals(((ActionableItem) item).getNeededTime().addTo(TIME), result.getRelevantInstant());
                    }

                    continue outer;
                }
            }

            fail("UUID %s had no item".formatted(result.getUUID().toString()));
        }

        outer: for (Item item : items) {
            for (SearchItemsOutputData result : results) {
                if (item.getID().equals(result.getUUID())) {
                    continue outer;
                }
            }

            fail("UUID %s had no result".formatted(item.getID().toString()));
        }
    }
}

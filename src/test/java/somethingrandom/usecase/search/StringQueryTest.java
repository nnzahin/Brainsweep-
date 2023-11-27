package somethingrandom.usecase.search;

import org.junit.Before;
import org.junit.Test;
import somethingrandom.entity.ActionableItem;
import somethingrandom.entity.DelayedItem;
import somethingrandom.entity.Item;
import somethingrandom.entity.ReferenceItem;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.*;

public class StringQueryTest {
    private static final Item referenceItem = new ReferenceItem("ABC", UUID.fromString("dfbdaabc-a80e-4baf-9028-db04c7d836d3"), Instant.MIN, "Irrelevant");
    private static final Item delayedItem = new DelayedItem("DEF", UUID.fromString("ab0cc5c6-8755-47e1-9201-f24a11f4370f"), Instant.MIN, Instant.MAX);
    private static final Item actionableItem = new ActionableItem("ABCDEFGHI", UUID.fromString("4b77eb49-d460-4d61-859f-3072918437ce"), Instant.MIN, Duration.of(1, ChronoUnit.SECONDS));

    private DummyPresenter presenter;
    private SearchItemsInputBoundary usecase;

    private static class DummyDataAccessObject implements SearchItemsDataAccessInterface {
        @Override
        public Collection<Item> getAllItems() {
            return List.of(referenceItem, delayedItem, actionableItem);
        }
    }

    private static class DummyPresenter implements SearchItemsOutputBoundary {
        public Collection<Item> results = null;

        @Override
        public void presentSearchResults(Collection<Item> items) {
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
        usecase = new SearchItemsInteractor(dao, presenter);
    }

    @Test
    public void shouldReturnAllResultsWithEmptyQuery() {
        usecase.execute(new SearchItemsInputData(""));

        assertNotNull(presenter.results);
        assertEquals(3, presenter.results.size());
        assertTrue(presenter.results.containsAll(List.of(referenceItem, actionableItem, delayedItem)));
    }

    @Test
    public void shouldReturnOnlyResultsWithSubstring() {
        usecase.execute(new SearchItemsInputData("ABC"));

        assertNotNull(presenter.results);
        assertEquals(2, presenter.results.size());
        assertTrue(presenter.results.containsAll(List.of(referenceItem, actionableItem)));
    }

    @Test
    public void shouldReturnSingleResultWithSubstring() {
        usecase.execute(new SearchItemsInputData("dEFg"));

        assertNotNull(presenter.results);
        assertEquals(1, presenter.results.size());
        assertTrue(presenter.results.contains(actionableItem));
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
        assertTrue(presenter.results.containsAll(List.of(referenceItem, actionableItem)));
    }
}

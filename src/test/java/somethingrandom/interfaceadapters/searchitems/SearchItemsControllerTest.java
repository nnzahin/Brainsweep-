package somethingrandom.interfaceadapters.searchitems;

import org.junit.Test;
import somethingrandom.entity.DelayedItem;
import somethingrandom.entity.Item;
import somethingrandom.entity.ReferenceItem;
import somethingrandom.usecase.SearchItemsInputBoundary;

import java.time.Instant;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertTrue;

public class SearchItemsControllerTest {
    private static class DummyUseCase implements SearchItemsInputBoundary {
        public Collection<Item> items = null;

        @Override
        public void search() {
            items = List.of(
                new ReferenceItem("Abc", UUID.fromString("cdf1c6e8-6197-445b-a6dd-c8f985efdfd3"), Instant.MIN, "def"),
                new DelayedItem("Abc", UUID.fromString("5b090604-9358-4acb-a347-f0dfb6e34c45"), Instant.MIN, Instant.MAX)
            );
        }
    }

    @Test
    public void shouldListAllItemsByDefault() {
        DummyUseCase useCase = new DummyUseCase();
        SearchItemsController controller = new SearchItemsController(useCase);

        controller.refresh();
        Iterator<Item> items = useCase.items.iterator();
        assertTrue(items.next() instanceof ReferenceItem);
        assertTrue(items.next() instanceof DelayedItem);
    }
}

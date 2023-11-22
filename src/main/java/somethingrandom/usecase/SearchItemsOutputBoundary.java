package somethingrandom.usecase;

import somethingrandom.entity.Item;

import java.util.Collection;

public interface SearchItemsOutputBoundary {
    /**
     * Called if the search query succeeded, along with its results.
     *
     * @param items The items that matched the query.
     */
    // TODO: Is it OK to use the Item entity here?
    void succeeded(Collection<Item> items);

    void failed(String message);
}

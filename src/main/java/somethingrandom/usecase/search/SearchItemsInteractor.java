package somethingrandom.usecase.search;

import org.jetbrains.annotations.NotNull;
import somethingrandom.entity.ActionableItem;
import somethingrandom.entity.DelayedItem;
import somethingrandom.entity.Item;
import somethingrandom.usecase.DataAccessException;

import java.time.Clock;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * A SearchItemsInteractor is responsible for using a query to filter
 * the list of items and present them on a view.
 */
public class SearchItemsInteractor implements SearchItemsInputBoundary{
    private final SearchItemsDataAccessInterface dataAccess;
    private final SearchItemsOutputBoundary presenter;
    private final Clock clock;

    /**
     * Creates a new interactor with the given database and output boundary.
     *
     * @param dataAccess The database to access items from.
     * @param presenter The presenter to display results on.
     * @param clock The clock to read time from; you probably want Clock.systemUTC or so.
     *              This is just used internally, so it doesn't have to have any interesting
     *              time zone.
     */
    public SearchItemsInteractor(SearchItemsDataAccessInterface dataAccess,
                                 SearchItemsOutputBoundary presenter,
                                 Clock clock) {
        this.dataAccess = dataAccess;
        this.presenter = presenter;
        this.clock = clock;
    }

    /**
     * Executes the provided query, displaying results on the presenter.
     *
     * @param query The query to search for.
     */
    @Override
    public void execute(SearchItemsInputData query) {
        try {
            Instant baseTime = clock.instant();
            Collection<Item> items = dataAccess.getAllItems();
            List<SearchItemsOutputData> results = new ArrayList<>();

            String searchText = query.getSearchQuery().toLowerCase();

            for (Item item : items) {
                if (item.getName().toLowerCase().contains(searchText)) {
                    results.add(createSearchResult(baseTime, item));
                }
            }

            presenter.presentSearchResults(results);
        } catch (DataAccessException e) {
            presenter.presentSearchFailure(e.getMessage());
        }
    }

    @NotNull
    private static SearchItemsOutputData createSearchResult(Instant baseTime, Item item) {
        if (item instanceof ActionableItem) {
            return new SearchItemsOutputData(item.getName(), item.getID(), baseTime, ((ActionableItem) item).getNeededTime());
        }

        if (item instanceof DelayedItem) {
            return new SearchItemsOutputData(item.getName(), item.getID(), ((DelayedItem) item).getRemindDate());
        }

        return new SearchItemsOutputData(item.getName(), item.getID());
    }
}

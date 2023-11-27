package somethingrandom.usecase.search;

import somethingrandom.entity.Item;
import somethingrandom.usecase.DataAccessException;

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

    /**
     * Creates a new interactor with the given database and output boundary.
     *
     * @param dataAccess The database to access items from.
     * @param presenter The presenter to display results on.
     */
    public SearchItemsInteractor(SearchItemsDataAccessInterface dataAccess,
                                 SearchItemsOutputBoundary presenter ){
        this.dataAccess = dataAccess;
        this.presenter = presenter;
    }

    /**
     * Executes the provided query, displaying results on the presenter.
     *
     * @param query The query to search for.
     */
    @Override
    public void execute(SearchItemsInputData query) {
        try {
            Collection<Item> items = dataAccess.getAllItems();
            List<Item> results = new ArrayList<>();

            String searchText = query.getSearchQuery().toLowerCase();

            for (Item item : items) {
                if (item.getName().toLowerCase().contains(searchText)) {
                    results.add(item);
                }
            }

            presenter.presentSearchResults(results);
        } catch (DataAccessException e) {
            presenter.presentSearchFailure(e.getMessage());
        }
    }
}

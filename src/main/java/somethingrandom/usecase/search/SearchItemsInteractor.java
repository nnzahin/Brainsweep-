package somethingrandom.usecase.search;

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
        //not sure how we're implementing this yet.
    }
}

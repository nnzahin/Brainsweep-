package somethingrandom.usecase.search;

/**
 * Represents the external interface to the Search use case.
 */
public interface SearchItemsInputBoundary {
    /**
     * Requests that the use case generate results for the query, and to place
     * them in the presenter.
     *
     * @param query The query to search for.
     */
    void execute(SearchItemsInputData query);
}

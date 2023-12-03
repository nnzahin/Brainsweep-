package somethingrandom.usecase.search;

/**
 * Represents a search query.
 *
 * Currently, this can have a string to search for, and nothing else.
 */
public class SearchItemsInputData {
    private final String searchQuery;

    public SearchItemsInputData(String searchQuery) {
        this.searchQuery = searchQuery;
    }

    public String getSearchQuery(){
        return searchQuery;
    }
}

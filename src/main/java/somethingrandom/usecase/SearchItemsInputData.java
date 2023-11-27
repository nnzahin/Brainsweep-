package somethingrandom.usecase;

public class SearchItemsInputData {

    private final String searchQuery;

    public SearchItemsInputData(String searchQuery) {
        this.searchQuery = searchQuery;
    }

    String getSearchQuery(){return searchQuery;}

}

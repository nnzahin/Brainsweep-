package somethingrandom.usecase.searchitems;

public class SearchItemsInputData {

    private final String searchQuery;

    public SearchItemsInputData(String searchQuery) {
        this.searchQuery = searchQuery;
    }

    String getSearchQuery(){return searchQuery;}

}

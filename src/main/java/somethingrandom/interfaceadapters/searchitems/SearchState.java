package somethingrandom.interfaceadapters.searchitems;

public class SearchState {
    private String searchQuery = "";
    private String searchError = "This item does not exist.";

    public SearchState(){}
    public String getSearchError(){return searchError;}
    public void setSearchQuery(String search){this.searchQuery = search;}

    public String getSearchQuery(){return this.searchQuery;}


}

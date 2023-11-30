package somethingrandom.interfaceadapters.searchitems;

public class SearchState {
    private String search = "";
    private String searchError = "This item does not exist.";

    public SearchState(){}
    public String getSearchError(){return searchError;}
    public void setSearchQuery(String search){this.search = search;}

    public String getSearchQuery(){return this.search;}


}

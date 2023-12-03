package somethingrandom.interfaceadapters.searchitems;

import somethingrandom.usecase.search.SearchItemsOutputData;

import java.util.Collection;

public class SearchState {
    private String search = "";
    private final String searchError = "This item does not exist.";
    private Collection<SearchItemsOutputData> results;


    public SearchState(){}
    public String getSearchError(){return searchError;}
    public void setSearchQuery(String search){this.search = search;}

    public String getSearchQuery(){return this.search;}
    public void setResults(Collection<SearchItemsOutputData> results){this.results = results;}
    public Collection<SearchItemsOutputData> getResults(){return results;}


}

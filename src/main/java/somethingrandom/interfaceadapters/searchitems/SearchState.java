package somethingrandom.interfaceadapters.searchitems;

import somethingrandom.entity.Item;

import java.util.Collection;

public class SearchState {
    private String search = "";
    private String searchError = "This item does not exist.";
    private Collection<Item> results;


    public SearchState(){}
    public String getSearchError(){return searchError;}
    public void setSearchQuery(String search){this.search = search;}

    public String getSearchQuery(){return this.search;}
    public void setResults(Collection<Item> results){this.results = results;}
    public Collection<Item> getResults(){return results;}


}

package somethingrandom.interfaceadapters.searchitems;

import somethingrandom.usecase.search.SearchItemsInteractor;
import somethingrandom.usecase.search.SearchItemsOutputData;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SearchState {
    private String search = "";
    private String searchError = null;
    private Collection<SearchItemsOutputData> results;
    private ArrayList<String> resultNames;

    public SearchState(){}
    public void setSearchQuery(String search){this.search = search;}

    public String getSearchQuery(){return this.search;}
    public void setResults(Collection<SearchItemsOutputData> results){this.results = results;}
    public Collection<SearchItemsOutputData> getResults(){return results;}

    public void setResultNames(ArrayList<String> resultNames) {
        this.resultNames = resultNames;}

    public ArrayList<String> getResultNames() {return resultNames;}

    public void setSearchError(String error) {this.searchError = error;}

    public String getSearchError() {return searchError;}



}

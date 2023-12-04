package somethingrandom.interfaceadapters.searchitems;

import somethingrandom.usecase.search.SearchItemsInteractor;
import somethingrandom.usecase.search.SearchItemsOutputData;

import java.util.*;

public class SearchState {
    private String search = "";
    private String searchError = null;
    private List<Result> results;

    public record Result(String name, UUID uuid, long date) {
        @Override
        public String toString() {
            return name;
        }
    }

    public SearchState(){}
    public void setSearchQuery(String search){this.search = search;}

    public String getSearchQuery(){return this.search;}
    public void setResults(List<Result> results){
        this.results = results;
    }

    public List<Result> getResults() {
        return this.results;
    }

    public void setSearchError(String error) {this.searchError = error;}

    public String getSearchError() {return searchError;}



}

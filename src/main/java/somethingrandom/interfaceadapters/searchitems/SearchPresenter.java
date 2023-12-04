package somethingrandom.interfaceadapters.searchitems;

import somethingrandom.entity.Item;
import somethingrandom.interfaceadapters.ViewManagerModel;
import somethingrandom.usecase.search.SearchItemsOutputBoundary;
import somethingrandom.usecase.search.SearchItemsOutputData;

import javax.swing.text.View;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class SearchPresenter implements SearchItemsOutputBoundary {
    private final SearchViewModel searchViewModel;

    public SearchPresenter(SearchViewModel searchViewModel){this.searchViewModel = searchViewModel;}

    @Override
    public void presentSearchResults(Collection<SearchItemsOutputData> items) {

        SearchState searchState = searchViewModel.getState();

        ArrayList<SearchState.Result> results = new ArrayList<>();

        for (SearchItemsOutputData item : items){
            // TODO 2038 problem
            results.add(new SearchState.Result(item.getName(), item.getUUID(), item.getRelevantInstant() != null ? item.getRelevantInstant().getEpochSecond() : Integer.MAX_VALUE));
        }

        results.sort((a, b) -> (int) (a.date() - b.date()));

        searchState.setResults(results);
        searchState.setSearchError(null);
        // Might be more efficient to store the name of each item mapped to its specific item entity

        this.searchViewModel.setState(searchState);
        this.searchViewModel.firePropertyChanged();

    }

    /**
     * Called if the search fails for some reason. The view should notify
     * the user, preferably non-invasively.
     *
     * @param message The message that should be displayed to the user.
     */
    public void presentSearchFailure(String message){
        SearchState state = searchViewModel.getState();
        state.setSearchError(message);
        searchViewModel.firePropertyChanged();
    }


}

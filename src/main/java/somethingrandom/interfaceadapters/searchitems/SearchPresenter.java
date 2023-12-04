package somethingrandom.interfaceadapters.searchitems;

import somethingrandom.entity.Item;
import somethingrandom.interfaceadapters.ViewManagerModel;
import somethingrandom.usecase.search.SearchItemsOutputBoundary;
import somethingrandom.usecase.search.SearchItemsOutputData;

import javax.swing.text.View;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class SearchPresenter implements SearchItemsOutputBoundary {
    private final SearchViewModel searchViewModel;

    private ViewManagerModel viewManagerModel;


    public SearchPresenter(SearchViewModel searchViewModel, ViewManagerModel viewManagerModel){
        this.searchViewModel = searchViewModel;
        this.viewManagerModel = viewManagerModel;

    }


    @Override
    public void presentSearchResults(Collection<SearchItemsOutputData> items) {

        SearchState searchState = searchViewModel.getState();
        searchState.setResults(items);

        ArrayList<String> resultNames = new ArrayList<>();

        for (SearchItemsOutputData item: searchState.getResults()){
            resultNames.add(item.getName());
        }

        searchState.setResultNames(resultNames);
        // Might be more efficient to store the name of each item mapped to its specific item entity

        this.searchViewModel.setState(searchState);
        this.searchViewModel.firePropertyChanged();

        this.viewManagerModel.setActiveView(searchViewModel.getViewName()); // would this apply in search?
        viewManagerModel.firePropertyChanged();

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

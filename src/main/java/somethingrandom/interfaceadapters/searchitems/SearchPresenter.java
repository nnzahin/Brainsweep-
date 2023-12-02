package somethingrandom.interfaceadapters.searchitems;

import somethingrandom.entity.Item;
import somethingrandom.interfaceadapters.ViewManagerModel;
import somethingrandom.usecase.searchitems.SearchItemsOutputData;

import javax.swing.text.View;
import java.util.Collection;

public class SearchPresenter implements SearchItemsOutputBoundary{
    private final SearchViewModel searchViewModel;

    private ViewManagerModel viewManagerModel;

    public SearchPresenter(SearchViewModel searchViewModel, ViewManagerModel viewManagerModel){
        this.searchViewModel = searchViewModel;
        this.viewManagerModel = viewManagerModel;
    }

    public void presentSearchResults(SearchItemsOutputData result){
        SearchState searchState = searchViewModel.getState();
        searchState.setResults(result.getItems());
        this.searchViewModel.setState(searchState);
        this.searchViewModel.firePropertyChanged();

        viewManagerModel.setActiveView(searchViewModel.getViewName());
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
        state.getSearchError();
        searchViewModel.firePropertyChanged();
    }


}

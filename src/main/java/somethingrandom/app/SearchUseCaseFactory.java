package somethingrandom.app;
import somethingrandom.interfaceadapters.ViewManagerModel;
import somethingrandom.interfaceadapters.searchitems.SearchController;
import somethingrandom.interfaceadapters.searchitems.SearchPresenter;
import somethingrandom.interfaceadapters.searchitems.SearchViewModel;
import somethingrandom.usecase.search.SearchItemsDataAccessInterface;
import somethingrandom.usecase.search.SearchItemsInputBoundary;
import somethingrandom.usecase.search.SearchItemsInteractor;
import somethingrandom.usecase.search.SearchItemsOutputBoundary;
import somethingrandom.view.SearchView;

import javax.swing.*;
import java.io.IOException;
import java.time.Clock;

public class SearchUseCaseFactory {

    /*
    Not actually sure what this does, I just mirrored the CA use case factory from class
     */
    private SearchUseCaseFactory(){}

    public static SearchView create(ViewManagerModel viewManagerModel, SearchViewModel searchViewModel, SearchItemsDataAccessInterface searchDataAccessObject, Clock clock){
        try {
            SearchController searchController = createSearchUseCase(viewManagerModel, searchViewModel, searchDataAccessObject, clock);

            return new SearchView(searchController, searchViewModel);
        }
        catch (IOException e){
            JOptionPane.showMessageDialog(null, "Item not found.");
        }
        return null;
    }

    private static SearchController createSearchUseCase(ViewManagerModel viewManagerModel, SearchViewModel searchViewModel, SearchItemsDataAccessInterface searchDataAccessObject, Clock clock) throws IOException {
        SearchItemsOutputBoundary searchItemsOutputBoundary = new SearchPresenter(searchViewModel, viewManagerModel);
        SearchItemsInputBoundary searchInteractor = new SearchItemsInteractor(searchDataAccessObject, searchItemsOutputBoundary, clock);
        return new SearchController(searchInteractor);
    }


}

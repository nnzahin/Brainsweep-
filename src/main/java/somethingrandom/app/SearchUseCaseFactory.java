package somethingrandom.app;
import somethingrandom.entity.Item;
import somethingrandom.interfaceadapters.ViewManagerModel;
import somethingrandom.interfaceadapters.searchitems.SearchController;
import somethingrandom.interfaceadapters.searchitems.SearchPresenter;
import somethingrandom.interfaceadapters.searchitems.SearchState;
import somethingrandom.interfaceadapters.searchitems.SearchViewModel;
import somethingrandom.usecase.DataAccessException;
import somethingrandom.usecase.search.SearchItemsDataAccessInterface;
import somethingrandom.usecase.search.SearchItemsInputBoundary;
import somethingrandom.usecase.search.SearchItemsInteractor;
import somethingrandom.usecase.search.SearchItemsOutputBoundary;
import somethingrandom.view.SearchView;

import javax.swing.*;
import java.io.IOException;
import java.time.Clock;
import java.util.ArrayList;
import java.util.Collection;

public class SearchUseCaseFactory {

    private SearchUseCaseFactory(){}

    public static SearchView create(ViewManagerModel viewManagerModel, SearchViewModel searchViewModel, SearchItemsDataAccessInterface searchDataAccessObject){
        try {

            // Retrieving names for default view
            ArrayList<String> allItemNames = new ArrayList<>();
            try {
                Collection<Item> allItems = searchDataAccessObject.getAllItems();
                for (Item item: allItems){
                    allItemNames.add(item.getName());
                }

                // this is also very iffy.
                SearchState state = new SearchState();

                searchViewModel.setState(state);
                state.setResultNames(allItemNames);

            }
            catch (DataAccessException e){
                JOptionPane.showMessageDialog(null, "Error");

                // I don't think this is right, please give suggestions.
            }

            SearchController searchController = createSearchUseCase(viewManagerModel, searchViewModel, searchDataAccessObject);

            // I believe this makes the default task list
            return new SearchView(searchController, searchViewModel);

        }
        catch (IOException e){
            JOptionPane.showMessageDialog(null, "Item not found.");
        }
        return null;
    }

    private static SearchController createSearchUseCase(ViewManagerModel viewManagerModel, SearchViewModel searchViewModel, SearchItemsDataAccessInterface searchDataAccessObject) throws IOException {
        SearchItemsOutputBoundary searchItemsOutputBoundary = new SearchPresenter(searchViewModel, viewManagerModel);
        Clock clock = Clock.systemUTC();
        SearchItemsInputBoundary searchInteractor = new SearchItemsInteractor(searchDataAccessObject, searchItemsOutputBoundary, clock);
        return new SearchController(searchInteractor);
    }

}

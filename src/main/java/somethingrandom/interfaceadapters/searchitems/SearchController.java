package somethingrandom.interfaceadapters.searchitems;
import somethingrandom.usecase.search.SearchItemsInputBoundary;
import somethingrandom.usecase.search.SearchItemsInputData;

public class SearchController {
    final SearchItemsInputBoundary searchItemUseCaseInteractor;

    public SearchController(SearchItemsInputBoundary searchItemsUseCaseInteractor){
        this.searchItemUseCaseInteractor = searchItemsUseCaseInteractor;
    }
    public void execute(String itemName){
        SearchItemsInputData searchItemsInputData = new SearchItemsInputData(itemName);
        searchItemUseCaseInteractor.execute(searchItemsInputData);
    }
}

package somethingrandom.interfaceadapters.searchitems;

import somethingrandom.usecase.edititem.EditItemInputBoundary;
import somethingrandom.usecase.searchitems.SearchItemsInputData;

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

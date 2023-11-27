package somethingrandom.usecase;

public class SearchItemsInteractor implements SearchItemsInputBoundary{

    final SearchItemsDataAccessInterface searchItemsDataAccessObject;

    final SearchItemsOutputBoundary searchItemsPresenter;

    public SearchItemsInteractor(SearchItemsDataAccessInterface searchItemsDataAccessInterface,
                                 SearchItemsOutputBoundary searchItemsOutputBoundary ){
        this.searchItemsDataAccessObject = searchItemsDataAccessInterface;
        this.searchItemsPresenter= searchItemsOutputBoundary;
    }

    @Override
    public void execute(SearchItemsInputData searchItemsInputData) {
        //not sure how we're implementing this yet.
    }


}

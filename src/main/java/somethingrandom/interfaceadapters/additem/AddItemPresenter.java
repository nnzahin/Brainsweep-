package somethingrandom.interfaceadapters.additem;

import somethingrandom.interfaceadapters.ViewManagerModel;
import somethingrandom.usecase.add.AddItemOutputBoundary;


public class AddItemPresenter implements AddItemOutputBoundary{
    private final AddItemViewModel addItemViewModel;
    private ViewManagerModel viewManagerModel;

    public AddItemPresenter(AddItemViewModel viewModel, ViewManagerModel viewManagerModel) {
        addItemViewModel = viewModel;
        viewManagerModel = viewManagerModel;
    }

    public void prepareSuccessView(String response){
        AddItemState addItemState = addItemViewModel.getState();
    }

    @Override
    public void prepareFailView() {

    }

}

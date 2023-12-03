package somethingrandom.interfaceadapters.additem;

import somethingrandom.interfaceadapters.ViewManagerModel;
import somethingrandom.interfaceadapters.additem.AddItemState;
import somethingrandom.interfaceadapters.additem.AddItemViewModel;
import somethingrandom.usecase.AddItemOutputBoundary;
import somethingrandom.usecase.AddItemOutputData;


public class AddItemPresenter implements AddItemOutputBoundary{
    private final AddItemViewModel addItemViewModel;
    private ViewManagerModel viewManagerModel;

    public AddItemPresenter(AddItemViewModel viewModel, ViewManagerModel viewManagerModel) {
        addItemViewModel = viewModel;
        this.viewManagerModel = viewManagerModel;
    }

    public void prepareSuccessView(String response){
        AddItemState addItemState = addItemViewModel.getState();
    }

    @Override
    public void prepareFailView() {

    }

}

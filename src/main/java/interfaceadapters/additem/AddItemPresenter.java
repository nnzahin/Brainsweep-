package interfaceadapters.additem;

import interfaceadapters.ViewManagerModel;
import usecase.additem.AddItemOutputBoundary;


public class AddItemPresenter implements AddItemOutputBoundary{
    private final AddItemViewModel addItemViewModel;

    private ViewManagerModel viewManagerModel;

    public AddItemPresenter(AddItemViewModel viewModel) {
        addItemViewModel = viewModel;
    }

    public void prepareSuccessView(String response){
        AddItemState addItemState = addItemViewModel.getState();
    }

}

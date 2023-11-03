package somethingrandom.interfaceadapters.additem;

import somethingrandom.interfaceadapters.ViewManagerModel;
import somethingrandom.interfaceadapters.additem.AddItemState;
import somethingrandom.interfaceadapters.additem.AddItemViewModel;
import somethingrandom.usecase.AddItemOutputBoundary;
import somethingrandom.usecase.AddItemOutputData;


public class AddItemPresenter implements AddItemOutputBoundary{
    private final AddItemViewModel addItemViewModel;

    private ViewManagerModel viewManagerModel;

    public void prepareSuccessView(String response){
        AddItemState addItemState = addItemViewModel.getState();
    }

}

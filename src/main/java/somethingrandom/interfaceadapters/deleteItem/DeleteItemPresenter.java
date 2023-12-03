package somethingrandom.interfaceadapters.deleteItem;

import somethingrandom.interfaceadapters.ViewManagerModel;
import somethingrandom.usecase.delete.DeleteItemOutputBoundary;

public class DeleteItemPresenter implements DeleteItemOutputBoundary {
    private final DeleteItemViewModel deleteItemViewModel;

    private final ViewManagerModel viewManager;

    public DeleteItemPresenter(DeleteItemViewModel viewModel, ViewManagerModel viewManager) {
        this.deleteItemViewModel = viewModel;
        this.viewManager = viewManager;
    }

    @Override
    public void presentSuccessView(String message){

    }

    @Override
    public void presentFailureView(String message) {

    }
}

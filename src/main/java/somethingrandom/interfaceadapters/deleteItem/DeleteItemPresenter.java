package somethingrandom.interfaceadapters.deleteItem;

import somethingrandom.interfaceadapters.ViewManagerModel;
import somethingrandom.interfaceadapters.details.ItemDetailsViewModel;
import somethingrandom.usecase.delete.DeleteItemOutputBoundary;
import somethingrandom.view.ItemDetailsView;

public class DeleteItemPresenter implements DeleteItemOutputBoundary {
    private final ItemDetailsViewModel viewModel;

    public DeleteItemPresenter(ItemDetailsViewModel viewModel ) {
        this.viewModel = viewModel;
    }

    @Override
    public void presentSuccessView(String message){

    }

    @Override
    public void presentFailureView(String message) {

    }
}

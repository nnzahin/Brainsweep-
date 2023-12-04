package somethingrandom.interfaceadapters.delete;

import somethingrandom.interfaceadapters.details.ItemDetailsViewModel;
import somethingrandom.usecase.delete.DeleteItemOutputBoundary;

public class DeleteItemPresenter implements DeleteItemOutputBoundary {
    private ItemDetailsViewModel view;
    public DeleteItemPresenter(ItemDetailsViewModel view) {
        this.view = view;
    }

    @Override
    public void presentSuccessView(){
        DeleteItemState state = new DeleteItemState(true);
    }

    @Override
    public void presentFailureView() {
        DeleteItemState state = new DeleteItemState(false);
    }
}

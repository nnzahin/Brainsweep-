package somethingrandom.interfaceadapters.delete;

import somethingrandom.interfaceadapters.details.ItemDetailsViewModel;

public class DeleteItemPresenter {
    private ItemDetailsViewModel view;
    public DeleteItemPresenter(ItemDetailsViewModel view) {
        this.view = view;
    }

    public void prepareSuccessView(){
        DeleteItemState state = new DeleteItemState(true);
    }

    public void prepareFailView() {
        DeleteItemState state = new DeleteItemState(false);
    }
}

package somethingrandom.interfaceadapters.details;

import somethingrandom.usecase.details.ItemDetailsInputBoundary;

import java.util.UUID;

public class ItemDetailsController {
    private final ItemDetailsInputBoundary usecase;
    private final ItemDetailsViewModel viewModel;

    public ItemDetailsController(ItemDetailsInputBoundary usecase, ItemDetailsViewModel viewModel) {
        this.usecase = usecase;
        this.viewModel = viewModel;
    }

    public void requestDetails(UUID uuid) {
        if (uuid == null) {
            viewModel.setErrorMessage(null);
            viewModel.setState(null);
            return;
        }

        usecase.showDetailsForId(uuid);
    }
}

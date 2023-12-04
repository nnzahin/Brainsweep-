package somethingrandom.interfaceadapters.details;

import somethingrandom.usecase.details.ItemDetailsInputBoundary;

import java.util.UUID;

public class ItemDetailsController {
    private final ItemDetailsInputBoundary usecase;

    public ItemDetailsController(ItemDetailsInputBoundary usecase) {
        this.usecase = usecase;
    }

    public void requestDetails(UUID uuid) {
        usecase.showDetailsForId(uuid);
    }
}

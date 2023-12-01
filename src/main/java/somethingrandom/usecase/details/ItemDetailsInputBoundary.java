package somethingrandom.usecase.details;

import java.util.UUID;

/**
 * Used to request the interactor to display item details.
 */
public interface ItemDetailsInputBoundary {
    /**
     * Requests that the interactor query and present details for the
     * item with the provided identifier.
     *
     * @param id The ID to query.
     */
    void showDetailsForId(UUID id);
}

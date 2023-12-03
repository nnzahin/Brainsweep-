package somethingrandom.usecase.details;

/**
 * An interface to present the details of items.
 * <p>
 * Methods here are called after the interactor finished the request; it
 * calls presentDetails on success, and presentFailure if an error occurred.
 */
public interface ItemDetailsOutputBoundary {
    /**
     * Called if the request succeeded, and the panel should display the provided
     * data.
     *
     * @param details The data that should be displayed.
     */
    void presentDetails(ItemDetailsOutputData details);

    /**
     * Called if the request failed. The panel should display an error message.
     *
     * @param message The error message that should be displayed.
     */
    void presentFailure(String message);
}

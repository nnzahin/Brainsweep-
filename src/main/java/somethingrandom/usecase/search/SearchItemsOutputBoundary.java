package somethingrandom.usecase.search;

import java.util.Collection;

/**
 * The interface for presenters that wish to be used to display search results.
 */
public interface SearchItemsOutputBoundary {
    /**
     * Called when the search completes successfully, along with the list of
     * corresponding entities.
     * <p>
     * As they are provided in a Collection, there is no guarantee of order.
     * It's expected that the view be responsible for this.
     */
    void presentSearchResults(Collection<SearchItemsOutputData> items);

    /**
     * Called if the search fails for some reason. The view should notify
     * the user, preferably non-invasively.
     *
     * @param message The message that should be displayed to the user.
     */
    void presentSearchFailure(String message);
}

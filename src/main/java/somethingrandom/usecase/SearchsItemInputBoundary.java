package somethingrandom.usecase;

/**
 * Represents the public interface to the 'search' use case.
 * <p>
 * This is used by both the item listing controller/presenter and the (upcoming)
 * item search controller/presenter.
 */
public interface SearchsItemInputBoundary {
    /**
     * Executes a search with the provided query.
     * <p>
     * Found matches will be placed into the use case's associated presenter.
     */
    void search();
}

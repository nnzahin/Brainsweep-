package somethingrandom.usecase;

/**
 * Represents an error that occurred while accessing data.
 *
 * This is used to indicate that fetching the data failed for some reason in
 * a way the use-cases need to worry about; possibilities include a API error,
 * network error, or similar.
 *
 * (This is in the usecase package so usecase doesn't know anything about
 * data_access, i.e. to comply with the dependency rule.)
 */
public class DataAccessException extends Exception {
    /**
     * Creates a new DataAccessException with the provided message.
     *
     * @param message The message to associate.
     */
    public DataAccessException(String message) {
        super(message);
    }

    /**
     * Creates a new DataAccessException wrapping the provided exception.
     *
     * @param e The throwable to wrap.
     */
    public DataAccessException(Throwable e) {
        super(e);
    }
}

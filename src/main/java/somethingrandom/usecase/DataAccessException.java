package somethingrandom.usecase;

public class DataAccessException extends Exception {
    public DataAccessException(Exception e) {
        super(e);
    }

    public DataAccessException(String message) {
        super(message);
    }
}

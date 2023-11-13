package somethingrandom.dataaccess.google.auth;

import somethingrandom.usecase.DataAccessException;

/**
 * An AuthenticationException represents an error that specifically occurred
 * while authentication, probably while refreshing a token.
 */
public class AuthenticationException extends DataAccessException {
    /**
     * Creates a new AuthenticationException with the provided message.
     *
     * @param message The message to associate with the exception.
     */
    public AuthenticationException(String message) {
        super(message);
    }

    /**
     * Creates a new exception that 'wraps' the throwable.
     *
     * @param e The throwable to associate.
     */
    public AuthenticationException(Throwable e) {
        super(e);
    }
}

package somethingrandom.dataaccess.google.auth;

import somethingrandom.usecase.DataAccessException;

public class AuthenticationException extends DataAccessException {
    public AuthenticationException(String message) {
        super(message);
    }

    public AuthenticationException(Exception e) {
        super(e);
    }
}

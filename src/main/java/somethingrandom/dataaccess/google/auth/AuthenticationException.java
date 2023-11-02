package somethingrandom.dataaccess.google.auth;

public class AuthenticationException extends Exception {
    AuthenticationException(Exception e) {
        super(e);
    }
}

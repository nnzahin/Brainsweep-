package somethingrandom.dataaccess.google.auth;

public class LoginFailureException extends Exception {
    LoginFailureException(Exception e) {
        super(e);
    }
}

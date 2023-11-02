package somethingrandom.dataaccess.google.auth;

public class GoogleTokenSource implements Token.Source {
    private final CodeVerifier verifier;
    private final String code;

    public GoogleTokenSource(CodeVerifier verifier, String code) {
        this.verifier = verifier;
        this.code = code;
    }

    @Override
    public Token.ExpiringToken requestToken() {
        throw new RuntimeException("not implemented");
    }
}

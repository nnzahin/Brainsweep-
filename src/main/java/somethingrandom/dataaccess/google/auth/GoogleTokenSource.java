package somethingrandom.dataaccess.google.auth;

import okhttp3.*;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

class GoogleTokenSource implements Token.Source {
    private final OkHttpClient client;
    private final AuthenticationSession session;

    private static final HttpUrl TOKEN_ENDPOINT = HttpUrl.parse("https://oauth2.googleapis.com/token");

    GoogleTokenSource(OkHttpClient client, AuthenticationSession authenticationSession) {
        this.client = client;
        this.session = authenticationSession;
    }

    @Override
    public Token.ExpiringToken requestToken() throws AuthenticationException {
        Request req = new Request.Builder()
            .addHeader("Content-Type", "application/x-www-form-urlencoded")
            .url(TOKEN_ENDPOINT)
            .post(getRequestBody())
            .build();

        try (Response response = client.newCall(req).execute()) {
            JSONObject parsed = new JSONObject(response.body().string());

            String token = parsed.getString("access_token");
            Number expiresAfterTime = parsed.getNumber("expires_in");

            Instant expiry = Instant.now().plus(expiresAfterTime.longValue(), ChronoUnit.SECONDS);
            return new Token.ExpiringToken(token, expiry);
        } catch (IOException | JSONException e) {
            throw new AuthenticationException(e);
        }
    }

    private RequestBody getRequestBody() {
        return new FormBody.Builder()
            .add("client_id", LoginFlow.OAUTH_CLIENT_ID)
            .add("client_secret", session.clientSecret())
            .add("code", session.code())
            .add("grant_type", "authorization_code")
            .add("code_verifier", session.verifier().getCodeVerifier())
            .add("redirect_uri", session.redirectUrl())
            .build();
    }
}

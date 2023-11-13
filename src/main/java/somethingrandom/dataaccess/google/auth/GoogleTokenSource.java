package somethingrandom.dataaccess.google.auth;

import okhttp3.*;
import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Objects;
import java.util.Optional;

@SuppressWarnings("KotlinInternalInJava")
class GoogleTokenSource implements Token.Source {
    private final OkHttpClient client;
    private final AuthenticationSession session;

    private Optional<String> refreshToken = Optional.empty();

    private static final @NotNull HttpUrl TOKEN_ENDPOINT = Objects.requireNonNull(HttpUrl.parse("https://oauth2.googleapis.com/token"));

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
            if (response.body() == null || response.code() != 200) {
                throw new AuthenticationException(String.format("Could not redeem token: %s", response.message()));
            }

            JSONObject parsed = new JSONObject(response.body().string());

            String token = parsed.getString("access_token");
            Number expiresAfterTime = parsed.getNumber("expires_in");

            Instant expiry = Instant.now().plus(expiresAfterTime.longValue(), ChronoUnit.SECONDS);

            if (refreshToken.isEmpty()) {
                refreshToken = Optional.of(token);
            }

            return new Token.ExpiringToken(token, expiry);
        } catch (IOException | JSONException e) {
            throw new AuthenticationException(e);
        }
    }

    private RequestBody getRequestBody() {
        FormBody.Builder builder = new FormBody.Builder();

        builder.add("client_id", LoginFlow.OAUTH_CLIENT_ID);
        builder.add("client_secret", session.clientSecret());

        if (refreshToken.isEmpty()) {
            builder.add("code", session.code());
            builder.add("grant_type", "authorization_code");
            builder.add("code_verifier", session.verifier().getCodeVerifier());
            builder.add("redirect_uri", session.redirectUrl());
        } else {
            builder.add("refresh_token", refreshToken.get());
        }

        return builder.build();
    }
}

package dataaccess.google;

import okhttp3.*;
import org.json.JSONObject;
import dataaccess.google.auth.AuthenticationException;
import dataaccess.google.auth.Token;

import java.io.IOException;

/**
 * OkHttpAPIProvider is an API provider that uses the OkHttp3 library to make
 * its requests.
 * <p>
 * This is the primary one used in Brainsweep as of writing.
 */
class OkHttpAPIProvider implements APIProvider {
    private final OkHttpClient client;
    private final Token token;

    /**
     * Creates an API provider for the given HTTP client and token.
     *
     * @param client The client to execute requests with.
     * @param token The token to authenticate requests with.
     */
    public OkHttpAPIProvider(OkHttpClient client, Token token) {
        this.client = client;
        this.token = token;
    }

    @Override
    public JSONObject request(APIRequestBody body, String url) throws IOException, AuthenticationException {
        RequestBody requestBody;
        if (body.getContent() == null) {
            requestBody = null;
        } else {
            requestBody = RequestBody.create(body.getContent(), MediaType.get(body.getMimeType()));
        }

        Request request = new Request.Builder()
            .url(url)
            .method(body.getMethod(), requestBody)
            .addHeader("Authorization", "Bearer " + token.getToken())
            .get().build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful() || response.body() == null) {
                throw new IOException(response.message());
            }

            return new JSONObject(response.body().string());
        }
    }
}

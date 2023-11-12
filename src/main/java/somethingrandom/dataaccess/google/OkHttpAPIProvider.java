package somethingrandom.dataaccess.google;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONObject;
import somethingrandom.dataaccess.google.auth.AuthenticationException;
import somethingrandom.dataaccess.google.auth.Token;

import java.io.IOException;

public class OkHttpAPIProvider implements APIProvider {
    private final OkHttpClient client;
    private final Token token;

    public OkHttpAPIProvider(OkHttpClient client, Token token) {
        this.client = client;
        this.token = token;
    }

    @Override
    public JSONObject request(String url) throws IOException, AuthenticationException {
        Request request = new Request.Builder()
            .url(url)
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

package somethingrandom.dataaccess.google;

import org.json.JSONObject;
import somethingrandom.dataaccess.google.auth.AuthenticationException;

import java.io.IOException;

/**
 * An APIProvider abstracts the process of connecting to an API.
 * <p>
 * The singular 'request' method is used to connect to the Google API. A full
 * URL is provided, and the parsed result is returned, so it's easy to replace
 * HTTP libraries or to test without involving HTTP.
 */
public interface APIProvider {
    /**
     * Makes the HTTP request to the provided URL.
     * <p>
     * The request will be made with the required authentication, along with
     * the specified request body.
     *
     * @param body The data to accompany the request.
     * @param url The URL to request.
     * @return The parsed JSON API result.
     * @throws IOException on network errors
     * @throws AuthenticationException if the token could not be refreshed
     */
    JSONObject request(APIRequestBody body, String url) throws IOException, AuthenticationException;

    class Constant implements APIProvider {
        private final String source;

        public Constant(String source) {
            this.source = source;
        }

        @Override
        public JSONObject request(APIRequestBody body, String url) {
            return new JSONObject(source);
        }
    }
}

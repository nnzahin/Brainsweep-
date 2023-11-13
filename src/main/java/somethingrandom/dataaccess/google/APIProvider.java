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
     * @param url The URL to request.
     * @return The parsed JSON API result.
     * @throws IOException on network errors
     * @throws AuthenticationException if the token could not be refreshed
     */
    JSONObject request(Body body, String url) throws IOException, AuthenticationException;

    /**
     * Represents the additional data to be sent with the request.
     * <p>
     * This entails the request method, content, and MIME type.
     */
    interface Body {
        String getMethod();
        String getContent();
        String getMimeType();
    }

    /**
     * A GetBody is a Body with no content using the GET method.
     */
    class GetBody implements Body {
        @Override
        public String getMethod() {
            return "GET";
        }

        @Override
        public String getContent() {
            return null;
        }

        @Override
        public String getMimeType() {
            return null;
        }
    }

    class Constant implements APIProvider {
        private final String source;

        public Constant(String source) {
            this.source = source;
        }

        @Override
        public JSONObject request(Body body, String url) {
            return new JSONObject(source);
        }
    }
}

package somethingrandom.dataaccess.google;

import org.jetbrains.annotations.Nullable;
import org.json.JSONObject;

/**
 * Represents the additional data to be sent with the request.
 * <p>
 * This entails the request method, content, and MIME type.
 */
public interface APIRequestBody {
    /**
     * Gets the HTTP method that should be used for the request.
     *
     * This will probably be "GET" or "POST".
     *
     * @return the method to use
     */
    String getMethod();

    /**
     * Gets the content of this request body, or null if there isn't any.
     *
     * @return the content of the body
     */
    @Nullable
    String getContent();

    /**
     * Gets the MIME type of the body.
     *
     * This will be used in the request's Content-Type.
     *
     * @return the MIME type associated with the request
     */
    String getMimeType();

    /**
     * A GetBody is a Body with no content using the GET method.
     */
    class GetBody implements APIRequestBody {
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

    /**
     * A JSONBody is a Body containing a JSONObject.
     *
     * It uses the method specified in the constructor.
     */
    class JSONBody implements APIRequestBody {
        private final String method;
        private final JSONObject payload;

        public JSONBody(String method, JSONObject payload) {
            this.method = method;
            this.payload = payload;
        }

        @Override
        public String getMethod() {
            return method;
        }

        @Override
        public String getContent() {
            return this.payload.toString();
        }

        @Override
        public String getMimeType() {
            return "application/json";
        }
    }
}

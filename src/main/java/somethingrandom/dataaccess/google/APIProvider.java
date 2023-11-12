package somethingrandom.dataaccess.google;

import org.json.JSONObject;
import somethingrandom.dataaccess.google.auth.AuthenticationException;

import java.io.IOException;

public interface APIProvider {
    JSONObject request(String url) throws IOException, AuthenticationException;

    class Constant implements APIProvider {
        private final String source;

        public Constant(String source) {
            this.source = source;
        }

        @Override
        public JSONObject request(String url) {
            return new JSONObject(source);
        }
    }
}

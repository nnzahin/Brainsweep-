package somethingrandom.dataaccess.google.auth;

import okhttp3.*;
import org.json.JSONObject;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.net.*;
import java.security.SecureRandom;
import java.time.Clock;
import java.util.Scanner;

// https://developers.google.com/identity/protocols/oauth2/native-app

@SuppressWarnings("KotlinInternalInJava")
public class LoginFlow {
    private static final String OAUTH_CLIENT_ID = "1094948025113-ej3dvu6bs0ctmsfftd8p677o9mkv16m0.apps.googleusercontent.com";
    private final CodeVerifier verifier;
    private final OkHttpClient httpClient;
    private final String clientSecret;
    private int usedPort = 0;

    public static void main(String[] args) throws IOException {
        LoginFlow c = new LoginFlow(new OkHttpClient(), System.getenv("OAUTH_CLIENT_SECRET"), new S256CodeVerifier(new SecureRandom()));
        String code = c.getAuthorizationCode();
        System.out.println("got authorization code: " + code);
        String token = c.getAuthorizationToken(code);
        System.out.println("got authorization token: " + token);

        c.addEvent(token, "It works on October 2nd");
    }

    public LoginFlow(OkHttpClient httpClient, String clientSecret, CodeVerifier verifier) {
        this.httpClient = httpClient;
        this.clientSecret = clientSecret;
        this.verifier = verifier;

        if (clientSecret == null) {
            throw new IllegalArgumentException("clientSecret cannot be null");
        }
    }

    /**
     * Starts the login process. This will synchronously start a Web browser and
     * Web server, wait for the user to finish, then return a token.
     *
     * @return A token that can be used to authenticate.
     * @throws LoginFailureException if an exception occurred while logging in.
     */
    public Token execute() throws LoginFailureException {
        try {
            String code = getAuthorizationCode();
            String token = getAuthorizationToken(code);

            return new Token(new GoogleTokenSource(verifier, code), Clock.systemUTC());
        } catch (Exception e) {
            throw new LoginFailureException(e);
        }
    }

    private String getAuthorizationCode() throws IOException {
        // This code is PERFECT and will never need refactoring.
        ServerSocket server = new ServerSocket();
        server.bind(new InetSocketAddress(0));

        usedPort = server.getLocalPort();

        String uri = "https://accounts.google.com/o/oauth2/v2/auth";
        uri += "?client_id=" + OAUTH_CLIENT_ID;
        uri += "&redirect_uri=http://127.0.0.1:" + usedPort;
        uri += "&scope=https://www.googleapis.com/auth/calendar";
        uri += "&code_challenge=" + verifier.getCodeChallenge();
        uri += "&code_challenge_method=" + verifier.getMethodName();
        uri += "&response_type=code";

        URI parsed;
        try {
            parsed = new URI(uri);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }

        Desktop.getDesktop().browse(parsed);

        while (true) {
            Socket socket = server.accept();
            InputStream stream = socket.getInputStream();
            Scanner sc = new Scanner(stream);
            String line = sc.nextLine();

            HttpUrl provided = HttpUrl.parse("http://127.0.0.1/" + line.split("\\s")[1]);
            assert provided != null;

            String code = provided.queryParameter("code");
            if (code != null) {
                socket.getOutputStream().write("HTTP/1.0 200 OK\r\nContent-Type: text/plain; charset=utf-8\r\n\r\nAll done! You can go back to Notetaker now.".getBytes());
                socket.close();
                server.close();
                return code;
            }

            socket.getOutputStream().write("HTTP/1.0 400 Bad Request\r\nContent-Type: text/plain; charset=utf-8\\r\\n\\r\\nThe request wasn't valid; this is probably our bug.".getBytes());
            socket.close();
        }
    }

    private String getAuthorizationToken(String code) throws IOException {
        HttpUrl url = new HttpUrl.Builder()
                .scheme("https")
                .host("oauth2.googleapis.com")
                .addPathSegment("token")
                .build();

        RequestBody body = new FormBody.Builder()
                .add("client_id", OAUTH_CLIENT_ID)
                .add("client_secret", clientSecret)
                .add("code", code)
                .add("grant_type", "authorization_code")
                .add("code_verifier", verifier.getCodeVerifier())
                .add("redirect_uri", "http://127.0.0.1:" + usedPort)
                .build();

        Request req = new Request.Builder()
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .url(url)
                .post(body)
                .build();
        try (Response response = httpClient.newCall(req).execute()) {
            JSONObject parsed = new JSONObject(response.body().string());

            return parsed.getString("access_token");
        }
    }

    private void addEvent(String token, String eventName) throws IOException {
        HttpUrl url = new HttpUrl.Builder()
                .scheme("https")
                .host("www.googleapis.com")
                .addPathSegments("calendar/v3/calendars/primary/events/quickAdd")
                .addQueryParameter("text", eventName)
                .build();

        Request req = new Request.Builder()
                .post(RequestBody.create(new byte[0]))
                .url(url)
                .addHeader("Authorization", "Bearer " + token)
                .build();

        try (Response resp = httpClient.newCall(req).execute()) {
            System.out.println("event creation gave " + resp.code() + ": " + resp.message());
            System.out.println(resp.body().string());
        }
    }
}

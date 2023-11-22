package dataaccess.google.auth;

import okhttp3.*;

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
    static final String OAUTH_CLIENT_ID = "1094948025113-ej3dvu6bs0ctmsfftd8p677o9mkv16m0.apps.googleusercontent.com";
    private final CodeVerifier verifier;
    private final OkHttpClient httpClient;
    private final String clientSecret;
    private int usedPort = 0;

    public static void main(String[] args) throws AuthenticationException, IOException {
        LoginFlow c = new LoginFlow(new OkHttpClient(), System.getenv("OAUTH_CLIENT_SECRET"), new S256CodeVerifier(new SecureRandom()));
        Token t = c.execute();
        System.out.println("got authorization token: " + t.getToken().substring(0, 5) + "...");

        c.addEvent(t, "It works on October 2nd");
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
     * @throws AuthenticationException if an exception occurred while logging in.
     */
    public Token execute() throws AuthenticationException {
        try {
            String code = getAuthorizationCode();
            AuthenticationSession session = new AuthenticationSession(verifier, code, getRedirectUrl(), clientSecret);

            return new Token(new GoogleTokenSource(httpClient, session), Clock.systemUTC());
        } catch (Exception e) {
            throw new AuthenticationException(e);
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

    private String getRedirectUrl() {
        return "http://127.0.0.1:" + usedPort;
    }

    private void addEvent(Token token, String eventName) throws IOException, AuthenticationException {
        HttpUrl url = new HttpUrl.Builder()
                .scheme("https")
                .host("www.googleapis.com")
                .addPathSegments("calendar/v3/calendars/primary/events/quickAdd")
                .addQueryParameter("text", eventName)
                .build();

        Request req = new Request.Builder()
                .post(RequestBody.create(new byte[0]))
                .url(url)
                .addHeader("Authorization", "Bearer " + token.getToken())
                .build();

        try (Response resp = httpClient.newCall(req).execute()) {
            System.out.println("event creation gave " + resp.code() + ": " + resp.message());
            System.out.println(resp.body().string());
        }
    }
}

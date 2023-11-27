package somethingrandom.dataaccess.google;

import okhttp3.OkHttpClient;
import somethingrandom.dataaccess.google.auth.LoginFlow;
import somethingrandom.dataaccess.google.auth.S256CodeVerifier;
import somethingrandom.entity.ActionableItem;
import somethingrandom.entity.ReferenceItem;
import somethingrandom.usecase.DataAccessException;

import java.security.SecureRandom;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

public class Demo {
    public static void main(String[] args) throws DataAccessException {
        OkHttpClient client = new OkHttpClient();
        LoginFlow loginFlow = new LoginFlow(client, System.getenv("OAUTH_CLIENT_SECRET"), new S256CodeVerifier(new SecureRandom()), GoogleDataAccessObject.getScopes());

        GoogleDataAccessObject dao = new GoogleDataAccessObject(client, loginFlow.execute(), "Brainsweep");
        dao.save(new ReferenceItem("Hello, world!", UUID.randomUUID(), Instant.now(), "This is a description."));
        dao.save(new ActionableItem("Hello, world!", UUID.randomUUID(), Instant.now().plus(1, ChronoUnit.DAYS), Duration.of(5, ChronoUnit.MINUTES)));
    }
}

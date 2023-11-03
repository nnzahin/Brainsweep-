package somethingrandom.dataaccess.google.auth;

import org.junit.Before;
import org.junit.Test;

import java.util.Base64;
import java.util.Random;

import static org.junit.Assert.*;

public class S256CodeVerifierTest {
    private S256CodeVerifier verifier;

    private static final String EXPECTED_VERIFIER = "oeOEhP5R6y--hpeuz1HxzCEe2T.2GaClsQ-~d.juBuvrjJj7H~proe-i-swd6Hr6";
    // found by: `printf '%s' "oeOEhP5R6y--hpeuz1HxzCEe2T.2GaClsQ-~d.juBuvrjJj7H~proe-i-swd6Hr6" | openssl sha256 -binary | base64`
    private static final String EXPECTED_CHALLENGE = "zWO2hLND8vOn9Csq7l6h3PJMUf0r8PUEeZq3F3vXREU";

    @Before
    public void before() {
        verifier = new S256CodeVerifier(new Random(1000));
    }

    @Test
    public void shouldHaveCorrectIdentifier() {
        assertEquals("S256", verifier.getMethodName());
    }

    @Test
    public void shouldHaveBase64EncodedChallenge() {
        String challenge = verifier.getCodeChallenge();
        Base64.Decoder decoder = Base64.getUrlDecoder();

        try {
            byte[] result = decoder.decode(challenge);

            for (byte b : result) {
                assertNotEquals('=', b);
            }
        } catch (IllegalArgumentException e) {
            fail("base64 format was not valid");
        }
    }

    @Test
    public void shouldChangeVerifierWithDifferentSeed() {
        S256CodeVerifier other = new S256CodeVerifier(new Random(1001));
        assertNotEquals(verifier.getCodeVerifier(), other.getCodeVerifier());
        assertNotEquals(verifier.getCodeChallenge(), other.getCodeChallenge());
    }

    @Test
    public void shouldNotChangeWithSameSeed() {
        S256CodeVerifier other = new S256CodeVerifier(new Random(1000));

        assertEquals(EXPECTED_VERIFIER, verifier.getCodeVerifier());
        assertEquals(EXPECTED_VERIFIER, other.getCodeVerifier());

        assertEquals(EXPECTED_CHALLENGE, verifier.getCodeChallenge());
        assertEquals(EXPECTED_CHALLENGE, other.getCodeChallenge());
    }
}

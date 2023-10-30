package somethingrandom.dataaccess.google.auth;

import okio.ByteString;

import java.nio.charset.StandardCharsets;
import java.util.Random;

/**
 * A CodeVerifier is a challenge sent during the OAuth authentication flow. It prevents
 * another application from hijacking the authentication token by requiring a code to
 * be sent during the initial creation of the login screen and redemption of the token.
 */
public abstract class CodeVerifier {
    private final String challenge;
    private final String verifier;

    protected CodeVerifier(Random random) {
        byte[] verifierBytes = generateVerifier(random);
        byte[] challengeBytes = generateChallenge(verifierBytes);

        verifier = ByteString.of(verifierBytes).string(StandardCharsets.US_ASCII);
        challenge = ByteString.of(challengeBytes).string(StandardCharsets.US_ASCII);
    }

    private byte[] generateVerifier(Random random) {
        byte[] bytes = new byte[64];
        random.nextBytes(bytes);

        for (int i = 0; i < bytes.length; i++) {
            while (!isValidVerifierCharacter((char) bytes[i])) {
                bytes[i] = (byte) Math.abs(random.nextInt());
            }
        }

        return bytes;
    }

    private static boolean isValidVerifierCharacter(int ch) {
        if (ch > 127) {
            return false;
        }

        return Character.isAlphabetic(ch)
            || Character.isDigit(ch)
            || ch == '-'
            || ch == '.'
            || ch == '_'
            || ch == '~';
    }

    /**
     * Generates the challenge for the provided verifier.
     *
     * @return A URL-safe challenge string.
     */
    protected abstract byte[] generateChallenge(byte[] verifier);

    /**
     * Gets the identifier of the verifier's method. This should be in a format acceptable
     * by an OAuth endpoint.
     */
    public abstract String getMethodName();

    /**
     * Gets the verifier code. This should be sent when redeeming the token.
     *
     * @return The verifier as a string. This will be URL-safe.
     */
    public String getCodeVerifier() {
        return verifier;
    }

    /**
     * Gets the challenge for this verifier. This should be sent when creating the sign-in
     * screen.
     *
     * @return The challenge as a string. This will be URL-safe.
     */
    public String getCodeChallenge() {
        return challenge;
    }
}

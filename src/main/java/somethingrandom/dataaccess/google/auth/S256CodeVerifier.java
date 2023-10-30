package somethingrandom.dataaccess.google.auth;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Random;

public class S256CodeVerifier extends CodeVerifier {
    public S256CodeVerifier(Random random) {
        super(random);
    }

    @Override
    public byte[] generateChallenge(byte[] verifier) {
        MessageDigest digest;

        try {
            digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            // SHA-256 is common enough that we will assume it is present.
            throw new RuntimeException(e);
        }

        byte[] buffer = Base64.getUrlEncoder().encode(digest.digest(verifier));
        int i = 0;
        while (i < buffer.length && buffer[i] != '=') {
            i += 1;
        }

        return Arrays.copyOf(buffer, i);
    }

    @Override
    public String getMethodName() {
        return "S256";
    }
}

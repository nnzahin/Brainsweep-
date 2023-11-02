package somethingrandom.dataaccess.google.auth;

import org.jetbrains.annotations.Nullable;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

/**
 * A Token is an automatically-refreshing string. It expires at the end of some duration
 * suggested by the Token.Source given.
 * <p>
 * This is used to store OAuth tokens, since they expire reasonably quickly.
 * <p>
 * To use this, you'll need to provide:
 *
 * <ul>
 *     <li>a Token.Source implementation, to provide the tokens;</li>
 *     <li>a Clock, to provide the current time; and</li>
 *     <li>(optionally) a Duration where it should be expired even if it's technically valid.</li>
 * </ul>
 *
 * By default, tokens will expire 30s before the deadline, to prevent problems from
 * network issues.
 */
public class Token {
    private final Source source;
    private final Clock clock;
    private final Duration earlyExpiryWindow;
    @Nullable private ExpiringToken cached;

    /**
     * A token broken down into its string and expiration date.
     *
     * This is primarily returned from Token.Source, and shouldn't be used elsewhere.
     *
     * @param token The string version of the token.
     * @param expiry The instant the token expires.
     */
    public record ExpiringToken(String token, Instant expiry) {}

    /**
     * A source which tokens can be obtained from.
     */
    public interface Source {
        /**
         * Requests a new token, and blocks synchronously until one is available.
         *
         * @return The new token, if successful.
         * @throws AuthenticationException upon failure.
         */
        ExpiringToken requestToken() throws AuthenticationException;
    }

    /**
     * Creates a new refreshing token with the source and clock.
     * <p>
     * A default 30s timeout is used.
     *
     * @param source The source to request tokens from.
     * @param clock The clock to refer to.
     */
    public Token(Source source, Clock clock) {
        this(source, clock, Duration.of(30, ChronoUnit.SECONDS));
    }

    /**
     * Creates a new refreshing token with the source, clock, and refresh window.
     *
     * @param source The source to request tokens from.
     * @param clock The clock to refer to.
     * @param earlyExpiryWindow The window before expiry where the token should refresh.
     */
    public Token(Source source, Clock clock, Duration earlyExpiryWindow) {
        this.source = source;
        this.clock = clock;
        this.earlyExpiryWindow = earlyExpiryWindow;
    }

    /**
     * Gets the current value of the token, refreshing it if needed.
     *
     * @return The current token.
     */
    public String getToken() throws AuthenticationException {
        if (cached == null || cached.expiry().minus(earlyExpiryWindow).isBefore(clock.instant())) {
            cached = source.requestToken();
        }

        return cached.token();
    }
}

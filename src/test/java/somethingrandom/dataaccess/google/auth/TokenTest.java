package somethingrandom.dataaccess.google.auth;

import org.junit.Before;
import org.junit.Test;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Optional;

import static org.junit.Assert.*;

public class TokenTest {
    private Clock fixedClock;

    @Before
    public void setUp() {
        fixedClock = Clock.fixed(Instant.parse("2023-10-31T18:11:54.00Z"), ZoneId.of("-04:00"));
    }

    private static class TickableClock extends Clock {
        private Instant instant = Instant.MIN;

        public void setInstant(Instant instant) {
            this.instant = instant;
        }

        @Override
        public ZoneId getZone() {
            return ZoneId.of("UTC");
        }

        @Override
        public Clock withZone(ZoneId zoneId) {
            throw new RuntimeException("not supported");
        }

        @Override
        public Instant instant() {
            return this.instant;
        }
    }

    private Token.Source createCountingSource(Instant expiry) {
        return new Token.Source() {
            int uses = 0;

            @Override
            public Token.ExpiringToken requestToken() {
                uses += 1;
                return new Token.ExpiringToken("token #" + uses, expiry);
            }
        };
    }

    @Test
    public void shouldRequestFirstToken() {
        Token token = new Token(createCountingSource(Instant.MAX), fixedClock);
        assertEquals(token.getToken(), "token #1");
    }

    @Test
    public void shouldCacheTokens() {
        Token token = new Token(createCountingSource(Instant.MAX), fixedClock);

        assertEquals(token.getToken(), "token #1");
        assertEquals(token.getToken(), "token #1");
    }

    @Test
    public void shouldExpireTokens() {
        final Instant initialTime = Instant.parse("2023-11-02T14:37:00.00Z");
        final Instant expiresWhen = Instant.parse("2023-11-02T14:47:00.00Z");
        final Instant secondAttempt = Instant.parse("2023-11-02T14:58:00.00Z");

        TickableClock clock = new TickableClock();

        Token token = new Token(createCountingSource(initialTime), clock);

        assertEquals("token #1", token.getToken());
        clock.setInstant(expiresWhen);
        assertEquals("token #2", token.getToken());
        clock.setInstant(secondAttempt);
        assertEquals("token #3", token.getToken());
    }

    @Test
    public void shouldExpireTokensWithinEarlyWindow() {
        final Instant initialTime = Instant.parse("2023-11-02T14:37:00.00Z");
        final Instant expiresWhen = Instant.parse("2023-11-02T14:46:31.00Z");

        TickableClock clock = new TickableClock();

        Token token = new Token(createCountingSource(initialTime), clock);

        assertEquals("token #1", token.getToken());
        clock.setInstant(expiresWhen);
        assertEquals("token #2", token.getToken());
    }

    @Test
    public void shouldNotRequestTokenWhenConstructed() {
        final Instant initialTime = Instant.parse("2023-11-02T14:37:00.00Z");
        final Instant expiresWhen = Instant.parse("2023-11-02T14:47:00.00Z");
        final Instant secondAttempt = Instant.parse("2023-11-02T14:58:00.00Z");

        TickableClock clock = new TickableClock();

        Token token = new Token(createCountingSource(initialTime), clock);

        clock.setInstant(expiresWhen);
        assertEquals("token #1", token.getToken());
    }
}

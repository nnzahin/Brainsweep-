package somethingrandom.usecase.search;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

public class SearchItemsResult {
    private final String name;
    private final UUID uuid;
    private @Nullable Instant couldBeReadyWhen = null;

    /**
     * Creates a new search result containing just the name.
     * <p>
     * This is primarily intended for use with reference items.
     *
     * @param name The name of the item.
     * @param uuid The identifier of the item.
     */
    SearchItemsResult(String name, UUID uuid) {
        this.name = name;
        this.uuid = uuid;
    }

    /**
     * Creates a new search result containing the name, and a duration.
     * <p>
     * In addition, the time the query was submitted should be provided. This
     * ensures that differences in time getting results don't change how they
     * are ordered.
     * <p>
     * This is primarily intended for use with actionable items.
     *
     * @param name The name of the item.
     * @param querySubmitted The time when the use-case was executed.
     * @param duration How long the item is expected to complete.
     */
    SearchItemsResult(String name, UUID uuid, Instant querySubmitted, Duration duration) {
        this.name = name;
        this.uuid = uuid;
        this.couldBeReadyWhen = querySubmitted.plus(duration);
    }

    /**
     * Creates a new search result containing the name and a timestamp.
     * <p>
     * This is primarily intended for delayed items.
     *
     * @param name The name of the item.
     * @param uuid The identifier of the item.
     * @param reminder The relevant time of the item.
     */
    SearchItemsResult(String name, UUID uuid, @NotNull Instant reminder) {
        this.name = name;
        this.uuid = uuid;
        this.couldBeReadyWhen = reminder;
    }

    /**
     * Gets the name of the result.
     *
     * @return The result's name.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the UUID associated with this result.
     *
     * @return The result's UUID.
     */
    public UUID getUUID() {
        return uuid;
    }

    @Nullable
    public Instant getRelevantInstant() {
        return couldBeReadyWhen;
    }
}

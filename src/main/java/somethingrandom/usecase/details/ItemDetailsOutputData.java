package somethingrandom.usecase.details;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.Instant;

/**
 * Stores information about an item that would be useful in the details pane.
 */
public class ItemDetailsOutputData {
    private final String title;
    private final String description;
    private final Instant creationTime;

    ItemDetailsOutputData(String title, String description, Instant creationTime) {
        this.title = title;
        this.description = description;
        this.creationTime = creationTime;
    }

    /**
     * Gets the title of the item.
     *
     * @return The item's title.
     */
    public @NotNull String getTitle() {
        return title;
    }

    /**
     * Gets the description of the item, or null if it does not have one.
     *
     * @return The item's description, or null.
     */
    public @Nullable String getDescription() {
        return description;
    }

    /**
     * Returns the time when the item was created.
     *
     * @return The item's creation date.
     */
    public @NotNull Instant getCreationTime() {
        return creationTime;
    }
}

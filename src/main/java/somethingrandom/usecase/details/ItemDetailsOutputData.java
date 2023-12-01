package somethingrandom.usecase.details;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.Instant;

/**
 * Stores information about an item that would be useful in the details pane.
 */
public class ItemDetailsOutputData {
    private final @NotNull String title;
    private final @Nullable String description;
    private final @NotNull Instant creationTime;

    public ItemDetailsOutputData(@NotNull String title, @NotNull Instant creationTime) {
        this.title = title;
        this.description = null;
        this.creationTime = creationTime;
    }

    public ItemDetailsOutputData(@NotNull String title, @Nullable String description, @NotNull Instant creationTime) {
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

package somethingrandom.interfaceadapters;

import java.time.Duration;
import java.time.Instant;

/**
 * A controller that can be used with the item-editing dialog.
 */
public interface ItemDialogController {
    /**
     * Requests to save the item and close the dialog.
     */
    void finished(String title, String plannedItemKind, String description, Instant remindAt, Duration duration);
}

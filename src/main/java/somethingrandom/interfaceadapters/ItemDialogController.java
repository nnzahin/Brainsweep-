package somethingrandom.interfaceadapters;

/**
 * A controller that can be used with the item-editing dialog.
 */
public interface ItemDialogController {
    /**
     * Requests to save the item and close the dialog.
     */
    void finished(String title, String description);
}

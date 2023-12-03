package somethingrandom.interfaceadapters.details;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Map;

/**
 * Stores the information currently visible in the item details panel.
 */
public class ItemDetailsViewModel {
    public static final String EDIT_LABEL = "Edit";
    public static final String DELETE_LABEL = "Delete";

    static final String NOTHING_HERE_TEXT = "Nothing is selected";

    private final PropertyChangeSupport support = new PropertyChangeSupport(this);
    private @Nullable Map<String, String> state = null;
    private @NotNull String errorMessage = NOTHING_HERE_TEXT;

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    void setState(@Nullable Map<String, String> state) {
        Map<String, String> oldState = this.state;
        this.state = state;
        support.firePropertyChange("state", oldState, this.state);
    }

    public @Nullable Map<String, String> getState() {
        return state;
    }

    void setErrorMessage(@Nullable String errorMessage) {
        String oldMessage = this.errorMessage;
        this.errorMessage = errorMessage == null ? NOTHING_HERE_TEXT : errorMessage;
        support.firePropertyChange("errorMessage", oldMessage, this.errorMessage);
    }

    @NotNull
    public String getErrorMessage() {
        return errorMessage;
    }
}

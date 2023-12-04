package somethingrandom.interfaceadapters.edit;


import somethingrandom.interfaceadapters.ViewModel;
import somethingrandom.interfaceadapters.edit.EditState;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class EditViewModel extends ViewModel {
    public EditViewModel() {super("Edit Item");}

    public static final String EDIT_BUTTON_LABEL = "Edit";
    public static final String CANCEL_BUTTON_LABEL = "x";
    public static final String SAVE_BUTTON_LABEL = "Save";
    public static final String TITLE_LABEL = "Update Item";
    public static final String DISC_LABEL = "Description";
    private EditState state = new EditState();
    public void setState(EditState state) {this.state = state;}
    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    public void firePropertyChanged() {support.firePropertyChange("state", null, this.state);}

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }
    public EditState getState(){return state;}

}

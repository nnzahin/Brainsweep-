package interfaceadapters.additem;

import interfaceadapters.ViewModel;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class AddItemViewModel extends ViewModel {
    public static final String CANCEL_BUTTON_LABEL = "x";
    public static final String TITLE_LABEL = "Add Item";
    private AddItemState state = new AddItemState();
    public AddItemViewModel() {super("Add Item");}
    public void setState(AddItemState state) {this.state = state;}
    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    public void firePropertyChanged() {support.firePropertyChange("state", null, this.state);}

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }
    public AddItemState getState(){return state;}

}

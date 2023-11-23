package interfaceadapters.listitems;

import interfaceadapters.ViewModel;
import view.ListItemsView;
import interfaceadapters.listitems.ListItemsState;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class ListItemsViewModel extends ViewModel {


    public static final String REFERENCE_ITEMS_BUTTON_LABEL = "Reference Items";
    public static final String ACTIONABLE_ITEMS_BUTTON_LABEL = "Actionable Items";
    public static final String DELAYED_ITEMS_BUTTON_LABEL = "Delayed Items";
    public static final String ADD_BUTTON_LABEL = "Add";
    public static final String SEARCH_BUTTON_LABEL = "Search";

    private ListItemsState state = new ListItemsState();

    public ListItemsViewModel(String viewName) {
        super(viewName);
    }

    public void setState(ListItemsState state){this.state = state;}

    public ListItemsState getState(){return state;}

    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    @Override
    public void firePropertyChanged() {support.firePropertyChange("state", null, this.state);

    }


    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {

    }

}

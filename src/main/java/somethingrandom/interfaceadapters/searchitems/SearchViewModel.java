package somethingrandom.interfaceadapters.searchitems;


import somethingrandom.interfaceadapters.ViewModel;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class SearchViewModel extends ViewModel {
    public static final String SEARCH_LABEL = "Search View";

    public static final String SEARCH_BUTTON_LABEL = "Search";

    private SearchState state = new SearchState();

    public SearchViewModel(){super("Search");}

    public void setState(SearchState state){this.state = state;}

    public SearchState getState(){return this.state;}
    private final PropertyChangeSupport support = new PropertyChangeSupport(this);


    @Override
    public void firePropertyChanged() {support.firePropertyChange("state", null, this.state);}


    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);

    }

}

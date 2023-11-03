package somethingrandom.interfaceadapters;

import java.beans.PropertyChangeSupport;

public class ViewManagerModel {
    private String activeViewName;
    private final PropertyChangeSupport support = new PropertyChangeSupport(this);
    public String getActiveView() {
        return activeViewName;
    }
    public void setActiveView(String activeView) {
        this.activeViewName = activeView;
    }
}

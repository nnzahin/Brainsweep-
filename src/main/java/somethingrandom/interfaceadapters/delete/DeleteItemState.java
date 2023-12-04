package somethingrandom.interfaceadapters.delete;

import somethingrandom.interfaceadapters.additem.AddItemState;

public class DeleteItemState {
    private final boolean success;
    public DeleteItemState(boolean success) {
        this.success = success;
    }
    public boolean itemDeleted(){return success;}
}

package somethingrandom.usecase.searchitems;

import somethingrandom.entity.Item;

import java.util.Collection;

public class SearchItemsOutputData {

    private final Collection<Item> items;

    private boolean useCaseFailed;

    public SearchItemsOutputData(Collection<Item> items, boolean useCaseFailed){
        this.items = items;
        this.useCaseFailed = useCaseFailed;
    }
    public Collection<Item> getItems(){return this.items;}

}

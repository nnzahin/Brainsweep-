package somethingrandom.usecase;

import somethingrandom.entity.Item;

public class SearchItemsOutputData {

    private final Item item;

    private boolean useCaseFailed;

    public SearchItemsOutputData(Item item, boolean useCaseFailed){
        this.item = item;
        this.useCaseFailed = useCaseFailed;
    }

}

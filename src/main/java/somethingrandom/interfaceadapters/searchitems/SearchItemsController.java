package somethingrandom.interfaceadapters.searchitems;

import somethingrandom.usecase.SearchsItemInputBoundary;

public class SearchItemsController {
    private final SearchsItemInputBoundary boundary;

    public SearchItemsController(SearchsItemInputBoundary boundary) {
        this.boundary = boundary;
    }

    public void refresh() {
        boundary.search();
    }
}

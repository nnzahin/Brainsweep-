package somethingrandom.interfaceadapters.searchitems;

import somethingrandom.usecase.SearchItemsInputBoundary;

public class SearchItemsController {
    private final SearchItemsInputBoundary boundary;

    public SearchItemsController(SearchItemsInputBoundary boundary) {
        this.boundary = boundary;
    }

    public void refresh() {
        boundary.search();
    }
}

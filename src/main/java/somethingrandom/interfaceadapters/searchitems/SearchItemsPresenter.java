package somethingrandom.interfaceadapters.searchitems;

import somethingrandom.entity.Item;
import somethingrandom.usecase.SearchItemsOutputBoundary;

import java.util.Collection;

public class SearchItemsPresenter implements SearchItemsOutputBoundary {
    private final SearchItemsViewModel viewModel;

    public SearchItemsPresenter(SearchItemsViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void succeeded(Collection<Item> items) {

    }

    @Override
    public void failed(String message) {

    }
}

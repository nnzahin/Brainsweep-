package somethingrandom.interfaceadapters.details;

import somethingrandom.usecase.details.ItemDetailsOutputBoundary;
import somethingrandom.usecase.details.ItemDetailsOutputData;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class ItemDetailsPresenter implements ItemDetailsOutputBoundary {
    private final ItemDetailsViewModel viewModel;
    private final Locale locale;
    private final ZoneId zone;

    public ItemDetailsPresenter(ItemDetailsViewModel viewModel, Locale locale, ZoneId zone) {
        this.viewModel = viewModel;
        this.locale = locale;
        this.zone = zone;
    }

    @Override
    public void presentDetails(ItemDetailsOutputData details) {
        Map<String, String> state = new HashMap<>();

        state.put("Title", details.getTitle());

        if (details.getDescription() != null) {
            state.put("Description", details.getDescription());
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM).withLocale(locale).withZone(zone);
        state.put("Created", formatter.format(details.getCreationTime()));

        viewModel.setState(state);
    }

    @Override
    public void presentFailure(String message) {
        viewModel.setErrorMessage(message);
        viewModel.setState(null);
    }
}

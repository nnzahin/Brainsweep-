package somethingrandom.interfaceadapters.details;

import org.junit.Before;
import org.junit.Test;
import somethingrandom.usecase.details.ItemDetailsOutputData;

import java.time.Instant;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static org.junit.Assert.*;

public class ItemDetailsPresenterTest {
    private ItemDetailsViewModel viewModel;
    private ItemDetailsPresenter presenter;

    @Before
    public void setup() {
        viewModel = new ItemDetailsViewModel();
        presenter = new ItemDetailsPresenter(viewModel, Locale.ENGLISH, ZoneId.of("-04:00"));
    }

    @Test
    public void shouldShowErrorMessageOnFailure() {
        presenter.presentFailure("Failed");
        assertEquals("Failed", viewModel.getErrorMessage());
        assertNull(viewModel.getState());
    }

    @Test
    public void shouldClearExistingStateOnFailure() {
        viewModel.setState(new HashMap<>());

        presenter.presentFailure("Failed");
        assertNull(viewModel.getState());
    }

    @Test
    public void shouldShowAllCommonItemProperties() {
        Instant timestamp = Instant.parse("2023-12-01T00:56:00-04:00");
        ItemDetailsOutputData details = new ItemDetailsOutputData("The Title", timestamp);
        presenter.presentDetails(details);

        Map<String, String> state = viewModel.getState();
        assertNotNull(state);
        assertEquals("The Title", state.get("Title"));
        assertFalse(state.containsKey("Description"));
        // Varies with user's system settings; fixed English and -04:00 time zone for testing.
        // (\202f is a non-breaking string, used by Java's formatting to prevent 12:56:00
        // AM)
        assertEquals("Dec 1, 2023, 12:56:00" + "\u202f" + "AM", state.get("Created"));
    }

    @Test
    public void shouldShowAllReferenceItemProperties() {
        Instant timestamp = Instant.parse("2023-12-01T00:56:00-04:00");
        ItemDetailsOutputData details = new ItemDetailsOutputData("The Title", "This Description", timestamp);
        presenter.presentDetails(details);

        Map<String, String> state = viewModel.getState();
        assertNotNull(state);
        assertEquals("The Title", state.get("Title"));
        assertEquals("This Description", state.get("Description"));
        // Varies with user's system settings; fixed English and -04:00 time zone for testing.
        // (\202f is a non-breaking string, used by Java's formatting to prevent 12:56:00
        // AM)
        assertEquals("Dec 1, 2023, 12:56:00" + "\u202f" + "AM", state.get("Created"));
    }
}

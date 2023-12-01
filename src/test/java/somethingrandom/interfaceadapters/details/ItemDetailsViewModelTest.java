package somethingrandom.interfaceadapters.details;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class ItemDetailsViewModelTest {
    private ItemDetailsViewModel viewModel;

    @Before
    public void setup() {
        viewModel = new ItemDetailsViewModel();
    }

    @Test
    public void shouldIndicateErrorMessageIfNoState() {
        assertNotNull(viewModel.getErrorMessage());
        assertFalse(viewModel.getErrorMessage().isEmpty());
    }

    @Test
    public void shouldProvideSetErrorMessage() {
        viewModel.setErrorMessage("Error!");

        assertEquals("Error!", viewModel.getErrorMessage());
    }

    @Test
    public void shouldProvideSetState() {
        Map<String, String> state = new HashMap<>();
        viewModel.setState(state);

        assertEquals(state, viewModel.getState());
    }

    @Test
    public void shouldCallListenerOnStateChange() {
        Map<String, String> state = new HashMap<>();
        final boolean[] ok = {false}; // allow mutating from the listener

        viewModel.addPropertyChangeListener(propertyChangeEvent -> {
            if (propertyChangeEvent.getPropertyName().equals("state")) {
                ok[0] = true;
                assertNull(propertyChangeEvent.getOldValue());
                assertEquals(state, propertyChangeEvent.getNewValue());
            }
        });

        viewModel.setState(state);
        assertTrue(ok[0]);
    }

    @Test
    public void shouldCallListenerOnErrorMessageChange() {
        final boolean[] ok = {false}; // allow mutating from the listener

        viewModel.addPropertyChangeListener(propertyChangeEvent -> {
            if (propertyChangeEvent.getPropertyName().equals("errorMessage")) {
                ok[0] = true;
                assertEquals(ItemDetailsViewModel.NOTHING_HERE_TEXT, propertyChangeEvent.getOldValue());
                assertEquals("Error!!", propertyChangeEvent.getNewValue());
            }
        });

        viewModel.setErrorMessage("Error!!");
        assertTrue(ok[0]);
    }
}

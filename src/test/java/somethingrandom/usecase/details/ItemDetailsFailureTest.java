package somethingrandom.usecase.details;

import org.junit.Before;
import org.junit.Test;
import somethingrandom.usecase.DataAccessException;

import java.util.Optional;
import java.util.UUID;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class ItemDetailsFailureTest {
    private DummyPresenter presenter;

    @Before
    public void setup() {
        presenter = new DummyPresenter();
    }

    @Test
    public void shouldPresentFailureIfNoItemExists() {
        ItemDetailsInputBoundary usecase = new ItemDetailsInteractor(uuid -> Optional.empty(), presenter);

        usecase.showDetailsForId(UUID.fromString("634f6f85-7cb8-4af5-9cc3-17fec4dcf0b6"));
        assertNotNull(presenter.message);
    }

    @Test
    public void shouldPresentFailureIfDataAccessThrows() {
        ItemDetailsInputBoundary usecase = new ItemDetailsInteractor(uuid -> {
            throw new DataAccessException("Exception Message");
        }, presenter);

        usecase.showDetailsForId(UUID.fromString("634f6f85-7cb8-4af5-9cc3-17fec4dcf0b6"));
        assertTrue(presenter.message.contains("Exception Message"));
    }
}

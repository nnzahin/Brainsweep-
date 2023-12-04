package somethingrandom.interfaceadapters.details;

import org.junit.Before;
import org.junit.Test;
import somethingrandom.usecase.details.ItemDetailsInputBoundary;

import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class ItemDetailsControllerTest {
    private ItemDetailsController controller;
    private DummyUseCase usecase;

    private static class DummyUseCase implements ItemDetailsInputBoundary {
        public UUID uuid;

        @Override
        public void showDetailsForId(UUID id) {
            assertNull(this.uuid);
            uuid = id;
        }
    }

    @Before
    public void setup() {
        usecase = new DummyUseCase();
        controller = new ItemDetailsController(usecase, new ItemDetailsViewModel());
    }

    @Test
    public void shouldCallUseCaseWithUUID() {
        UUID uuid = UUID.fromString("8abe0dd8-f944-4df6-9315-eb8df82eb52b");
        controller.requestDetails(uuid);
        assertEquals(uuid, usecase.uuid);
    }
}

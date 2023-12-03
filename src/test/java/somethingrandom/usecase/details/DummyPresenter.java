package somethingrandom.usecase.details;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

class DummyPresenter implements ItemDetailsOutputBoundary {
    public ItemDetailsOutputData details;
    public String message;

    @Override
    public void presentDetails(ItemDetailsOutputData details) {
        assertNull(this.details);
        assertNull(this.message);
        assertNotNull(details);
        this.details = details;
    }

    @Override
    public void presentFailure(String message) {
        assertNull(this.details);
        assertNull(this.message);
        assertNotNull(message);
        this.message = message;
    }
}

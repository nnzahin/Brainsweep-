package somethingrandom.usecase.details;

import somethingrandom.entity.Item;
import somethingrandom.usecase.DataAccessException;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

class DummyDataAccess implements ItemDetailsDataAccessInterface {
    private final Map<UUID, Item> items = new HashMap<UUID, Item>();

    public void add(UUID uuid, Item item) {
        items.put(uuid, item);
    }

    @Override
    public Optional<Item> getItemById(UUID uuid) throws DataAccessException {
        return Optional.ofNullable(items.getOrDefault(uuid, null));
    }
}

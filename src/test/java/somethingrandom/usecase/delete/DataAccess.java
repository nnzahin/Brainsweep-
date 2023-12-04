package somethingrandom.usecase.delete;

import somethingrandom.entity.Item;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class DataAccess implements DeleteItemDataAccessInterface{
    private final Map<UUID, Item> data;

    public DataAccess(Map<UUID, Item> data){
        this.data = data;
    }

    public Map<UUID, Item> getData(){
        return data;
    }
    public Boolean delete(UUID id){
        return data.remove(id) != null;
    }
}

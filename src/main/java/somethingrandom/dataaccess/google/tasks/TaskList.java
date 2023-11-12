package somethingrandom.dataaccess.google.tasks;

import somethingrandom.dataaccess.google.APIProvider;

public class TaskList {
    private final APIProvider provider;
    private final String identifier;

    TaskList(APIProvider provider, String identifier) {
        this.provider = provider;
        this.identifier = identifier;
    }

    public String getIdentifier() {
        return identifier;
    }
}

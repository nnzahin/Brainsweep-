package somethingrandom.dataaccess.google.tasks;

import somethingrandom.dataaccess.google.APIProvider;

public class TaskList {
    private final APIProvider provider;
    private final String identifier;
    private final String title;

    TaskList(APIProvider provider, String title, String identifier) {
        this.provider = provider;
        this.identifier = identifier;
        this.title = title;
    }

    public String getIdentifier() {
        return identifier;
    }

    public String getTitle() {
        return title;
    }

    public APIProvider getProvider() {
        return provider;
    }
}

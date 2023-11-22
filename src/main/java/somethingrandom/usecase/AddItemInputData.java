package somethingrandom.usecase;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;

public class AddItemInputData {
    private final String name;
    private final UUID id;
    private final Instant creationDate;
    private final Duration neededTime;
    private final Instant remindDate;

    private final String description;
    private String item;
    public AddItemInputData(String item, String description) {
        this.item = item;
        this.description = description;
    }
    //AddActionable Item Constructor 1
    public AddItemInputData(String name, UUID id, Instant creationDate) {
        this.name = name;
        this.id = id;
        this.creationDate = creationDate;
        this.neededTime = null;
    }
    // AddActionable Item Constructor 2
    public AddItemInputData(String name, UUID id, Instant creationDate, Duration neededTime) {
        this.name = name;
        this.id = id;
        this.creationDate = creationDate;
        this.neededTime = neededTime;
    }
    //AddDelayed Item Constructor
    public AddItemInputData(String name, UUID id, Instant creationDate, Instant remindDate) {
        this.name = name;
        this.id = id;
        this.creationDate = creationDate;
        this.remindDate = remindDate;
    }

    //AddReferenceItem Constructor
    public AddItemInputData(String name, UUID id, Instant creationDate, String description) {
        this.name = name;
        this.id = id;
        this.creationDate = creationDate;
        this.description = description;
    }
    String getItem() {return item;}

    public String getName() {
        return name;
    }
    public void setItem(String item) {
        this.item = item;
    }

    public UUID getId() {
        return id;
    }

    public Instant getCreationDate() {
        return creationDate;
    }

    public Duration getNeededTime() {
        return neededTime;
    }

    public Instant getRemindDate() {
        return remindDate;
    }
    String getDescription() {return description;}
}

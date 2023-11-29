package somethingrandom.usecase;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;

public class AddItemInputData {
    private String name;
    private Duration neededTime;
    private Instant remindDate;

    private String description;

    /**
     * AddReferenceItem Constructor
     * Create a new item with the following details.
     *
     * @param name          The name of the item.
     * @param description   The description of the item.
     */
    public AddItemInputData(String name, String description) {
        this.name = name;
        this.description = description;
    }

    /**
     * AddActionable Item Constructor
     * Create a new item with the following details.
     *
     * @param name          The name of the item.
     * @param neededTime    The needed time to complete the item.
     */
    public AddItemInputData(String name, Duration neededTime) {
        this.name = name;
        this.neededTime = neededTime;
    }

    /**
     * Add Delayed Item Constructor
     * Create a new item with the following details.
     *
     * @param name         The name of the item.
     * @param remindDate   The reminder date of the item.
     */
    public AddItemInputData(String name, Instant remindDate) {
        this.name = name;
        this.remindDate = remindDate;
    }

    public String getName() {
        return name;
    }
    public void setName(String name){
        this.name = name;
    }

    public Duration getNeededTime() {
        return neededTime;
    }
    public void setNeededTime(Duration neededTime){
        this.neededTime = neededTime;
    }

    public Instant getRemindDate() {
        return remindDate;
    }
    public void setRemindDate(Instant remindDate){
        this.remindDate = remindDate;
    }

    public String getDescription() {return description;}
    public void setDescription(String description){
        this.description = description;
    }
}

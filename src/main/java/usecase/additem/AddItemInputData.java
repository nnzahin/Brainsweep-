package usecase.additem;
import java.util.Date;

public class AddItemInputData {
    private String item;
    private String description;
    public AddItemInputData(String item, String description) {
        this.item = item;
        this.description = description;
    }
    String getItem() {return item;}

    String getDescription() {return description;}
}

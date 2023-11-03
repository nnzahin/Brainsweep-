package somethingrandom.interfaceadapters.additem;

public class AddItemState {
    private String itemName = "";

    private String description = "";
    public AddItemState(AddItemState copy) {
        itemName = copy.itemName;
        description = copy.description;
    }
    public AddItemState(){}

    public String getItemName(){return itemName;}

    public String getDescription(){return description;}

    public void setDescription(String description){this.description = description;}

    public void setItemName(String itemName){this.itemName = itemName;}

}

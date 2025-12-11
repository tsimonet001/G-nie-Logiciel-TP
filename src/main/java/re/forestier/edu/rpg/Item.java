package re.forestier.edu.rpg;

public class Item {
    private String name;
    private String description;
    private int weight;
    private int value;

    public Item(String name, String description, int weight, int value) {
        this.name = name;
        this.description = description;
        this.weight = weight;
        this.value = value;
    }

    public String getName() { return name; }
    public String getDescription() { return description; }
    public int getWeight() { return weight; }
    public int getValue() { return value; }

    @Override
    public String toString() {
        return name + " : " + description;
    }
}
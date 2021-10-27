package dungeonmania;


// assume that:
// items that need to be triggered to use;
// potions, bombs
// items that are auto-used:
// weaponry, armour/shields, one-ring

public abstract class Item {
    private String id;
    private String type;
    private int usesLeft;

    public Item (String id, String type) {
        this.id = id;
        this.type = type;
        this.usesLeft = 1;
    }

    public void activate(Character character) {
        this.usesLeft = usesLeft - 1;
    }

    // basic getters and setters
    
    /**
     * @return String return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @return String return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @return int return the usesLeft
     */
    public int getUsesLeft() {
        return usesLeft;
    }

    /**
     * @param usesLeft the usesLeft to set
     */
    public void setUsesLeft(int usesLeft) {
        this.usesLeft = usesLeft;
    }

}

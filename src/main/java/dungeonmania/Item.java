package dungeonmania;

import dungeonmania.util.Position;

public abstract class Item extends Entity {
    private int usesLeft;

    public Item(Position position, Dungeon dungeon) {
        super(position, dungeon);
        this.usesLeft = 1;
    }

    /** 
     * decreases number of item available for player to use
     * @param player
     */
    public void activate(Player player) {
        this.usesLeft = usesLeft - 1;
    }

    // basic getters and setters

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

package dungeonmania;

import dungeonmania.util.Position;


// assume that:
// items that need to be triggered to use;
// potions, bombs
// items that are auto-used:
// weaponry, armour/shields, one-ring

public abstract class Item extends Entity {
    private int usesLeft;

    public Item(Position position, Dungeon dungeon) {
        super(position, dungeon);
        this.usesLeft = 1;
    }

    public void activate(Character character) {
        this.usesLeft = usesLeft - 1;
    }

    // for polymorphism
    public boolean correctKey(Door door) {
        return false;
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

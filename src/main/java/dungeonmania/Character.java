package dungeonmania;

import dungeonmania.util.Position;
import java.util.ArrayList;
import java.util.List;

public class Character extends MovingEntity {
    private Inventory inventory = new Inventory();
    public static final int ORIGINAL_HEALTH = 5;
    // private Dungeon dungeon;

    public Character(Position position, String id) {
        super(position, id, "Character");
        this.setHealth(ORIGINAL_HEALTH);
    }

    /**
     * @return Inventory return the inventory
     */
    public Inventory getInventory() {
        return inventory;
    }

}

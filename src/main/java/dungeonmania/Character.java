
package dungeonmania;

import dungeonmania.util.Position;
import java.util.ArrayList;
import java.util.List;

public class Character extends MovingEntity {
    private Inventory inventory = new Inventory();
    private CharacterState characterState;
    public static final int ORIGINAL_HEALTH = 5;
    // public static final int ORIGINAL_ATTACK_DAMAGE = 3;
    // private Dungeon dungeon;


    public Character(Position position, String id) {
        super(position, id, "Character");
        this.setHealth(ORIGINAL_HEALTH);
        this.characterState = new StandardState(this);
    }


    // assume that:
    // items that need to be triggered to use;
    // potions, bombs
    // items that are auto-used:
    // weaponry, armour/shields, one-ring - if we try to use these ourselves, they will not do anything -> reference implementation
    public void useItem(String type) {
        if (type.equals("HealthPotion") || type.equals("InvincibilityPotion") || type.equals("InvisibilityPotion") || type.equals("Bomb")) {
            inventory.use(type, this);
        }
    }


    // basic getters and setters

    /**
     * @return Inventory return the inventory
     */
    public Inventory getInventory() {
        return inventory;
    }
    
    /**
     * @return CharacterState return the characterState
     */
    public CharacterState getCharacterState() {
        return characterState;
    }

    /**
     * @param characterState the characterState to set
     */
    public void setCharacterState(CharacterState characterState) {
        this.characterState = characterState;
    }

}


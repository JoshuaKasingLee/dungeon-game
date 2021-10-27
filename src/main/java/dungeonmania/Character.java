
package dungeonmania;

import dungeonmania.util.Position;
import dungeonmania.util.Direction;
import java.util.ArrayList;
import java.util.List;

public class Character extends MovingEntity {
    private Inventory inventory = new Inventory();
    private CharacterState characterState;
    public static final int ORIGINAL_HEALTH = 10;
    public static final int CHARACTER_ATTACK_DAMAGE = 3;
    // private Dungeon dungeon;


    public Character(Position position, String id, Dungeon dungeon) {
        super(position, id, "Character", dungeon);
        this.setHealth(ORIGINAL_HEALTH);
        this.setAttackDamage(CHARACTER_ATTACK_DAMAGE);
        this.characterState = new StandardState(this);
    }

    @Override
    public boolean checkValidMove(Position pos, Direction dir) {
        
        return true;
    }

    // assume we pick items after we fight enemies
    @Override
    public void move(Direction direction) {
        super.move(direction);
        fightEnemies(getPosition());
        pickItems(getPosition());
        characterState.updateState();
    }


    public void pickItems(Position pos) {
        
    }

    public void fightEnemies(Position pos) {
    
    }

    // assume that:
    // items that need to be triggered to use;
    // potions, bombs
    // items that are auto-used:
    // weaponry, armour/shields, one-ring - if we try to use these ourselves, they will not do anything -> reference implementation
    public void useItem(String type) {
        if (type.equals("HealthPotion") || type.equals("InvincibilityPotion") || type.equals("InvisibilityPotion") || type.equals("Bomb")) {
            inventory.use(type, this);
        } else if (type.equals(null) || type.equals("")) {
            // do nothing
        } else {
            throw new IllegalArgumentException("Cannot use" + type);
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

    public static void main(String[] args) {   

    }

}


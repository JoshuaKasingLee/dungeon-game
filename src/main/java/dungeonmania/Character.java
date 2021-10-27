
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
        super.checkValidMove(pos, dir);
        // check for doors
        for (Entity e : getDungeon().getEntities(pos)) {
            if (e instanceof Boulder) {
                checkUnlockedDoor(pos.translateBy(dir));
            }
            if (e instanceof Door) {
                checkUnlockedDoor(pos);
            }
        }

        // check for obstructions
        for (Entity e : getDungeon().getEntities(pos)) {
            // assume can't walk on top of spawner
            if (e instanceof Wall || e instanceof ZombieToastSpawner || !checkUnlockedDoor(pos)) {
                return false;
            }
            // assume boulders never exist on the edge of the dungeon (i.e. there is always a wall border)
            // assume boulder can be pushed onto items/other moving entities
            if (e instanceof Boulder) {
                Position newPos = pos.translateBy(dir);
                for (Entity e1 : getDungeon().getEntities(newPos)) {
                    if (e1 instanceof Wall || e1 instanceof Boulder || e1 instanceof ZombieToastSpawner || !checkUnlockedDoor(newPos)) {
                        return false;
                    }
                }   
            }
        }
        return true;
    }

    
    /** 
     * checks whether door exists in position, and if so returns true if we can unlock it
     * returns false if no door or no correct key
     * @param pos
     * @return boolean
     */
    public boolean checkUnlockedDoor(Position pos) {
        Door door = getDungeon().getDoor(pos);
        if (door != null) {
            return inventory.useKey(door, this);
        } else {
            return false;
        }
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
        for (Item i : getDungeon().getItems(pos)) {
            inventory.add(i);
        }
    }

    public void fightEnemies(Position pos) {
        for (Enemy e : getDungeon().getEnemies(pos)) {
            if (!e.isAlly()) {
                characterState.battleEnemy(e);
                // if character health is below zero
                if (this.getHealth() <= 0) {
                    if (inventory.getItem("OneRing") != null) {
                        inventory.use("OneRing", this);
                    } else {
                        getDungeon().removeFrom(this);
                    }  
                }
                // if enemy health is below zero
                if (e.getHealth() <=0 ) {
                    // check for armour
                    if (e.getArmour() > 0) {
                        Armour a = new Armour(Integer.toString(inventory.count("Armour")), getDungeon());
                        inventory.add(a);
                    }
                    getDungeon().removeFrom(e);
                }
            }     
        } 
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


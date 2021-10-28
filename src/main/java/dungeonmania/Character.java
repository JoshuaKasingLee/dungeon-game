package dungeonmania;

import dungeonmania.util.Position;
import dungeonmania.util.Direction;
import java.util.ArrayList;
import java.util.List;
import dungeonmania.exceptions.InvalidActionException;

public class Character extends MovingEntity {
    private Inventory inventory = new Inventory();
    private CharacterState characterState;
    public static final int ORIGINAL_HEALTH = 10;
    public static final int CHARACTER_ATTACK_DAMAGE = 3;

    public Character(Position position, String id, Dungeon dungeon) {
        super(position, id, "Character", dungeon);
        this.setHealth(ORIGINAL_HEALTH);
        this.setAttackDamage(CHARACTER_ATTACK_DAMAGE);
        this.characterState = new StandardState(this);
    }

    // assume we pick items after we fight enemies
    @Override
    public void move(Direction direction) {
        super.move(direction);
        fightEnemies(getPosition());
        pickItems(getPosition());
        characterState.updateState();
    }

    // move checking functions

    @Override
    public boolean checkValidMove(Position pos, Direction dir) {
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

    // fighting functions

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

    // item functions

    public void pickItems(Position pos) {
        for (Item i : getDungeon().getItems(pos)) {
            inventory.add(i);
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

    // assume only need to give 1 gold to be bribed
    // assume 2 cardinally adjacent tiles does NOT mean diagonally adjacent
    public void bribe(Mercenary mercenary) {
        Position mercPos = mercenary.getPosition();
        List<Position> cardinalAdjMercPos = getCardinalAdjPositions2(mercPos);
        for (Position p : cardinalAdjMercPos) {
            if (p.equals(getPosition())) {
                inventory.use("Treasure", this); // will throw exception in use if no treasure
                mercenary.setAlly(true);
                break;
            } else {
                throw new InvalidActionException("Mercenary is not within 2 cardinal tiles");
            }
        }
    }

    public void destroySpawner(ZombieToastSpawner spawner) {

    }

    // helper functions

    // gets list of positions within 1 cardinally adjacent tiles
    private List<Position> getCardinalAdjPositions1(Position pos) {
        List<Position> cardinalAdjPositions = new ArrayList<>();
        List<Position> adjPositions = pos.getAdjacentPositions();
        cardinalAdjPositions.add(adjPositions.get(1));
        cardinalAdjPositions.add(adjPositions.get(3));
        cardinalAdjPositions.add(adjPositions.get(5));
        cardinalAdjPositions.add(adjPositions.get(7));
        return cardinalAdjPositions;
    }

    // gets list of positions within 2 cardinally adjacent tiles
    private List<Position> getCardinalAdjPositions2(Position pos) {
        List<Position> cardinalAdjPositions = new ArrayList<>();
        List<Position> adjPositions = pos.getAdjacentPositions();
        cardinalAdjPositions.add(adjPositions.get(1));
        cardinalAdjPositions.add(adjPositions.get(1).translateBy(Direction.UP));
        cardinalAdjPositions.add(adjPositions.get(3));
        cardinalAdjPositions.add(adjPositions.get(3).translateBy(Direction.RIGHT));
        cardinalAdjPositions.add(adjPositions.get(5));
        cardinalAdjPositions.add(adjPositions.get(5).translateBy(Direction.DOWN));
        cardinalAdjPositions.add(adjPositions.get(7));
        cardinalAdjPositions.add(adjPositions.get(7).translateBy(Direction.LEFT));
        return cardinalAdjPositions;
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


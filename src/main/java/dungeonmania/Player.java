package dungeonmania;

import dungeonmania.util.Position;
import dungeonmania.util.Direction;
import java.util.ArrayList;
import java.util.List;
import dungeonmania.exceptions.InvalidActionException;

public class Player extends MovingEntity {
    private Inventory inventory = new Inventory();
    private CharacterState characterState;
    public static final int ORIGINAL_HEALTH_STANDARD = 10;
    public static final int ORIGINAL_HEALTH_HARD = 7;
    public static final int CHARACTER_ATTACK_DAMAGE = 3;

    public Player(Position position, Dungeon dungeon) {
        super(position, dungeon);
        this.setHealth(dungeon.getGamemode().getStartingHealth());
        this.setAttackDamage(CHARACTER_ATTACK_DAMAGE);
        this.characterState = new StandardState(this);
    }

    public Player(Position position, Dungeon dungeon, int currHealth) {
        this(position, dungeon);
        setHealth(currHealth);
    }

     // move functions
    
    /** 
     * moves the player one unit in current direction, fighting all enemies and picking up all items
     * @param direction
     */
    @Override
    public void move(Direction direction) {
        super.move(direction);
        fightEnemies(getPosition());
        pickItems(getPosition());
        characterState.updateState();
    }

    /** 
     * returns true if a move to the given position is valid, false if else
     * @param pos
     * @param dir
     * @return boolean
     */
    @Override
    public boolean checkValidMove(Position pos, Direction dir) {
        for (Entity e : getDungeon().getEntities(pos)) {
            if (e instanceof Wall || e instanceof ZombieToastSpawner || !checkUnlockedDoor(pos)) {
                return false;
            }
            if (e instanceof Boulder) {
                System.out.println(pos.toString());
                Position newPos = pos.translateBy(dir);
                System.out.println(newPos.toString());
                for (Entity e1 : getDungeon().getEntities(newPos)) {
                    System.out.println(e1.getType());
                    if (e1 instanceof Wall || e1 instanceof Boulder || e1 instanceof ZombieToastSpawner || !checkUnlockedDoor(newPos)) {
                        System.out.println("hi");
                        return false;
                    }
                }   
            }
        }
        return true;
    }

    /** 
     * checks whether door exists in position, and if so returns true if we can unlock it
     * returns false if door and no correct key
     * @param pos
     * @return boolean
     */
    public boolean checkUnlockedDoor(Position pos) {
        for (Entity e : getDungeon().getEntities(pos)) {
            if (e instanceof Door) {
                Door door = (Door) e;
                return inventory.useKey(door, this); // if wrong key, will return false here
            } else {
                return true;
            }
        }
        return true;
    }

    // fighting functions
    
    /** 
     * simulates battle between two entities on same position
     * removes loser from dungeon, and player inherits armour if enemy possesses it
     * @param pos
     */
    public void fightEnemies(Position pos) {
        for (Entity entity : getDungeon().getEntities(pos)) {
            if (entity instanceof Enemy) {
                Enemy e = (Enemy) entity;
                if (!e.isAlly()) {
                    characterState.battleEnemy(e);
                    // if character health is below zero
                    if (this.getHealth() <= 0) {
                        if (inventory.getItem("OneRing") != null) {
                            inventory.use("OneRing", this);
                        } else {
                            getDungeon().removeEntity(this);
                        }  
                    }
                    // if enemy health is below zero
                    if (e.getHealth() <=0 ) {
                        if (e.getArmour() > 0) {
                            Armour a = new Armour(getDungeon(), e.getArmour());
                            inventory.add(a);
                        }
                        getDungeon().removeEntity(e);
                    }
                }
            }
        }
    }

    // item functions
    
    /** 
     * add items in given position to player inventory, and remove from dungeon
     * @param pos
     */
    public void pickItems(Position pos) {
        for (Entity e : getDungeon().getEntities(pos)) {
            if (e instanceof Item) {
                Item i = (Item) e;
                getDungeon().removeEntity(i);
                inventory.add(i);
            }
        }
    }

    /** 
     * allows player to activate desired item
     * throws IllegalArgumentException if item cannot be activated
     * @param type
     */
    public void useItem(String type) {
        if (type.equals("HealthPotion") || type.equals("InvincibilityPotion") || type.equals("InvisibilityPotion") || type.equals("Bomb")) {
            inventory.use(type, this);
        } else if (type.equals(null) || type.equals("")) {
        } else {
            throw new IllegalArgumentException("Cannot use" + type);
        }
    }

    // interact functions
    
    /** 
     * gives input mercenary gold in order to become an ally
     * throws InvalidActionException if no treasure or mercenary not within 2 tiles
     * @param mercenary
     */
    public void bribe(Mercenary mercenary) {
        Position mercPos = mercenary.getPosition();
        List<Position> cardinalAdjMercPos = getCardinalAdjPositions2(mercPos);
        for (Position p : cardinalAdjMercPos) {
            if (p.equals(getPosition())) {
                inventory.use("Treasure", this); // will throw exception in use if no treasure
                mercenary.setAlly(true);
                return;
            }
        }
        throw new InvalidActionException("Mercenary is not within 2 cardinal tiles");
    }
    
    /** 
     * removes input spawner from dungeon map by using weapon
     * throws InvalidActionException if player does not possess weapon or spawner is not adjacent
     * @param spawner
     */
    public void destroySpawner(ZombieToastSpawner spawner) {
        Position spawnerPos = spawner.getPosition();
        List<Position> cardinalAdjMercPos = getCardinalAdjPositions1(spawnerPos);
        for (Position p : cardinalAdjMercPos) {
            
            if (p.equals(getPosition())) {
                if (inventory.getItem("Sword") != null) {
                    inventory.use("Sword", this);
                    getDungeon().removeEntity(spawner);
                    break;
                } else if (inventory.getItem("Bow") != null) {
                    inventory.use("Bow", this);
                    getDungeon().removeEntity(spawner);
                    break;
                } else {
                    throw new InvalidActionException("Cannot destory ZombieToastspawner without weapon");
                }
            } else {
                throw new InvalidActionException("ZombieToastSpawner is not cardinally adjacent");
            }
        }
    }

    // helper functions

    /** 
     * returns list of positions within 1 cardinally adjacent tile
     * @param pos
     * @return List<Position>
     */
    private List<Position> getCardinalAdjPositions1(Position pos) {
        List<Position> cardinalAdjPositions = new ArrayList<>();
        List<Position> adjPositions = pos.getAdjacentPositions();
        cardinalAdjPositions.add(adjPositions.get(1));
        cardinalAdjPositions.add(adjPositions.get(3));
        cardinalAdjPositions.add(adjPositions.get(5));
        cardinalAdjPositions.add(adjPositions.get(7));
        return cardinalAdjPositions;
    }

    /** 
     * returns list of positions within 2 cardinally adjacent tiles
     * @param pos
     * @return List<Position>
     */
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
    
    /** 
     * @return String
     */
    @Override
    public String setType() {
        return "Player";
    }
    
    public static void main(String[] args) {
    }
}


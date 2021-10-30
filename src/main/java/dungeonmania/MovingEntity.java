package dungeonmania;

import dungeonmania.util.Position;
import dungeonmania.util.Direction;

public abstract class MovingEntity extends Entity {
    private int health;
    private int attackDamage;

    public MovingEntity(Position position, Dungeon dungeon) {
        super(position, dungeon);
        this.health = 0;
        this.attackDamage = 0;
        dungeon.addEntity(this);
    }

    // moving functions

    /** 
     * moves entity in given direction
     * @param direction
     */
    public void move(Direction direction) {
        if (direction == Direction.UP) {
            moveUp();
        } else if (direction == Direction.DOWN) {
            moveDown();
        } else if (direction == Direction.LEFT) {
            moveLeft();
        } else if (direction == Direction.RIGHT) {
            moveRight();
        }
    }
    
    /** 
     * moves entity one position up
     */
    public void moveUp() {
        Position newPos = getPosition().translateBy(Direction.UP);
        if (checkValidMove(newPos, Direction.UP)) {
            setPosition(newPos);
        }
    }

    /** 
     * moves entity one position down
     */
    public void moveDown() {
        Position newPos = getPosition().translateBy(Direction.DOWN);
        if (checkValidMove(newPos, Direction.DOWN)) {
            setPosition(newPos);
        }

    }

    /** 
     * moves entity one position left
     */
    public void moveLeft() {
        Position newPos = getPosition().translateBy(Direction.LEFT);
        if (checkValidMove(newPos, Direction.LEFT)) {
            setPosition(newPos);
        }
    }

    /** 
     * moves entity one position right
     */
    public void moveRight() {
        Position newPos = getPosition().translateBy(Direction.RIGHT);
        if (checkValidMove(newPos, Direction.RIGHT)) {
            setPosition(newPos);
        }
    }
        
    /** 
     * returns true if a move to the given position is valid, false if else
     * @param pos
     * @param dir
     * @return boolean
     */
    public boolean checkValidMove(Position pos, Direction dir) {
        // check for obstructions
        for (Entity e : getDungeon().getEntities(pos)) {
            // assume can't walk on top of spawner
            if (e instanceof Wall || e instanceof ZombieToastSpawner || e instanceof Door) {
                return false;
            }
            if (e instanceof Boulder) {
                Position newPos = pos.translateBy(dir);
                for (Entity e1 : getDungeon().getEntities(newPos)) {
                    if (e1 instanceof Wall || e1 instanceof Boulder || e1 instanceof ZombieToastSpawner || e1 instanceof Door) {
                        return false;
                    }
                }   
            }
        }
        return true;
    }

    // basic getters and setters

    /**
     * @return int return the health
     */
    public int getHealth() {
        return health;
    }

    /**
     * @param health the health to set
     */
    public void setHealth(int health) {
        this.health = health;
    }

    /**
     * @return int return the attackDamage
     */
    public int getAttackDamage() {
        return attackDamage;
    }

    /**
     * @param attackDamage the attackDamage to set
     */
    public void setAttackDamage(int attackDamage) {
        this.attackDamage = attackDamage;
    }
}


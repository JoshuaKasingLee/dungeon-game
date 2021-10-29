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

    // other functions

    
    // override in character to pick items and fight enemies
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
    
    public void moveUp() {
        Position newPos = new Position(getPosition().getX(), getPosition().getY() + 1);
        if (checkValidMove(newPos, Direction.UP)) {
            setPosition(newPos);
        }
    }

    public void moveDown() {
        Position newPos = new Position(getPosition().getX(), getPosition().getY() - 1);
        if (checkValidMove(newPos, Direction.DOWN)) {
            setPosition(newPos);
        }

    }

    public void moveLeft() {
        Position newPos = new Position(getPosition().getX() - 1, getPosition().getY());
        if (checkValidMove(newPos, Direction.LEFT)) {
            setPosition(newPos);
        }
    }

    public void moveRight() {
        Position newPos = new Position(getPosition().getX() + 1, getPosition().getY());
        if (checkValidMove(newPos, Direction.RIGHT)) {
            setPosition(newPos);
        }
    }
        
    // need to override for spider
    // assumes zombies + mercenaries can also push boulders, etc. -> everything a player can do except key/door
    public boolean checkValidMove(Position pos, Direction dir) {

        // check for obstructions
        for (Entity e : getDungeon().getEntities(pos)) {
            // assume can't walk on top of spawner
            if (e instanceof Wall || e instanceof ZombieToastSpawner || e instanceof Door) {
                return false;
            }
            // assume boulders never exist on the edge of the dungeon (i.e. there is always a wall border)
            // assume boulder can be pushed onto items/other moving entities
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


}


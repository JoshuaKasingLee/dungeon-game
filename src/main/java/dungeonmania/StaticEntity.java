package dungeonmania;

import dungeonmania.util.Position;
import dungeonmania.util.Direction;

public abstract class StaticEntity extends Entity {
    private double movementFactor;
    
    public StaticEntity(Position position, Dungeon dungeon) {
        super(position, dungeon);
        this.movementFactor = setMovementFactor();
        dungeon.addEntity(this);
    }

    /**
     * UPDATES to be overidden by some classes
     * @param direction
     */
    public abstract void update(Direction direction);

    
    /** 
     * gets player position
     * @return Position
     */
    public Position getPlayerPosition() {
        for (Entity entity : this.getEntities()) {
            if (entity instanceof Player) {
                return entity.getPosition();
            }
        }
        // player does not exist
        return null;
    }
    
    /** 
     * Returns the movement factor of the tile
     * @return double
     */
    public double setMovementFactor() {
        return 1;
    }

    /** 
     * gets the movement factor
     * @return double
     */
    public double getMovementFactor() {
        return movementFactor;
    }
}

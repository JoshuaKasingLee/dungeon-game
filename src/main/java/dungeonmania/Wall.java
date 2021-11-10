package dungeonmania;

import dungeonmania.util.Position;
import dungeonmania.util.Direction;

public class Wall extends StaticEntity {
    public Wall(Position position, Dungeon dungeon) {
        super(position, dungeon);
    }

    /** 
     * @return String
     */
    @Override
    public String setType() {
        return "wall";
    }
    
    /** 
     * update wall status for one tick - does nothing
     * @param direction
     */
    @Override
    public void update(Direction direction) {
        return;
    }


    /** 
     * Sets the movement factor of the entity
     * @return double
     */
    @Override
    public double setMovementFactor() {
        return -1;
    }
    
}

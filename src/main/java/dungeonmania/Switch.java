package dungeonmania;

import dungeonmania.util.Position;
import dungeonmania.util.Direction;

public class Switch extends StaticEntity {    
    public Switch(Position position, Dungeon dungeon) {
        super(position, dungeon);
    }
    
    /** 
     * update switch status for one tick - does nothing
     * @param direction
     */
    @Override
    public void update(Direction direction) {
        return;
    }
    
    /** 
     * @return boolean
     */
    // Refactor into lambda function
    public boolean hasBoulder() {
        for (Entity entity : this.getEntities()) {
            if (entity instanceof Boulder) {
                if (this.getPosition().equals(entity.getPosition())) {
                    return true;
                }
            }
        }
        
        return false;
    }

    /** 
     * @return String
     */
    @Override
    public String setType() {
        return "switch";
    }
}

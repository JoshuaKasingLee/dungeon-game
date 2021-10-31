package dungeonmania;

import dungeonmania.util.Position;
import dungeonmania.util.Direction;

public class Door extends StaticEntity {
    private boolean locked = true;
    private int key;
    
    public Door(Position position, Dungeon dungeon, int key) {
        super(position, dungeon);
        this.key = key;
    }
    
    /** 
     * update door status for one tick
     * @param direction
     */
    @Override
    public void update(Direction direction) {
        if (this.getPosition().equals(this.getPlayerPosition())) {
            this.locked = false;
        }
    }

    // basic getters and setters

    /** 
     * @return String
     */
    @Override
    public String setType() {
        return "door";
    }
    
    /** 
     * @return boolean
     */
    public boolean isLocked() {
        return locked;
    }
    
    /** 
     * @return int
     */
    public int getKey() {
        return this.key;
    }
}

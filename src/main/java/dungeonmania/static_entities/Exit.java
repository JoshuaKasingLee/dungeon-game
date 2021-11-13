package dungeonmania.static_entities;

import dungeonmania.util.Position;
import dungeonmania.Dungeon;
import dungeonmania.util.Direction;

public class Exit extends StaticEntity {
    public Exit(Position position, Dungeon dungeon) {
        super(position, dungeon);
    }

    /** 
     * @return String
     */
    @Override
    public String setType() {
        return "exit";
    }

    
    /** 
     * update exit status for one tick
     * @param direction
     */
    @Override
    public void update(Direction direction) {
        return;
    }

    /** 
     * returns true if player is in exit, false if else
     * @return boolean
     */
    public boolean hasPlayer() {
        if (this.getPosition().equals(this.getPlayerPosition())) {
            return true;
        } else {
            return false;
        }
        
    }
}

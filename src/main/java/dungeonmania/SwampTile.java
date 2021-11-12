package dungeonmania;

import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class SwampTile extends StaticEntity {
    public SwampTile(Position position, Dungeon dungeon, double movementFactor) {
        super(position, dungeon);
        setMovementFactor(movementFactor);
    }

    /**
     * Updates the static entity tile
     * @param direction
     */
    public void update(Direction direction) {
        return;
    }

    /** 
     * @return String
     */
    @Override
    public String setType() {
        return "swamp_tile";
    }

}

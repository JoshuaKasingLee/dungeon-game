package dungeonmania;

import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class SwampTile extends StaticEntity {
    // Counter that counts the number of ticks that the player is on the square
    // Update function increases the counter, and update function resets counter if does not have player
    // Can boulders go through swamps?
    // TODO: Check whether player is called first or static entity
    // TODO: Add a movement_factor and remove it from Gamemode

    public SwampTile(Position position, Dungeon dungeon, double movementFactor) {
        super(position, dungeon);
        setMovementFactor(movementFactor);
    }

    /**
     * TODO: Update Javadoc and Function
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

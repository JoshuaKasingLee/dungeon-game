package dungeonmania;

import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class SwampTile extends StaticEntity {
    // Counter that counts the number of ticks that the player is on the square
    // Update function increases the counter, and update function resets counter if does not have player
    // Can boulders go through swamps?
    // TODO: Check whether player is called first or static entity
    int playerHeldCounter = 1;

    public SwampTile(Position position, Dungeon dungeon) {
        super(position, dungeon);
        playerHeldCounter = 1;
    }

    /**
     * TODO: Update Javadoc and Function
     * @param direction
     */
    public void update(Direction direction) {
        if (getPosition().equals(getPlayerPosition())) {
            updatePlayerSlowed();
            playerHeldCounter++;
        } else if (!getPosition().equals(getPlayerPosition())) {
            updatePlayerSlowed();
            playerHeldCounter = 1;
        }
    }

    /** 
     * Sets the movement factor of the entity
     * @return double
     */
    @Override
    public double setMovementFactor() {
        return getGamemode().getSwampMovement();
    }

    /** 
     * @return String
     */
    @Override
    public String setType() {
        return "swamp_tile";
    }

    /** 
     * Updates the slowness state of the player
     */
    public void updatePlayerSlowed() {
        if (playerHeldCounter < getMovementFactor() && getPosition().equals(getPlayerPosition())) {
            getDungeon().getPlayer().setSlowed(true);
        } else {
            getDungeon().getPlayer().setSlowed(false);
        }
    }
}

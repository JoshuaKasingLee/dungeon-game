package dungeonmania;

import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class SwampTile extends StaticEntity {
    // Counter that counts the number of ticks that the player is on the square
    // Update function increases the counter, and update function resets counter if does not have player
    // Can boulders go through swamps?
    // TODO: Check whether player is called first or static entity
    // TODO: Add a movement_factor and remove it from Gamemode

    public SwampTile(Position position, Dungeon dungeon) {
        super(position, dungeon);
    }

    /**
     * TODO: Update Javadoc and Function
     * @param direction
     */
    public void update(Direction direction) {
        for (Entity entity : getDungeon().getEntities()) {
            if (entity instanceof MovingEntity && getPosition().equals(entity.getPosition())) {
                updateEntitySlowed(entity);
            }
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
    public void updateEntitySlowed(Entity entity) {
        if (((MovingEntity)entity).getSlowed() < getMovementFactor() && getPosition().equals(getPlayerPosition())) {
            ((MovingEntity)entity).incrementSlowed();
        } else {
            ((MovingEntity)entity).setSlowed(1);
        }
    }
}

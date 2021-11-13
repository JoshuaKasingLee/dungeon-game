package dungeonmania.static_entities;

import dungeonmania.util.Position;
import dungeonmania.Dungeon;
import dungeonmania.Entity;
import dungeonmania.util.Direction;


public class Portal extends StaticEntity {
    private Portal pairedPortal = null;
    private String colour;

    public Portal(Position position, Dungeon dungeon, String colour) {
        super(position, dungeon);
        this.colour = colour;
    }

    /** 
     * update portal status for one tick
     * @param direction
     */
    @Override
    public void update(Direction direction) {
        pairedPortal = findPairedPortal();
        if (this.getPosition().equals(this.getPlayerPosition()) && getPlayerTeleported() == false) {
            teleportEntity(this.getDungeon().getPlayer(), direction);
        }
    }
    
    /** 
     * teleport given entity from one portal to its corresponding portal
     * @param entity
     * @param direction
     */
    public void teleportEntity(Entity entity, Direction direction) {
        // for Entity in entities, if entity != this && == portal, teleport moveDirection + entityPosition
        Position currPos = pairedPortal.getPosition();
        Position dir = direction.getOffset();
        Position newPos = new Position((currPos.getX() + dir.getX()), (currPos.getY() + dir.getY()));

        // Same check as Player
        for (Entity e : getDungeon().getEntities(newPos)) {
            if ((e instanceof Wall) ||
                (e instanceof Boulder) ||
                (e instanceof Door) ||
                (e instanceof ZombieToastSpawner)) {
                // ((Player) )
                entity.setPosition(currPos);
                setPlayerTeleported(true);
                return;
            }
        }
        entity.setPosition(newPos);
        setPlayerTeleported(true);
    }

    
    /** 
     * return portal pair
     * @return Portal
     */
    public Portal findPairedPortal() {
        for (Entity entity : this.getEntities()) {
            if (entity instanceof Portal && !entity.equals(this) && ((Portal)entity).getPortalColour().equals(getPortalColour())) {
                return (Portal)entity;
            }
        }
        // paired portal does not exist
        return null;
    }

    // basic getters and setters

    /** 
     * @return String
     */
    @Override
    public String setType() {
        return "portal";
    }
    
    /** 
     * @return String
     */
    public String getPortalColour() {
        return this.colour;
    }
    
    /** 
     * @return boolean
     */
    public boolean getPlayerTeleported() {
        return getDungeon().getPlayer().getTeleported();
    }
    
    /** 
     * @param teleported
     */
    public void setPlayerTeleported(boolean teleported) {
        getDungeon().getPlayer().setTeleported(teleported);
    }
    
}

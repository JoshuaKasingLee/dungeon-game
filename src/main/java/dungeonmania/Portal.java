package dungeonmania;

import dungeonmania.util.Position;
import dungeonmania.util.Direction;

public class Portal extends StaticEntity {
    Portal pairedPortal;
    private String colour;

    public Portal(Position position, String id, String colour) {
        super(position, id);
        pairedPortal = findPairedPortal(); // to change
        this.colour = colour;
    }

    @Override
    public String setType() {
        return "portal";
    }

    @Override
    public void update(Direction direction) {
        if (this.getPosition().equals(this.getPlayerPosition())) {
            teleportEntity(this.getDungeon().getPlayer(), direction);
        }
    }

    public void teleportEntity(Entity entity, Direction direction) {
        // for Entity in entities, if entity != this && == portal, teleport moveDirection + entityPosition
        Position currPos = this.getPosition();
        Position dir = direction.getOffset();
        Position newPos = new Position((currPos.getX() + dir.getX()), (currPos.getY() + dir.getY()));

        // Same check as Player
        if (getDungeon().getEntity(newPos) instanceof Wall || 
            getDungeon().getEntity(newPos) instanceof Door ||
            getDungeon().getEntity(newPos) instanceof ZombieToastSpawner) {
            getDungeon().getPlayer().setPosition(currPos);
        } else {
            getDungeon().getPlayer().setPosition(newPos);
        }
    }

    public Portal findPairedPortal() {
        for (Entity entity : this.getEntities()) {
            if (entity instanceof Portal && !entity.equals(this)) {
                return (Portal)entity;
            }
        }
        // paired portal does not exist
        return null;
    }
}

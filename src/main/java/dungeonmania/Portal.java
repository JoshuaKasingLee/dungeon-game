package dungeonmania;

import dungeonmania.util.Position;
import dungeonmania.util.Direction;

import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;

public class Portal extends StaticEntity {
    private Portal pairedPortal = null;
    private String colour;

    public Portal(Position position, Dungeon dungeon, String colour) {
        super(position, dungeon);
        // pairedPortal = findPairedPortal();
        this.colour = colour;
    }

    @Override
    public String setType() {
        return "Portal";
    }

    @Override
    public void update(Direction direction) {
        pairedPortal = findPairedPortal();
        if (this.getPosition().equals(this.getPlayerPosition()) && getPlayerTeleported() == false) {
            teleportEntity(this.getDungeon().getPlayer(), direction);
        }
    }

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

    public Portal findPairedPortal() {
        for (Entity entity : this.getEntities()) {
            if (entity instanceof Portal && !entity.equals(this) && ((Portal)entity).getPortalColour().equals(getPortalColour())) {
                return (Portal)entity;
            }
        }
        // paired portal does not exist
        return null;
    }

    public String getPortalColour() {
        return this.colour;
    }

    public boolean getPlayerTeleported() {
        return getDungeon().getPlayer().getTeleported();
    }

    public void setPlayerTeleported(boolean teleported) {
        getDungeon().getPlayer().setTeleported(teleported);
    }


    public static void main(String[] args) {

        // test whether a portal teleports a player to corresponding portal
        // Create a controller
        DungeonManiaController controller = new DungeonManiaController();

        // Create a new game
        DungeonResponse dungeonInfo = controller.newGame("portals-2", "Peaceful");

        // Get player entity
        EntityResponse player = dungeonInfo.getEntities().stream().filter(n -> n.getType().equals("Player")).findFirst().orElse(null);
        System.out.println("Expected: " + new Position(0, 1) + "Actual: " + player.getPosition());

        // Get player entity
        // EntityResponse portal = dungeonInfo.getEntities().stream().filter(n -> n.getType().equals("Portal")).findFirst().orElse(null);
        // System.out.println("Portal - Expected: " + new Position(1, 1) + "Actual: " + portal.getPosition());

        // dungeonInfo.getEntities().stream().filter(n -> n.getType().equals("Portal")).forEach(s -> System.out.println(s.getPosition()));

        // Move character into portal (portal is obstructed on the RHS)
        dungeonInfo = controller.tick(null, Direction.RIGHT);
        player = dungeonInfo.getEntities().stream().filter(n -> n.getType().equals("Player")).findFirst().orElse(null);
        System.out.println("Expected: " + new Position(4, 1) + "Actual: " + player.getPosition());

        // Move player down
        dungeonInfo = controller.tick(null, Direction.DOWN);
        player = dungeonInfo.getEntities().stream().filter(n -> n.getType().equals("Player")).findFirst().orElse(null);
        System.out.println("Expected: " + new Position(4, 2) + "Actual: " + player.getPosition());

        // Move player up into portal
        dungeonInfo = controller.tick(null, Direction.UP);
        player = dungeonInfo.getEntities().stream().filter(n -> n.getType().equals("Player")).findFirst().orElse(null);
        System.out.println("Expected: " + new Position(1, 0) + "Actual: " + player.getPosition());
    }
    
}

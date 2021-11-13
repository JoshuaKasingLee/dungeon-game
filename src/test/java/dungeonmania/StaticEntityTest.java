package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;

import dungeonmania.util.Position;
import dungeonmania.util.Direction;

import org.junit.jupiter.api.Test;


public class StaticEntityTest {
    @Test
    public void testWall() {
        DungeonManiaController controller = new DungeonManiaController();

        DungeonResponse dungeonInfo = controller.newGame("walls", "Standard");

        EntityResponse player = null;
        player = dungeonInfo.getEntities().stream().filter(n -> n.getType().equals("player")).findFirst().orElse(null);

        assertEquals(new Position(1, 1), player.getPosition());

        dungeonInfo = controller.tick(null, Direction.UP);
        player = dungeonInfo.getEntities().stream().filter(n -> n.getType().equals("player")).findFirst().orElse(null);
        assertEquals(new Position(1, 1), player.getPosition());
        dungeonInfo = controller.tick(null, Direction.LEFT);
        player = dungeonInfo.getEntities().stream().filter(n -> n.getType().equals("player")).findFirst().orElse(null);
        assertEquals(new Position(1, 1), player.getPosition());
        dungeonInfo = controller.tick(null, Direction.DOWN);
        player = dungeonInfo.getEntities().stream().filter(n -> n.getType().equals("player")).findFirst().orElse(null);
        assertEquals(new Position(1, 2), player.getPosition());
        dungeonInfo = controller.tick(null, Direction.LEFT);
        player = dungeonInfo.getEntities().stream().filter(n -> n.getType().equals("player")).findFirst().orElse(null);
        assertEquals(new Position(1, 2), player.getPosition());
        dungeonInfo = controller.tick(null, Direction.RIGHT);
        player = dungeonInfo.getEntities().stream().filter(n -> n.getType().equals("player")).findFirst().orElse(null);
        assertEquals(new Position(2, 2), player.getPosition());
        dungeonInfo = controller.tick(null, Direction.RIGHT);
        player = dungeonInfo.getEntities().stream().filter(n -> n.getType().equals("player")).findFirst().orElse(null);
        assertEquals(new Position(2, 2), player.getPosition());
    }

    @Test
    public void testSpawnerCreatesEntityStandard() {
        DungeonManiaController controller = new DungeonManiaController();

        DungeonResponse dungeonInfo = controller.newGame("spawner", "Standard");

        for (int i = 0; i < 19; i++) {
            dungeonInfo = controller.tick(null, Direction.RIGHT);
            assertEquals(dungeonInfo.getEntities().stream().anyMatch(n -> n.getType().equals("zombie_toast")), false);
        }
        dungeonInfo = controller.tick(null, Direction.RIGHT);
        assertTrue(dungeonInfo.getEntities().stream().anyMatch(n -> n.getType().equals("zombie_toast")));
    }

    @Test
    public void testSpawnerCreatesEntityPeaceful() {
        DungeonManiaController controller = new DungeonManiaController();

        DungeonResponse dungeonInfo = controller.newGame("spawner", "Peaceful");

        for (int i = 0; i < 199; i++) {
            dungeonInfo = controller.tick(null, Direction.NONE);
            assertEquals(dungeonInfo.getEntities().stream().anyMatch(n -> n.getType().equals("zombie_toast")), false);
        }
    }

    @Test
    public void testSpawnerCreatesEntityHard() {
        DungeonManiaController controller = new DungeonManiaController();

        DungeonResponse dungeonInfo = controller.newGame("spawner", "Hard");

        for (int i = 0; i < 14; i++) {
            dungeonInfo = controller.tick(null, Direction.RIGHT);
            assertEquals(dungeonInfo.getEntities().stream().anyMatch(n -> n.getType().equals("zombie_toast")), false);
        }
        dungeonInfo = controller.tick(null, Direction.RIGHT);
        assertTrue(dungeonInfo.getEntities().stream().anyMatch(n -> n.getType().equals("zombie_toast")));
    }

    @Test
    public void testZombieSpawnerIsDestoyed() {
        DungeonManiaController controller = new DungeonManiaController();

        DungeonResponse dungeonInfo = controller.newGame("spawner", "Peaceful");

        assertTrue(dungeonInfo.getEntities().stream().anyMatch(n -> n.getType().equals("zombie_toast_spawner")));

        EntityResponse player = null;
        player = dungeonInfo.getEntities().stream().filter(n -> n.getType().equals("player")).findFirst().orElse(null);

        dungeonInfo = controller.tick(null, Direction.DOWN);
        player = dungeonInfo.getEntities().stream().filter(n -> n.getType().equals("player")).findFirst().orElse(null);
        assertEquals(new Position(1, 2), player.getPosition());
        dungeonInfo = controller.tick(null, Direction.DOWN);
        player = dungeonInfo.getEntities().stream().filter(n -> n.getType().equals("player")).findFirst().orElse(null);
        assertEquals(new Position(1, 3), player.getPosition());
        dungeonInfo = controller.tick(null, Direction.RIGHT);
        player = dungeonInfo.getEntities().stream().filter(n -> n.getType().equals("player")).findFirst().orElse(null);
        assertEquals(new Position(2, 3), player.getPosition());
        
        assertTrue(dungeonInfo.getEntities().stream().anyMatch(n -> n.getType().equals("zombie_toast_spawner")));
    }

    @Test
    public void testZombieSpawnerIsNotDestoyed() {
        DungeonManiaController controller = new DungeonManiaController();

        DungeonResponse dungeonInfo = controller.newGame("spawner", "Peaceful");

        assertTrue(dungeonInfo.getEntities().stream().anyMatch(n -> n.getType().equals("zombie_toast_spawner")));

        EntityResponse player = dungeonInfo.getEntities().stream().filter(n -> n.getType().equals("player")).findFirst().orElse(null);

        dungeonInfo = controller.tick(null, Direction.RIGHT);
        player = dungeonInfo.getEntities().stream().filter(n -> n.getType().equals("player")).findFirst().orElse(null);
        assertEquals(new Position(2, 1), player.getPosition());
        dungeonInfo = controller.tick(null, Direction.RIGHT);
        player = dungeonInfo.getEntities().stream().filter(n -> n.getType().equals("player")).findFirst().orElse(null);
        assertEquals(new Position(3, 1), player.getPosition());
        dungeonInfo = controller.tick(null, Direction.DOWN);
        player = dungeonInfo.getEntities().stream().filter(n -> n.getType().equals("player")).findFirst().orElse(null);
        assertEquals(new Position(3, 2), player.getPosition());
        
        assertTrue(dungeonInfo.getEntities().stream().anyMatch(n -> n.getType().equals("zombie_toast_spawner")));
    }

    @Test
    public void testDoorOpens() {
        DungeonManiaController controller = new DungeonManiaController();

        DungeonResponse dungeonInfo = controller.newGame("doors", "Peaceful");

        EntityResponse player = dungeonInfo.getEntities().stream().filter(n -> n.getType().equals("player")).findFirst().orElse(null);

        dungeonInfo = controller.tick(null, Direction.RIGHT);
        player = dungeonInfo.getEntities().stream().filter(n -> n.getType().equals("player")).findFirst().orElse(null);
        assertEquals(new Position(2, 2), player.getPosition());
        dungeonInfo = controller.tick(null, Direction.RIGHT);
        player = dungeonInfo.getEntities().stream().filter(n -> n.getType().equals("player")).findFirst().orElse(null);
        assertEquals(new Position(2, 2), player.getPosition());
        dungeonInfo = controller.tick(null, Direction.UP);
        player = dungeonInfo.getEntities().stream().filter(n -> n.getType().equals("player")).findFirst().orElse(null);
        assertEquals(new Position(2, 1), player.getPosition());
        dungeonInfo = controller.tick(null, Direction.DOWN);
        player = dungeonInfo.getEntities().stream().filter(n -> n.getType().equals("player")).findFirst().orElse(null);
        assertEquals(new Position(2, 2), player.getPosition());
        dungeonInfo = controller.tick(null, Direction.RIGHT);
        player = dungeonInfo.getEntities().stream().filter(n -> n.getType().equals("player")).findFirst().orElse(null);
        assertEquals(new Position(3, 2), player.getPosition());
    }

    @Test
    public void testDoorIncorrectKey() {
        DungeonManiaController controller = new DungeonManiaController();

        DungeonResponse dungeonInfo = controller.newGame("doors", "Peaceful");

        EntityResponse player = dungeonInfo.getEntities().stream().filter(n -> n.getType().equals("player")).findFirst().orElse(null);

        dungeonInfo = controller.tick(null, Direction.RIGHT);
        player = dungeonInfo.getEntities().stream().filter(n -> n.getType().equals("player")).findFirst().orElse(null);
        assertEquals(new Position(2, 2) ,player.getPosition());
        dungeonInfo = controller.tick(null, Direction.RIGHT);
        player = dungeonInfo.getEntities().stream().filter(n -> n.getType().equals("player")).findFirst().orElse(null);
        assertEquals(new Position(2, 2), player.getPosition());
        dungeonInfo = controller.tick(null, Direction.UP);
        player = dungeonInfo.getEntities().stream().filter(n -> n.getType().equals("player")).findFirst().orElse(null);
        assertEquals(new Position(2, 1), player.getPosition());
        dungeonInfo = controller.tick(null, Direction.DOWN);
        player = dungeonInfo.getEntities().stream().filter(n -> n.getType().equals("player")).findFirst().orElse(null);
        assertEquals(new Position(2, 2), player.getPosition());
        dungeonInfo = controller.tick(null, Direction.DOWN);
        player = dungeonInfo.getEntities().stream().filter(n -> n.getType().equals("player")).findFirst().orElse(null);
        assertEquals(new Position(2, 3), player.getPosition());
        dungeonInfo = controller.tick(null, Direction.DOWN);
        player = dungeonInfo.getEntities().stream().filter(n -> n.getType().equals("player")).findFirst().orElse(null);
        assertEquals(new Position(2, 4), player.getPosition());
        dungeonInfo = controller.tick(null, Direction.RIGHT);
        player = dungeonInfo.getEntities().stream().filter(n -> n.getType().equals("player")).findFirst().orElse(null);
        assertEquals(new Position(2, 4), player.getPosition());
        dungeonInfo = controller.tick(null, Direction.DOWN);
        player = dungeonInfo.getEntities().stream().filter(n -> n.getType().equals("player")).findFirst().orElse(null);
        assertEquals(new Position(2, 5), player.getPosition());
        dungeonInfo = controller.tick(null, Direction.UP);
        player = dungeonInfo.getEntities().stream().filter(n -> n.getType().equals("player")).findFirst().orElse(null);
        assertEquals(new Position(2, 4), player.getPosition());
        dungeonInfo = controller.tick(null, Direction.RIGHT);
        player = dungeonInfo.getEntities().stream().filter(n -> n.getType().equals("player")).findFirst().orElse(null);
        assertEquals(new Position(2, 4), player.getPosition());
    }

    @Test
    public void testPortalTeleportsPlayer() {
        DungeonManiaController controller = new DungeonManiaController();

        DungeonResponse dungeonInfo = controller.newGame("portals-2", "Peaceful");

        EntityResponse player = dungeonInfo.getEntities().stream().filter(n -> n.getType().equals("player")).findFirst().orElse(null);
        assertEquals(new Position(0, 1), player.getPosition());

        EntityResponse portal = dungeonInfo.getEntities().stream().filter(n -> n.getType().equals("portal")).findFirst().orElse(null);
        assertEquals(new Position(1, 1), portal.getPosition());

        dungeonInfo = controller.tick(null, Direction.RIGHT);
        player = dungeonInfo.getEntities().stream().filter(n -> n.getType().equals("player")).findFirst().orElse(null);
        assertEquals(new Position(4, 1), player.getPosition());
        dungeonInfo = controller.tick(null, Direction.DOWN);
        player = dungeonInfo.getEntities().stream().filter(n -> n.getType().equals("player")).findFirst().orElse(null);
        assertEquals(new Position(4, 2), player.getPosition());
        dungeonInfo = controller.tick(null, Direction.UP);
        player = dungeonInfo.getEntities().stream().filter(n -> n.getType().equals("player")).findFirst().orElse(null);
        assertEquals(new Position(1, 0), player.getPosition());
    }

    @Test
    public void testSwitchDoesNotObstruct() {
        DungeonManiaController controller = new DungeonManiaController();

        DungeonResponse dungeonInfo = controller.newGame("boulderAndSwitch", "Peaceful");

        assertTrue(dungeonInfo.getEntities().stream().anyMatch(n -> n.getType().equals("boulder")));

        assertTrue(dungeonInfo.getEntities().stream().anyMatch(n -> n.getType().equals("switch")));

        EntityResponse player = dungeonInfo.getEntities().stream().filter(n -> n.getType().equals("player")).findFirst().orElse(null);

        dungeonInfo = controller.tick(null, Direction.RIGHT);
        player = dungeonInfo.getEntities().stream().filter(n -> n.getType().equals("player")).findFirst().orElse(null);
        assertEquals(new Position(2, 1), player.getPosition());
        dungeonInfo = controller.tick(null, Direction.RIGHT);
        player = dungeonInfo.getEntities().stream().filter(n -> n.getType().equals("player")).findFirst().orElse(null);
        assertEquals(new Position(3, 1), player.getPosition());
    }

    @Test
    public void testSwitchCanTrigger() {
        DungeonManiaController controller = new DungeonManiaController();

        DungeonResponse dungeonInfo = controller.newGame("boulderAndSwitch", "Peaceful");

        assertTrue(dungeonInfo.getEntities().stream().anyMatch(n -> n.getType().equals("boulder")));

        assertTrue(dungeonInfo.getEntities().stream().anyMatch(n -> n.getType().equals("switch")));

        EntityResponse player = dungeonInfo.getEntities().stream().filter(n -> n.getType().equals("player")).findFirst().orElse(null);

        EntityResponse boulder = dungeonInfo.getEntities().stream().filter(n -> n.getType().equals("boulder")).findFirst().orElse(null);
        assertEquals(new Position(2, 2), boulder.getPosition());

        dungeonInfo = controller.tick(null, Direction.DOWN);
        player = dungeonInfo.getEntities().stream().filter(n -> n.getType().equals("player")).findFirst().orElse(null);
        assertEquals(new Position(1, 2), player.getPosition());
        dungeonInfo = controller.tick(null, Direction.DOWN);
        player = dungeonInfo.getEntities().stream().filter(n -> n.getType().equals("player")).findFirst().orElse(null);
        assertEquals(new Position(1, 3), player.getPosition());
        dungeonInfo = controller.tick(null, Direction.RIGHT);
        player = dungeonInfo.getEntities().stream().filter(n -> n.getType().equals("player")).findFirst().orElse(null);
        assertEquals(new Position(2, 3), player.getPosition());
        dungeonInfo = controller.tick(null, Direction.UP);
        player = dungeonInfo.getEntities().stream().filter(n -> n.getType().equals("player")).findFirst().orElse(null);
        boulder = dungeonInfo.getEntities().stream().filter(n -> n.getType().equals("boulder")).findFirst().orElse(null);
        assertEquals(new Position(2, 2), player.getPosition());
        assertEquals(new Position(2, 1), boulder.getPosition());
        dungeonInfo = controller.tick(null, Direction.LEFT);
        player = dungeonInfo.getEntities().stream().filter(n -> n.getType().equals("player")).findFirst().orElse(null);
        boulder = dungeonInfo.getEntities().stream().filter(n -> n.getType().equals("boulder")).findFirst().orElse(null);
        assertEquals(new Position(1, 2), player.getPosition());
        dungeonInfo = controller.tick(null, Direction.UP);
        player = dungeonInfo.getEntities().stream().filter(n -> n.getType().equals("player")).findFirst().orElse(null);
        boulder = dungeonInfo.getEntities().stream().filter(n -> n.getType().equals("boulder")).findFirst().orElse(null);
        assertEquals(new Position(1, 1), player.getPosition());
        dungeonInfo = controller.tick(null, Direction.RIGHT);
        player = dungeonInfo.getEntities().stream().filter(n -> n.getType().equals("player")).findFirst().orElse(null);
        boulder = dungeonInfo.getEntities().stream().filter(n -> n.getType().equals("boulder")).findFirst().orElse(null);
        assertEquals(new Position(2, 1), player.getPosition());
        assertEquals(new Position(3, 1), boulder.getPosition());
    }

    @Test
    public void testBoulderObstructsPlayer() {
        DungeonManiaController controller = new DungeonManiaController();

        DungeonResponse dungeonInfo = controller.newGame("boulderAndSwitch", "Peaceful");

        assertTrue(dungeonInfo.getEntities().stream().anyMatch(n -> n.getType().equals("boulder")));

        assertTrue(dungeonInfo.getEntities().stream().anyMatch(n -> n.getType().equals("switch")));

        EntityResponse player = dungeonInfo.getEntities().stream().filter(n -> n.getType().equals("player")).findFirst().orElse(null);

        dungeonInfo = controller.tick(null, Direction.DOWN);
        player = dungeonInfo.getEntities().stream().filter(n -> n.getType().equals("player")).findFirst().orElse(null);
        assertEquals(new Position(1, 2), player.getPosition());
        dungeonInfo = controller.tick(null, Direction.RIGHT);
        player = dungeonInfo.getEntities().stream().filter(n -> n.getType().equals("player")).findFirst().orElse(null);
        assertEquals(new Position(1, 2), player.getPosition());
    }

    @Test
    public void testExitDoesNotObstructs() {
        DungeonManiaController controller = new DungeonManiaController();

        DungeonResponse dungeonInfo = controller.newGame("exit", "Peaceful");

        EntityResponse player = dungeonInfo.getEntities().stream().filter(n -> n.getType().equals("player")).findFirst().orElse(null);

        dungeonInfo = controller.tick(null, Direction.DOWN);
        player = dungeonInfo.getEntities().stream().filter(n -> n.getType().equals("player")).findFirst().orElse(null);
        assertEquals(new Position(1, 2), player.getPosition());
    }

    @Test
    public void testSwampPeaceful() {
        DungeonManiaController controller = new DungeonManiaController();

        DungeonResponse dungeonInfo = controller.newGame("swampTile", "Peaceful");

        EntityResponse player = dungeonInfo.getEntities().stream().filter(n -> n.getType().equals("player")).findFirst().orElse(null);

        dungeonInfo = controller.tick(null, Direction.DOWN);
        player = dungeonInfo.getEntities().stream().filter(n -> n.getType().equals("player")).findFirst().orElse(null);
        assertEquals(new Position(1, 2), player.getPosition());

        dungeonInfo = controller.tick(null, Direction.DOWN);
        player = dungeonInfo.getEntities().stream().filter(n -> n.getType().equals("player")).findFirst().orElse(null);
        assertEquals(new Position(1, 3), player.getPosition());
    }

    @Test
    public void testSwampStandard() {
        DungeonManiaController controller = new DungeonManiaController();

        DungeonResponse dungeonInfo = controller.newGame("swampTile", "Standard");

        EntityResponse player = dungeonInfo.getEntities().stream().filter(n -> n.getType().equals("player")).findFirst().orElse(null);

        dungeonInfo = controller.tick(null, Direction.DOWN);
        player = dungeonInfo.getEntities().stream().filter(n -> n.getType().equals("player")).findFirst().orElse(null);
        assertEquals(new Position(1, 2), player.getPosition());

        dungeonInfo = controller.tick(null, Direction.DOWN);
        player = dungeonInfo.getEntities().stream().filter(n -> n.getType().equals("player")).findFirst().orElse(null);
        assertEquals(new Position(1, 3), player.getPosition());

        dungeonInfo = controller.tick(null, Direction.RIGHT);
        player = dungeonInfo.getEntities().stream().filter(n -> n.getType().equals("player")).findFirst().orElse(null);
        assertEquals(new Position(2, 3), player.getPosition());
    }

}
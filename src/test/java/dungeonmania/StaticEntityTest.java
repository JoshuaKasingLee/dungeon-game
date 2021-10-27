package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;

import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;

import dungeonmania.util.Position;
import dungeonmania.util.Direction;

import org.junit.jupiter.api.Test;

import java.util.List;

public class StaticEntityTest {
    @Test
    public void testWall() {
        // Create a controller
        DungeonManiaController controller = new DungeonManiaController();

        // Create a new game
        DungeonResponse dungeonInfo = controller.newGame("walls", "standard");

        // Get entities from dungeonInfo
        List<EntityResponse> entities = dungeonInfo.getEntities();

        // Get player entity
        EntityResponse player = entities.stream().filter(n -> n.getType().equals("player")).findFirst().orElse(null);

        // Assert player location is 0,0
        assertEquals(new Position(1, 1), player.getPosition());

        // Move character into wall and position remains the same
        controller.tick(null, Direction.UP);
        // Assert player location is 0,0
        assertEquals(new Position(1, 1), player.getPosition());
        // Move character into wall and position remains the same
        controller.tick(null, Direction.LEFT);
        // Assert player location is 0,0
        assertEquals(new Position(1, 1), player.getPosition());
        // Move character into wall and position remains the same
        controller.tick(null, Direction.DOWN);
        // Assert player location is 0,0
        assertEquals(new Position(1, 2), player.getPosition());
        // Move character into wall and position remains the same
        controller.tick(null, Direction.LEFT);
        // Assert player location is 0,0
        assertEquals(new Position(1, 2), player.getPosition());
        // Move character into wall and position remains the same
        controller.tick(null, Direction.RIGHT);
        // Assert player location is 0,0
        assertEquals(new Position(2, 2), player.getPosition());
        // Move character into wall and position remains the same
        controller.tick(null, Direction.RIGHT);
        // Assert player location is 0,0
        assertEquals(new Position(2, 2), player.getPosition());
    }

    @Test
    public void testSpawnerCreatesEntityStandard() {
        // test whether a zombie spawner creates an entity
        // Create a controller
        DungeonManiaController controller = new DungeonManiaController();

        // Create a new game
        DungeonResponse dungeonInfo = controller.newGame("spawner", "standard");

        // Get entities from dungeonInfo
        List<EntityResponse> entities = dungeonInfo.getEntities();

        // Game ticks
        for (int i = 0; i < 19; i++) {
            controller.tick(null, Direction.NONE);
            assertEquals(entities.stream().anyMatch(n -> n.getType().equals("zombie")), false);
        }
        // On 20th tick, spawn a zombie on the location of the spawner
        controller.tick(null, Direction.NONE);
        assertTrue(entities.stream().anyMatch(n -> n.getType().equals("zombie")));
    }

    @Test
    public void testSpawnerCreatesEntityPeaceful() {
        // test whether a zombie spawner creates an entity
        // Create a controller
        DungeonManiaController controller = new DungeonManiaController();

        // Create a new game
        DungeonResponse dungeonInfo = controller.newGame("spawner", "peaceful");

        // Get entities from dungeonInfo
        List<EntityResponse> entities = dungeonInfo.getEntities();

        // Game ticks
        for (int i = 0; i < 50; i++) {
            controller.tick(null, Direction.NONE);
            assertEquals(entities.stream().anyMatch(n -> n.getType().equals("zombie")), false);
        }
    }

    @Test
    public void testSpawnerCreatesEntityHard() {
        // test whether a zombie spawner creates an entity
        // Create a controller
        DungeonManiaController controller = new DungeonManiaController();

        // Create a new game
        DungeonResponse dungeonInfo = controller.newGame("spawner", "hard");

        // Get entities from dungeonInfo
        List<EntityResponse> entities = dungeonInfo.getEntities();

        // Game ticks
        for (int i = 0; i < 9; i++) {
            controller.tick(null, Direction.NONE);
            assertEquals(entities.stream().anyMatch(n -> n.getType().equals("zombie")), false);
        }
        // On 20th tick, spawn a zombie on the location of the spawner
        controller.tick(null, Direction.NONE);
        assertTrue(entities.stream().anyMatch(n -> n.getType().equals("zombie")));
    }

    @Test
    public void testZombieSpawnerIsDestoyed() {
        // test whether a zombie spawner will break if player is next to spawner with a weapon
        // Create a controller
        DungeonManiaController controller = new DungeonManiaController();

        // Create a new game
        DungeonResponse dungeonInfo = controller.newGame("spawner", "peaceful");

        // Get entities from dungeonInfo
        List<EntityResponse> entities = dungeonInfo.getEntities();

        // Assert spawner exists
        assertTrue(entities.stream().anyMatch(n -> n.getType().equals("zombie_toast_spawner")));

        // Get player entity
        EntityResponse player = entities.stream().filter(n -> n.getType().equals("player")).findFirst().orElse(null);

        // Move character down
        controller.tick(null, Direction.DOWN);
        assertEquals(new Position(1, 2), player.getPosition());
        // Move character down
        controller.tick(null, Direction.DOWN);
        assertEquals(new Position(1, 3), player.getPosition());
        // Move character right
        controller.tick(null, Direction.RIGHT);
        assertEquals(new Position(1, 2), player.getPosition());
        
        // Spawner has been destroyed
        assertFalse(entities.stream().anyMatch(n -> n.getType().equals("zombie_toast_spawner")));
    }

    @Test
    public void testZombieSpawnerIsNotDestoyed() {
        // test whether a zombie spawner will break if player is next to spawner with a weapon
        // Create a controller
        DungeonManiaController controller = new DungeonManiaController();

        // Create a new game
        DungeonResponse dungeonInfo = controller.newGame("spawner", "peaceful");

        // Get entities from dungeonInfo
        List<EntityResponse> entities = dungeonInfo.getEntities();

        // Assert spawner exists
        assertTrue(entities.stream().anyMatch(n -> n.getType().equals("zombie_toast_spawner")));

        // Get player entity
        EntityResponse player = entities.stream().filter(n -> n.getType().equals("player")).findFirst().orElse(null);

        // Move character right
        controller.tick(null, Direction.RIGHT);
        assertEquals(new Position(2, 1), player.getPosition());
        // Move character right
        controller.tick(null, Direction.RIGHT);
        assertEquals(new Position(3, 1), player.getPosition());
        // Move character right
        controller.tick(null, Direction.DOWN);
        assertEquals(new Position(3, 2), player.getPosition());
        
        // Spawner has not been destroyed as player does not have a weapon
        assertTrue(entities.stream().anyMatch(n -> n.getType().equals("zombie_toast_spawner")));
    }

    @Test
    public void testDoorOpens() {
        // test whether closed door obstructs player and unlocks with correct key
        // Create a controller
        DungeonManiaController controller = new DungeonManiaController();

        // Create a new game
        DungeonResponse dungeonInfo = controller.newGame("doors", "peaceful");

        // Get entities from dungeonInfo
        List<EntityResponse> entities = dungeonInfo.getEntities();

        // Get player entity
        EntityResponse player = entities.stream().filter(n -> n.getType().equals("player")).findFirst().orElse(null);

        // Move character right
        controller.tick(null, Direction.RIGHT);
        assertEquals(new Position(2, 2), player.getPosition());
        // Move character right - door is locked
        controller.tick(null, Direction.RIGHT);
        assertEquals(new Position(2, 2), player.getPosition());
        // Move character to get key1
        controller.tick(null, Direction.UP);
        assertEquals(new Position(2, 1), player.getPosition());
        // Move character to door again
        controller.tick(null, Direction.DOWN);
        assertEquals(new Position(2, 2), player.getPosition());
        // Move character into door
        controller.tick(null, Direction.RIGHT);
        assertEquals(new Position(3, 2), player.getPosition());
    }

    @Test
    public void testDoorIncorrectKey() {
        // test whether door opens with wrong key
        // Create a controller
        DungeonManiaController controller = new DungeonManiaController();

        // Create a new game
        DungeonResponse dungeonInfo = controller.newGame("doors", "peaceful");

        // Get entities from dungeonInfo
        List<EntityResponse> entities = dungeonInfo.getEntities();

        // Get player entity
        EntityResponse player = entities.stream().filter(n -> n.getType().equals("player")).findFirst().orElse(null);

        // Move character right
        controller.tick(null, Direction.RIGHT);
        assertEquals(new Position(2, 2) ,player.getPosition());
        // Move character right - door is locked
        controller.tick(null, Direction.RIGHT);
        assertEquals(new Position(2, 2), player.getPosition());
        // Move character to get key1
        controller.tick(null, Direction.UP);
        assertEquals(new Position(2, 1), player.getPosition());
        // Move character to other door
        controller.tick(null, Direction.DOWN);
        assertEquals(new Position(2, 2), player.getPosition());
        // Move character to other door
        controller.tick(null, Direction.DOWN);
        assertEquals(new Position(2, 3), player.getPosition());
        // Move character to other door
        controller.tick(null, Direction.DOWN);
        assertEquals(new Position(2, 4), player.getPosition());
        // Move character into door - door is locked
        controller.tick(null, Direction.RIGHT);
        assertEquals(new Position(2, 4), player.getPosition());
        // Move character to get key2
        controller.tick(null, Direction.DOWN);
        assertEquals(new Position(2, 5), player.getPosition());
        // Move character towards door2
        controller.tick(null, Direction.UP);
        assertEquals(new Position(2, 4), player.getPosition());
        // Move character into door
        controller.tick(null, Direction.RIGHT);
        assertEquals(new Position(3, 4), player.getPosition());
    }

    @Test
    public void testPortalTeleportsPlayer() {
        // test whether a portal teleports a player to corresponding portal
        // Create a controller
        DungeonManiaController controller = new DungeonManiaController();

        // Create a new game
        DungeonResponse dungeonInfo = controller.newGame("portals-2", "peaceful");

        // Get entities from dungeonInfo
        List<EntityResponse> entities = dungeonInfo.getEntities();

        // Get player entity
        EntityResponse player = entities.stream().filter(n -> n.getType().equals("player")).findFirst().orElse(null);

        // Move character into portal (portal is obstructed on the RHS)
        controller.tick(null, Direction.RIGHT);
        assertEquals(new Position(4, 1), player.getPosition());
        // Move player down
        controller.tick(null, Direction.DOWN);
        assertEquals(new Position(4, 2), player.getPosition());
        // Move player up into portal
        controller.tick(null, Direction.UP);
        assertEquals(new Position(1, 0), player.getPosition());
    }

    @Test
    public void testSwitchDoesNotObstruct() {
        // tests whether a floor obstructs a player other entities
        // Create a controller
        DungeonManiaController controller = new DungeonManiaController();

        // Create a new game
        DungeonResponse dungeonInfo = controller.newGame("boulderAndSwitch", "peaceful");

        // Get entities from dungeonInfo
        List<EntityResponse> entities = dungeonInfo.getEntities();

        // Assert boulder exists
        assertTrue(entities.stream().anyMatch(n -> n.getType().equals("boulder")));

        // Assert switch exists
        assertTrue(entities.stream().anyMatch(n -> n.getType().equals("switch")));


        // Get player entity
        EntityResponse player = entities.stream().filter(n -> n.getType().equals("player")).findFirst().orElse(null);

        // Move towards switch
        controller.tick(null, Direction.RIGHT);
        assertEquals(new Position(2, 1), player.getPosition());
        // Move onto switch
        controller.tick(null, Direction.RIGHT);
        assertEquals(new Position(3, 1), player.getPosition());
    }

    @Test
    public void testSwitchCanTrigger() {
        // tests whether switch is triggered if boulder is on it
        // Create a controller
        DungeonManiaController controller = new DungeonManiaController();

        // Create a new game
        DungeonResponse dungeonInfo = controller.newGame("boulderAndSwitch", "peaceful");

        // Get entities from dungeonInfo
        List<EntityResponse> entities = dungeonInfo.getEntities();

        // Assert boulder exists
        assertTrue(entities.stream().anyMatch(n -> n.getType().equals("boulder")));

        // Assert switch exists
        assertTrue(entities.stream().anyMatch(n -> n.getType().equals("switch")));

        // Get player entity
        EntityResponse player = entities.stream().filter(n -> n.getType().equals("player")).findFirst().orElse(null);

        // Get boulder entity
        EntityResponse boulder = entities.stream().filter(n -> n.getType().equals("boulder")).findFirst().orElse(null);
        // Check boulder position
        assertEquals(new Position(2, 2), boulder.getPosition());

        // Move behind boulder
        controller.tick(null, Direction.DOWN);
        assertEquals(new Position(1, 2), player.getPosition());
        // Move behind boulder
        controller.tick(null, Direction.DOWN);
        assertEquals(new Position(1, 3), player.getPosition());
        // Move behind boulder
        controller.tick(null, Direction.RIGHT);
        assertEquals(new Position(2, 3), player.getPosition());
        // Push boulder
        controller.tick(null, Direction.RIGHT);
        assertEquals(new Position(2, 2), player.getPosition());
        assertEquals(new Position(2, 1), boulder.getPosition());
        // Push boulder onto switch
        controller.tick(null, Direction.LEFT);
        assertEquals(new Position(1, 2), player.getPosition());
        controller.tick(null, Direction.UP);
        assertEquals(new Position(1, 1), player.getPosition());
        controller.tick(null, Direction.RIGHT);
        assertEquals(new Position(2, 1), player.getPosition());
        assertEquals(new Position(3, 1), boulder.getPosition());
    }

    @Test
    public void testBoulderObstructsPlayer() {
        // tests whether a boulder being pushed into a wall obstructs a player
        // Create a controller
        DungeonManiaController controller = new DungeonManiaController();

        // Create a new game
        DungeonResponse dungeonInfo = controller.newGame("boulderAndSwitch", "peaceful");

        // Get entities from dungeonInfo
        List<EntityResponse> entities = dungeonInfo.getEntities();

        // Assert boulder exists
        assertTrue(entities.stream().anyMatch(n -> n.getType().equals("boulder")));

        // Assert switch exists
        assertTrue(entities.stream().anyMatch(n -> n.getType().equals("switch")));

        // Get player entity
        EntityResponse player = entities.stream().filter(n -> n.getType().equals("player")).findFirst().orElse(null);

        // Move behind boulder
        controller.tick(null, Direction.DOWN);
        assertEquals(new Position(1, 2), player.getPosition());
        // Try push two boulders and stay in place
        controller.tick(null, Direction.RIGHT);
        assertEquals(new Position(1, 2), player.getPosition());
    }

    @Test
    public void testExitObstructs() {
        // test whether the exit obstructs a player
        // Create a controller
        DungeonManiaController controller = new DungeonManiaController();

        // Create a new game
        DungeonResponse dungeonInfo = controller.newGame("exit", "peaceful");

        // Get entities from dungeonInfo
        List<EntityResponse> entities = dungeonInfo.getEntities();

        // Get player entity
        EntityResponse player = entities.stream().filter(n -> n.getType().equals("player")).findFirst().orElse(null);

        // Move hop down onto exit
        controller.tick(null, Direction.DOWN);
        assertEquals(new Position(1, 2), player.getPosition());
    }
}
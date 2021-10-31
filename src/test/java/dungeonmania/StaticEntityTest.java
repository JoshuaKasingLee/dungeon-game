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
        DungeonResponse dungeonInfo = controller.newGame("walls", "Standard");

        // Get player entity
        EntityResponse player = null;
        player = dungeonInfo.getEntities().stream().filter(n -> n.getType().equals("Player")).findFirst().orElse(null);

        // Assert player location is 1,1
        assertEquals(new Position(1, 1), player.getPosition());

        // Move character into wall and position remains the same
        dungeonInfo = controller.tick(null, Direction.UP);
        // Assert player location is 1,1
        player = dungeonInfo.getEntities().stream().filter(n -> n.getType().equals("Player")).findFirst().orElse(null);
        assertEquals(new Position(1, 1), player.getPosition());
        // Move character into wall and position remains the same
        dungeonInfo = controller.tick(null, Direction.LEFT);
        // Assert player location is 1,1
        player = dungeonInfo.getEntities().stream().filter(n -> n.getType().equals("Player")).findFirst().orElse(null);
        assertEquals(new Position(1, 1), player.getPosition());
        // Move character into wall and position remains the same
        dungeonInfo = controller.tick(null, Direction.DOWN);
        // Assert player location is 0,0
        player = dungeonInfo.getEntities().stream().filter(n -> n.getType().equals("Player")).findFirst().orElse(null);
        assertEquals(new Position(1, 2), player.getPosition());
        // Move character into wall and position remains the same
        dungeonInfo = controller.tick(null, Direction.LEFT);
        // Assert player location is 0,0
        player = dungeonInfo.getEntities().stream().filter(n -> n.getType().equals("Player")).findFirst().orElse(null);
        assertEquals(new Position(1, 2), player.getPosition());
        // Move character into wall and position remains the same
        dungeonInfo = controller.tick(null, Direction.RIGHT);
        // Assert player location is 0,0
        player = dungeonInfo.getEntities().stream().filter(n -> n.getType().equals("Player")).findFirst().orElse(null);
        assertEquals(new Position(2, 2), player.getPosition());
        // Move character into wall and position remains the same
        dungeonInfo = controller.tick(null, Direction.RIGHT);
        // Assert player location is 0,0
        player = dungeonInfo.getEntities().stream().filter(n -> n.getType().equals("Player")).findFirst().orElse(null);
        assertEquals(new Position(2, 2), player.getPosition());
    }

    @Test
    public void testSpawnerCreatesEntityStandard() {
        // test whether a zombie spawner creates an entity
        // Create a controller
        DungeonManiaController controller = new DungeonManiaController();

        // Create a new game
        DungeonResponse dungeonInfo = controller.newGame("spawner", "Standard");

        // // Get entities from dungeonInfo
        // List<EntityResponse> entities = dungeonInfo.getEntities();

        // Game ticks
        for (int i = 0; i < 19; i++) {
            dungeonInfo = controller.tick(null, Direction.NONE);
            assertEquals(dungeonInfo.getEntities().stream().anyMatch(n -> n.getType().equals("ZombieToast")), false);
        }
        // On 20th tick, spawn a zombie on the location of the spawner
        dungeonInfo = controller.tick(null, Direction.NONE);
        assertTrue(dungeonInfo.getEntities().stream().anyMatch(n -> n.getType().equals("ZombieToast")));
    }

    @Test
    public void testSpawnerCreatesEntityPeaceful() {
        // test whether a zombie spawner creates an entity
        // Create a controller
        DungeonManiaController controller = new DungeonManiaController();

        // Create a new game
        DungeonResponse dungeonInfo = controller.newGame("spawner", "Peaceful");

        // Game ticks
        for (int i = 0; i < 199; i++) {
            dungeonInfo = controller.tick(null, Direction.NONE);
            assertEquals(dungeonInfo.getEntities().stream().anyMatch(n -> n.getType().equals("ZombieToast")), false);
        }
    }

    @Test
    public void testSpawnerCreatesEntityHard() {
        // test whether a zombie spawner creates an entity
        // Create a controller
        DungeonManiaController controller = new DungeonManiaController();

        // Create a new game
        DungeonResponse dungeonInfo = controller.newGame("spawner", "Hard");

        // Game ticks
        for (int i = 0; i < 14; i++) {
            dungeonInfo = controller.tick(null, Direction.NONE);
            assertEquals(dungeonInfo.getEntities().stream().anyMatch(n -> n.getType().equals("ZombieToast")), false);
        }
        // On 20th tick, spawn a zombie on the location of the spawner
        dungeonInfo = controller.tick(null, Direction.NONE);
        assertTrue(dungeonInfo.getEntities().stream().anyMatch(n -> n.getType().equals("ZombieToast")));
    }

    @Test
    public void testZombieSpawnerIsDestoyed() {
        // test whether a zombie spawner will break if player is next to spawner with a weapon
        // Create a controller
        DungeonManiaController controller = new DungeonManiaController();

        // Create a new game
        DungeonResponse dungeonInfo = controller.newGame("spawner", "Peaceful");

        // Assert spawner exists
        assertTrue(dungeonInfo.getEntities().stream().anyMatch(n -> n.getType().equals("ZombieToastSpawner")));

        // Get player entity
        EntityResponse player = null;
        player = dungeonInfo.getEntities().stream().filter(n -> n.getType().equals("Player")).findFirst().orElse(null);

        // Move character down
        dungeonInfo = controller.tick(null, Direction.DOWN);
        player = dungeonInfo.getEntities().stream().filter(n -> n.getType().equals("Player")).findFirst().orElse(null);
        assertEquals(new Position(1, 2), player.getPosition());
        // Move character down
        dungeonInfo = controller.tick(null, Direction.DOWN);
        player = dungeonInfo.getEntities().stream().filter(n -> n.getType().equals("Player")).findFirst().orElse(null);
        assertEquals(new Position(1, 3), player.getPosition());
        // Move character right
        dungeonInfo = controller.tick(null, Direction.RIGHT);
        player = dungeonInfo.getEntities().stream().filter(n -> n.getType().equals("Player")).findFirst().orElse(null);
        assertEquals(new Position(2, 3), player.getPosition());
        
        // // Spawner has been destroyed
        assertTrue(dungeonInfo.getEntities().stream().anyMatch(n -> n.getType().equals("ZombieToastSpawner")));
    }

    @Test
    public void testZombieSpawnerIsNotDestoyed() {
        // test whether a zombie spawner will break if player is next to spawner with a weapon
        // Create a controller
        DungeonManiaController controller = new DungeonManiaController();

        // Create a new game
        DungeonResponse dungeonInfo = controller.newGame("spawner", "Peaceful");


        // Assert spawner exists
        assertTrue(dungeonInfo.getEntities().stream().anyMatch(n -> n.getType().equals("ZombieToastSpawner")));

        // Get player entity
        EntityResponse player = dungeonInfo.getEntities().stream().filter(n -> n.getType().equals("Player")).findFirst().orElse(null);

        // Move character right
        dungeonInfo = controller.tick(null, Direction.RIGHT);
        player = dungeonInfo.getEntities().stream().filter(n -> n.getType().equals("Player")).findFirst().orElse(null);
        assertEquals(new Position(2, 1), player.getPosition());
        // Move character right
        dungeonInfo = controller.tick(null, Direction.RIGHT);
        player = dungeonInfo.getEntities().stream().filter(n -> n.getType().equals("Player")).findFirst().orElse(null);
        assertEquals(new Position(3, 1), player.getPosition());
        // Move character right
        dungeonInfo = controller.tick(null, Direction.DOWN);
        player = dungeonInfo.getEntities().stream().filter(n -> n.getType().equals("Player")).findFirst().orElse(null);
        assertEquals(new Position(3, 2), player.getPosition());
        
        // Spawner has not been destroyed as player does not have a weapon
        assertTrue(dungeonInfo.getEntities().stream().anyMatch(n -> n.getType().equals("ZombieToastSpawner")));
    }

    @Test
    public void testDoorOpens() {
        // test whether closed door obstructs player and unlocks with correct key
        // Create a controller
        DungeonManiaController controller = new DungeonManiaController();

        // Create a new game
        DungeonResponse dungeonInfo = controller.newGame("doors", "Peaceful");

        // Get player entity
        EntityResponse player = dungeonInfo.getEntities().stream().filter(n -> n.getType().equals("Player")).findFirst().orElse(null);

        // Move character right
        dungeonInfo = controller.tick(null, Direction.RIGHT);
        player = dungeonInfo.getEntities().stream().filter(n -> n.getType().equals("Player")).findFirst().orElse(null);
        assertEquals(new Position(2, 2), player.getPosition());
        // Move character right - door is locked
        dungeonInfo = controller.tick(null, Direction.RIGHT);
        player = dungeonInfo.getEntities().stream().filter(n -> n.getType().equals("Player")).findFirst().orElse(null);
        assertEquals(new Position(2, 2), player.getPosition());
        // Move character to get key1
        dungeonInfo = controller.tick(null, Direction.UP);
        player = dungeonInfo.getEntities().stream().filter(n -> n.getType().equals("Player")).findFirst().orElse(null);
        assertEquals(new Position(2, 1), player.getPosition());
        // Move character to door again
        dungeonInfo = controller.tick(null, Direction.DOWN);
        player = dungeonInfo.getEntities().stream().filter(n -> n.getType().equals("Player")).findFirst().orElse(null);
        assertEquals(new Position(2, 2), player.getPosition());
        // Move character into door
        dungeonInfo = controller.tick(null, Direction.RIGHT);
        player = dungeonInfo.getEntities().stream().filter(n -> n.getType().equals("Player")).findFirst().orElse(null);
        assertEquals(new Position(3, 2), player.getPosition());
    }

    @Test
    public void testDoorIncorrectKey() {
        // test whether door opens with wrong key
        // Create a controller
        DungeonManiaController controller = new DungeonManiaController();

        // Create a new game
        DungeonResponse dungeonInfo = controller.newGame("doors", "Peaceful");

        // Get player entity
        EntityResponse player = dungeonInfo.getEntities().stream().filter(n -> n.getType().equals("Player")).findFirst().orElse(null);

        // Move character right
        dungeonInfo = controller.tick(null, Direction.RIGHT);
        player = dungeonInfo.getEntities().stream().filter(n -> n.getType().equals("Player")).findFirst().orElse(null);
        assertEquals(new Position(2, 2) ,player.getPosition());
        // Move character right - door is locked
        dungeonInfo = controller.tick(null, Direction.RIGHT);
        player = dungeonInfo.getEntities().stream().filter(n -> n.getType().equals("Player")).findFirst().orElse(null);
        assertEquals(new Position(2, 2), player.getPosition());
        // Move character to get key1
        dungeonInfo = controller.tick(null, Direction.UP);
        player = dungeonInfo.getEntities().stream().filter(n -> n.getType().equals("Player")).findFirst().orElse(null);
        assertEquals(new Position(2, 1), player.getPosition());
        // Move character to other door
        dungeonInfo = controller.tick(null, Direction.DOWN);
        player = dungeonInfo.getEntities().stream().filter(n -> n.getType().equals("Player")).findFirst().orElse(null);
        assertEquals(new Position(2, 2), player.getPosition());
        // Move character to other door
        dungeonInfo = controller.tick(null, Direction.DOWN);
        player = dungeonInfo.getEntities().stream().filter(n -> n.getType().equals("Player")).findFirst().orElse(null);
        assertEquals(new Position(2, 3), player.getPosition());
        // Move character to other door
        dungeonInfo = controller.tick(null, Direction.DOWN);
        player = dungeonInfo.getEntities().stream().filter(n -> n.getType().equals("Player")).findFirst().orElse(null);
        assertEquals(new Position(2, 4), player.getPosition());
        // Move character into door - door is locked
        dungeonInfo = controller.tick(null, Direction.RIGHT);
        player = dungeonInfo.getEntities().stream().filter(n -> n.getType().equals("Player")).findFirst().orElse(null);
        assertEquals(new Position(2, 4), player.getPosition());
        // Move character to get key2
        dungeonInfo = controller.tick(null, Direction.DOWN);
        player = dungeonInfo.getEntities().stream().filter(n -> n.getType().equals("Player")).findFirst().orElse(null);
        assertEquals(new Position(2, 5), player.getPosition());
        // Move character towards door2
        dungeonInfo = controller.tick(null, Direction.UP);
        player = dungeonInfo.getEntities().stream().filter(n -> n.getType().equals("Player")).findFirst().orElse(null);
        assertEquals(new Position(2, 4), player.getPosition());
        // Move character into door
        dungeonInfo = controller.tick(null, Direction.RIGHT);
        player = dungeonInfo.getEntities().stream().filter(n -> n.getType().equals("Player")).findFirst().orElse(null);
        assertEquals(new Position(3, 4), player.getPosition());
    }

    @Test
    public void testPortalTeleportsPlayer() {
        // test whether a portal teleports a player to corresponding portal
        // Create a controller
        DungeonManiaController controller = new DungeonManiaController();

        // Create a new game
        DungeonResponse dungeonInfo = controller.newGame("portals-2", "Peaceful");

        // Get player entity
        EntityResponse player = dungeonInfo.getEntities().stream().filter(n -> n.getType().equals("Player")).findFirst().orElse(null);
        assertEquals(new Position(0, 1), player.getPosition());

        // Get player entity
        EntityResponse portal = dungeonInfo.getEntities().stream().filter(n -> n.getType().equals("Portal")).findFirst().orElse(null);
        assertEquals(new Position(1, 1), portal.getPosition());

        // Move character into portal (portal is obstructed on the RHS)
        dungeonInfo = controller.tick(null, Direction.RIGHT);
        player = dungeonInfo.getEntities().stream().filter(n -> n.getType().equals("Player")).findFirst().orElse(null);
        assertEquals(new Position(4, 1), player.getPosition());
        // Move player down
        dungeonInfo = controller.tick(null, Direction.DOWN);
        player = dungeonInfo.getEntities().stream().filter(n -> n.getType().equals("Player")).findFirst().orElse(null);
        assertEquals(new Position(4, 2), player.getPosition());
        // Move player up into portal
        dungeonInfo = controller.tick(null, Direction.UP);
        player = dungeonInfo.getEntities().stream().filter(n -> n.getType().equals("Player")).findFirst().orElse(null);
        assertEquals(new Position(1, 0), player.getPosition());
    }

    @Test
    public void testSwitchDoesNotObstruct() {
        // tests whether a floor obstructs a player other entities
        // Create a controller
        DungeonManiaController controller = new DungeonManiaController();

        // Create a new game
        DungeonResponse dungeonInfo = controller.newGame("boulderAndSwitch", "Peaceful");

        // Assert boulder exists
        assertTrue(dungeonInfo.getEntities().stream().anyMatch(n -> n.getType().equals("Boulder")));

        // Assert switch exists
        assertTrue(dungeonInfo.getEntities().stream().anyMatch(n -> n.getType().equals("Switch")));


        // Get player entity
        EntityResponse player = dungeonInfo.getEntities().stream().filter(n -> n.getType().equals("Player")).findFirst().orElse(null);

        // Move towards switch
        dungeonInfo = controller.tick(null, Direction.RIGHT);
        player = dungeonInfo.getEntities().stream().filter(n -> n.getType().equals("Player")).findFirst().orElse(null);
        assertEquals(new Position(2, 1), player.getPosition());
        // Move onto switch
        dungeonInfo = controller.tick(null, Direction.RIGHT);
        player = dungeonInfo.getEntities().stream().filter(n -> n.getType().equals("Player")).findFirst().orElse(null);
        assertEquals(new Position(3, 1), player.getPosition());
    }

    @Test
    public void testSwitchCanTrigger() {
        // tests whether switch is triggered if boulder is on it
        // Create a controller
        DungeonManiaController controller = new DungeonManiaController();

        // Create a new game
        DungeonResponse dungeonInfo = controller.newGame("boulderAndSwitch", "Peaceful");

        // Assert boulder exists
        assertTrue(dungeonInfo.getEntities().stream().anyMatch(n -> n.getType().equals("Boulder")));

        // Assert switch exists
        assertTrue(dungeonInfo.getEntities().stream().anyMatch(n -> n.getType().equals("Switch")));

        // Get player entity
        EntityResponse player = dungeonInfo.getEntities().stream().filter(n -> n.getType().equals("Player")).findFirst().orElse(null);

        // Get boulder entity
        EntityResponse boulder = dungeonInfo.getEntities().stream().filter(n -> n.getType().equals("Boulder")).findFirst().orElse(null);
        // Check boulder position
        assertEquals(new Position(2, 2), boulder.getPosition());

        // Move behind boulder
        dungeonInfo = controller.tick(null, Direction.DOWN);
        player = dungeonInfo.getEntities().stream().filter(n -> n.getType().equals("Player")).findFirst().orElse(null);
        assertEquals(new Position(1, 2), player.getPosition());
        // Move behind boulder
        dungeonInfo = controller.tick(null, Direction.DOWN);
        player = dungeonInfo.getEntities().stream().filter(n -> n.getType().equals("Player")).findFirst().orElse(null);
        assertEquals(new Position(1, 3), player.getPosition());
        // Move behind boulder
        dungeonInfo = controller.tick(null, Direction.RIGHT);
        player = dungeonInfo.getEntities().stream().filter(n -> n.getType().equals("Player")).findFirst().orElse(null);
        assertEquals(new Position(2, 3), player.getPosition());
        // Push boulder
        dungeonInfo = controller.tick(null, Direction.UP);
        player = dungeonInfo.getEntities().stream().filter(n -> n.getType().equals("Player")).findFirst().orElse(null);
        boulder = dungeonInfo.getEntities().stream().filter(n -> n.getType().equals("Boulder")).findFirst().orElse(null);
        assertEquals(new Position(2, 2), player.getPosition());
        assertEquals(new Position(2, 1), boulder.getPosition());
        // Push boulder onto switch
        dungeonInfo = controller.tick(null, Direction.LEFT);
        player = dungeonInfo.getEntities().stream().filter(n -> n.getType().equals("Player")).findFirst().orElse(null);
        boulder = dungeonInfo.getEntities().stream().filter(n -> n.getType().equals("Boulder")).findFirst().orElse(null);
        assertEquals(new Position(1, 2), player.getPosition());
        dungeonInfo = controller.tick(null, Direction.UP);
        player = dungeonInfo.getEntities().stream().filter(n -> n.getType().equals("Player")).findFirst().orElse(null);
        boulder = dungeonInfo.getEntities().stream().filter(n -> n.getType().equals("Boulder")).findFirst().orElse(null);
        assertEquals(new Position(1, 1), player.getPosition());
        dungeonInfo = controller.tick(null, Direction.RIGHT);
        player = dungeonInfo.getEntities().stream().filter(n -> n.getType().equals("Player")).findFirst().orElse(null);
        boulder = dungeonInfo.getEntities().stream().filter(n -> n.getType().equals("Boulder")).findFirst().orElse(null);
        assertEquals(new Position(2, 1), player.getPosition());
        assertEquals(new Position(3, 1), boulder.getPosition());
    }

    @Test
    public void testBoulderObstructsPlayer() {
        // tests whether a boulder being pushed into a wall obstructs a player
        // Create a controller
        DungeonManiaController controller = new DungeonManiaController();

        // Create a new game
        DungeonResponse dungeonInfo = controller.newGame("boulderAndSwitch", "Peaceful");

        // Assert boulder exists
        assertTrue(dungeonInfo.getEntities().stream().anyMatch(n -> n.getType().equals("Boulder")));

        // Assert switch exists
        assertTrue(dungeonInfo.getEntities().stream().anyMatch(n -> n.getType().equals("Switch")));

        // Get player entity
        EntityResponse player = dungeonInfo.getEntities().stream().filter(n -> n.getType().equals("Player")).findFirst().orElse(null);

        // Move behind boulder
        dungeonInfo = controller.tick(null, Direction.DOWN);
        player = dungeonInfo.getEntities().stream().filter(n -> n.getType().equals("Player")).findFirst().orElse(null);
        assertEquals(new Position(1, 2), player.getPosition());
        // Try push two boulders and stay in place
        dungeonInfo = controller.tick(null, Direction.RIGHT);
        player = dungeonInfo.getEntities().stream().filter(n -> n.getType().equals("Player")).findFirst().orElse(null);
        assertEquals(new Position(1, 2), player.getPosition());
    }

    @Test
    public void testExitDoesNotObstructs() {
        // test whether the exit obstructs a player
        // Create a controller
        DungeonManiaController controller = new DungeonManiaController();

        // Create a new game
        DungeonResponse dungeonInfo = controller.newGame("exit", "Peaceful");

        // Get player entity
        EntityResponse player = dungeonInfo.getEntities().stream().filter(n -> n.getType().equals("Player")).findFirst().orElse(null);

        // Move hop down onto exit
        dungeonInfo = controller.tick(null, Direction.DOWN);
        player = dungeonInfo.getEntities().stream().filter(n -> n.getType().equals("Player")).findFirst().orElse(null);
        assertEquals(new Position(1, 2), player.getPosition());
    }
}
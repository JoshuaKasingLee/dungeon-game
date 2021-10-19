package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class StaticEntityTest {
    @Test
    public void testDungeons() {
        assertTrue(DungeonManiaController.dungeons().size() > 0);
        assertTrue(DungeonManiaController.dungeons().contains("maze"));
    }

    @Test
    public void testWall() {
        // create a maze and player
        // attempt to walk into wall
        // throw exception

        // OR
        
        // Test that position of wall is not walk-through-able
    }

    @Test
    public void testZombieSpawnerCreatesEntity() {
        // test whether a zombie spawner creates an entity
    }

    @Test
    public void testZombieSpawnerIsDestoyed() {
        // test whether a zombie spawner will break if player is next to spawner with a weapon
    }

    @Test
    public void testDoorClosed() {
        // test whether closed door obstructs player
    }

    @Test
    public void testDoorOpen() {
        // test whether open door obstructs player
    }

    @Test
    public void testPortalObstructs() {
        // test whether a portal obstructs a player
    }

    @Test
    public void testPortalTeleportsPlayer() {
        // test whether a portal teleports a player to corresponding portal
    }

    @Test
    public void testPortalTeleportsEntity() {
        // test whether a portal teleports an entity (e.g. zombie)
    }

    @Test
    public void testSwitchDoesNotObstruct() {
        // tests whether a floor obstructs a player other entities
    }

    @Test
    public void testSwitchCanTrigger() {
        // tests whether switch is triggered if boulder is on it
    }

    @Test
    public void testBoulderObstructsPlayer() {
        // tests whether a boulder being pushed into a wall obstructs a player
    }

    @Test
    public void testBoulderObstructsNPCs() {
        // tests whether boulders obstruct NPCs
    }

    @Test
    public void testTwoBoulderObstruction() {
        // test whether two boulders adjacent to eachother will obstruct a character
    }

    @Test
    public void testBoulderIsMoveable() {
        // test whether a player can move a boulder
    }

    @Test
    public void testExitObstructs() {
        // test whether the exit obstructs a player
    }

    @Test
    public void testNPCsTriggerExit() {
        // test whether NPCs can trigger exit
    }

    @Test
    public void testExitIfGoalsNotComplete() {
        // test whether the exit triggers if it is not the last goal
    }

    @Test
    public void testExitTriggers() {
        // test whether the exit triggers upon final goal
    }

}
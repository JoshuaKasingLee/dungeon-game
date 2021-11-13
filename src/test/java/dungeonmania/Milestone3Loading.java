package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.Test;

// import java.util.ArrayList;
// import java.util.List;
import java.util.Arrays;

import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.moving_entities.Assassin;
import dungeonmania.moving_entities.Mercenary;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.static_entities.SwampTile;
import dungeonmania.util.Position;
import dungeonmania.util.Direction;

public class Milestone3Loading {

    @Test
    public void testNewEntitiesNewGame() {
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse dungeonInfo = controller.newGame("milestone3Entities", "Standard");
        assertEquals(true, dungeonInfo.getEntities().stream().anyMatch(x -> x.getType().equals("assassin")));
        assertEquals(true, dungeonInfo.getEntities().stream().anyMatch(x -> x.getType().equals("hydra")));
        assertEquals(true, dungeonInfo.getEntities().stream().anyMatch(x -> x.getType().equals("sun_stone")));
        assertEquals(true, dungeonInfo.getEntities().stream().anyMatch(x -> x.getType().equals("anduril")));

        assertDoesNotThrow(() -> controller.saveGame("testingNewEntities"));
        dungeonInfo = controller.loadGame("testingNewEntities");
        assertEquals(true, dungeonInfo.getEntities().stream().anyMatch(x -> x.getType().equals("assassin")));
        assertEquals(true, dungeonInfo.getEntities().stream().anyMatch(x -> x.getType().equals("hydra")));
        assertEquals(true, dungeonInfo.getEntities().stream().anyMatch(x -> x.getType().equals("sun_stone")));
        assertEquals(true, dungeonInfo.getEntities().stream().anyMatch(x -> x.getType().equals("anduril")));
    }

    @Test
    public void testAssassinsSpawnedFromMercenary() {
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse dungeonInfo = controller.newGame("enemyGoalTester", "Standard");

        while (!dungeonInfo.getEntities().stream().anyMatch(x -> x.getType().equals("assassin"))) {
            dungeonInfo = controller.newGame("enemyGoalTester", "Standard");
        }

        assertEquals(true, dungeonInfo.getEntities().stream().anyMatch(x -> x.getType().equals("assassin")));
    }
    
    @Test
    public void testCraftSceptreWithWoodAndTreasure() {
        DungeonManiaController controller = new DungeonManiaController();
        assertDoesNotThrow(() -> controller.newGame("craftingSceptre", "Standard"));
        assertDoesNotThrow(() -> controller.tick(null, Direction.DOWN));
        assertDoesNotThrow(() -> controller.tick(null, Direction.DOWN));
        DungeonResponse dungeonInfo = controller.tick(null, Direction.DOWN);
        assertEquals(true, dungeonInfo.getInventory().stream().anyMatch(x -> x.getType().equals("wood")));
        assertEquals(true, dungeonInfo.getInventory().stream().anyMatch(x -> x.getType().equals("treasure")));
        assertEquals(true, dungeonInfo.getInventory().stream().anyMatch(x -> x.getType().equals("sun_stone")));

        dungeonInfo = controller.build("sceptre");
        assertEquals(false, dungeonInfo.getInventory().stream().anyMatch(x -> x.getType().equals("wood")));
        assertEquals(false, dungeonInfo.getInventory().stream().anyMatch(x -> x.getType().equals("treasure")));
        assertEquals(false, dungeonInfo.getInventory().stream().anyMatch(x -> x.getType().equals("sun_stone")));

        assertEquals(true, dungeonInfo.getInventory().stream().anyMatch(x -> x.getType().equals("sceptre")));
        assertDoesNotThrow(() -> controller.saveGame("craftingSceptre"));
        dungeonInfo = controller.loadGame("craftingSceptre");
        assertEquals(true, dungeonInfo.getInventory().stream().anyMatch(x -> x.getType().equals("sceptre")));
    }

    @Test
    public void testCraftSceptreWithArrowAndKey() {
        DungeonManiaController controller = new DungeonManiaController();
        assertDoesNotThrow(() -> controller.newGame("craftingSceptre", "Standard"));
        assertDoesNotThrow(() -> controller.tick(null, Direction.RIGHT));
        assertDoesNotThrow(() -> controller.tick(null, Direction.RIGHT));
        assertDoesNotThrow(() -> controller.tick(null, Direction.RIGHT));
        DungeonResponse dungeonInfo = controller.tick(null, Direction.RIGHT);
        assertEquals(true, dungeonInfo.getInventory().stream().anyMatch(x -> x.getType().equals("arrow")));
        assertEquals(true, dungeonInfo.getInventory().stream().anyMatch(x -> x.getType().equals("key")));
        assertEquals(true, dungeonInfo.getInventory().stream().anyMatch(x -> x.getType().equals("sun_stone")));

        dungeonInfo = controller.build("sceptre");
        assertEquals(false, dungeonInfo.getInventory().stream().anyMatch(x -> x.getType().equals("arrow")));
        assertEquals(false, dungeonInfo.getInventory().stream().anyMatch(x -> x.getType().equals("key")));
        assertEquals(false, dungeonInfo.getInventory().stream().anyMatch(x -> x.getType().equals("sun_stone")));

        assertEquals(true, dungeonInfo.getInventory().stream().anyMatch(x -> x.getType().equals("sceptre")));
        assertDoesNotThrow(() -> controller.saveGame("craftingSceptre"));
        dungeonInfo = controller.loadGame("craftingSceptre");
        assertEquals(true, dungeonInfo.getInventory().stream().anyMatch(x -> x.getType().equals("sceptre")));
    }

    @Test
    public void testLoadingArmour() {
        DungeonManiaController controller = new DungeonManiaController();
        assertDoesNotThrow(() -> controller.loadGame("briber"));

    }
    @Test
    public void testMindControlMercenary() {
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse dungeonInfo = controller.loadGame("briber");

        assertEquals(true, dungeonInfo.getEntities().stream().anyMatch(x -> x.getType().equals("mercenary")));
        assertEquals(true, dungeonInfo.getInventory().stream().anyMatch(x -> x.getType().equals("sceptre")));

        Dungeon activeGame = controller.getActiveGame();

        Mercenary mercenary = (Mercenary)activeGame.getEntities().stream().filter(n -> n.getType().equals("mercenary")).findFirst().orElse(null);
        assertEquals(false, mercenary.isAlly());

        assertDoesNotThrow(() -> controller.tick("3", null));
        assertEquals(true, mercenary.isAlly());
        assertEquals("MercControlled", mercenary.getMercenaryState().getType());

        assertDoesNotThrow(() -> controller.saveGame("briberNewSave"));
        dungeonInfo = controller.loadGame("briberNewSave");
        mercenary = (Mercenary)activeGame.getEntities().stream().filter(n -> n.getType().equals("mercenary")).findFirst().orElse(null);
        assertEquals("MercControlled", mercenary.getMercenaryState().getType());

    }

    @Test
    public void testMindControlAssassin() {
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse dungeonInfo = controller.loadGame("briber");

        assertEquals(true, dungeonInfo.getEntities().stream().anyMatch(x -> x.getType().equals("assassin")));
        assertEquals(true, dungeonInfo.getInventory().stream().anyMatch(x -> x.getType().equals("sceptre")));

        Dungeon activeGame = controller.getActiveGame();

        Assassin assassin = (Assassin)activeGame.getEntities().stream().filter(n -> n.getType().equals("assassin")).findFirst().orElse(null);
        assertEquals(false, assassin.isAlly());

        assertDoesNotThrow(() -> controller.tick("3", null));
        assertEquals(true, assassin.isAlly());
        assertEquals("MercControlled", assassin.getMercenaryState().getType());
    }

    @Test
    public void testCraftMidnightArmour() {
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse dungeonInfo = controller.loadGame("briber");

        assertEquals(true, dungeonInfo.getInventory().stream().anyMatch(x -> x.getType().equals("armour")));
        assertEquals(true, dungeonInfo.getInventory().stream().anyMatch(x -> x.getType().equals("sun_stone")));

        dungeonInfo = controller.build("midnight_armour");

        assertEquals(true, dungeonInfo.getInventory().stream().anyMatch(x -> x.getType().equals("midnight_armour")));

        assertEquals(false, dungeonInfo.getInventory().stream().anyMatch(x -> x.getType().equals("armour")));
        assertEquals(false, dungeonInfo.getInventory().stream().anyMatch(x -> x.getType().equals("sun_stone")));

        assertDoesNotThrow(() -> controller.saveGame("craftingMidnightArmour"));
        dungeonInfo = controller.loadGame("craftingMidnightArmour");
        assertEquals(true, dungeonInfo.getInventory().stream().anyMatch(x -> x.getType().equals("midnight_armour")));
    }

    @Test
    public void testLoadingSwampTile() {
        DungeonManiaController controller = new DungeonManiaController();
        assertDoesNotThrow(() -> controller.newGame("swampTile", "Standard"));
        assertEquals(true, controller.getActiveGame().getEntities().stream().anyMatch(x -> x.getType().equals("swamp_tile")));
        SwampTile swampTile = (SwampTile)controller.getActiveGame().getEntities().stream().filter(n -> n.getType().equals("swamp_tile")).findFirst().orElse(null);
        double movementFactor = swampTile.getMovementFactor();
        assertDoesNotThrow(() -> controller.saveGame("swampTile"));
        assertDoesNotThrow(() -> controller.loadGame("swampTile"));
        assertEquals(true, controller.getActiveGame().getEntities().stream().anyMatch(x -> x.getType().equals("swamp_tile")));
        swampTile = (SwampTile)controller.getActiveGame().getEntities().stream().filter(n -> n.getType().equals("swamp_tile")).findFirst().orElse(null);
        assertEquals(movementFactor, swampTile.getMovementFactor());
    }

    // @Test
    // public 

    

}


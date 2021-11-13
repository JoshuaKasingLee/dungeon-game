package dungeonmania;


import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import dungeonmania.util.Direction;

import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.response.models.DungeonResponse;

import java.lang.IllegalArgumentException;


public class GameCraftingItems {

    @Test
    public void testBuildables() {
        DungeonManiaController controller = new DungeonManiaController();
        assertThrows(IllegalArgumentException.class, () -> controller.build("random"));
    }

    @Test
    public void testNotEnoughResources() {
        DungeonManiaController controller = new DungeonManiaController();
        assertDoesNotThrow(() -> controller.newGame("interactInvalidTester", "Standard"));
        assertThrows(InvalidActionException.class, () -> controller.build("bow"));
    }

    @Test
    public void testCraftBowAndShield() {
        DungeonManiaController controller = new DungeonManiaController();
        assertDoesNotThrow(() -> controller.newGame("craftingTest", "Standard"));
        assertDoesNotThrow(() -> controller.tick(null, Direction.DOWN));
        assertDoesNotThrow(() -> controller.tick(null, Direction.DOWN));
        assertDoesNotThrow(() -> controller.tick(null, Direction.DOWN));
        assertDoesNotThrow(() -> controller.tick(null, Direction.DOWN));
        assertDoesNotThrow(() -> controller.tick(null, Direction.DOWN));
        assertDoesNotThrow(() -> controller.tick(null, Direction.DOWN));
        assertDoesNotThrow(() -> controller.tick(null, Direction.DOWN));
        assertDoesNotThrow(() -> controller.tick(null, Direction.DOWN));
        assertDoesNotThrow(() -> controller.tick(null, Direction.DOWN));
        assertDoesNotThrow(() -> controller.build("bow"));
        DungeonResponse dungeonInfo = controller.build("shield");
        assertEquals(true, dungeonInfo.getInventory().stream().anyMatch(x -> x.getType().equals("bow")));
        assertEquals(true, dungeonInfo.getInventory().stream().anyMatch(x -> x.getType().equals("shield")));
        assertDoesNotThrow(() -> controller.saveGame("craftingResults"));
        dungeonInfo = controller.loadGame("craftingResults");
        assertEquals(true, dungeonInfo.getInventory().stream().anyMatch(x -> x.getType().equals("bow")));
        assertEquals(true, dungeonInfo.getInventory().stream().anyMatch(x -> x.getType().equals("shield")));
    }

    @Test
    public void testCraftInsufficientResources() {
        DungeonManiaController controller = new DungeonManiaController();
        assertDoesNotThrow(() -> controller.newGame("craftingTest", "Standard"));
        assertThrows(InvalidActionException.class, () -> controller.build("bow"));
        assertThrows(InvalidActionException.class, () -> controller.build("shield"));
    }

    @Test
    public void testCraftInvalidItem() {
        DungeonManiaController controller = new DungeonManiaController();
        assertDoesNotThrow(() -> controller.newGame("craftingTest", "Standard"));
        assertThrows(IllegalArgumentException.class, () -> controller.build("Key"));
    }


}
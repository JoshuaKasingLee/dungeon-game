package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.items.InvincibilityPotion;
import dungeonmania.items.Item;
import dungeonmania.moving_entities.Mercenary;
import dungeonmania.player.Player;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.response.models.ItemResponse;
import dungeonmania.static_entities.Wall;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

import java.lang.IllegalArgumentException;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class GameProcessingTest {
    @Test
    public void testNewGame() {

        DungeonManiaController controller = new DungeonManiaController();

        DungeonResponse dungeonInfo = controller.newGame("maze", "Standard");
        assertEquals("maze", dungeonInfo.getDungeonName());
        assertEquals(":exit ", dungeonInfo.getGoals());
        assertEquals(new ArrayList<>(), dungeonInfo.getInventory());
        assertEquals(new ArrayList<>(), dungeonInfo.getBuildables());
    }

    @Test
    public void testNewGameBadGamemode() {

        DungeonManiaController controller = new DungeonManiaController();

        assertThrows(IllegalArgumentException.class, () -> controller.newGame("maze", "Super Hard"));
        
    }

    @Test
    public void testNewGameBadDungeonName() {

        DungeonManiaController controller = new DungeonManiaController();

        assertThrows(IllegalArgumentException.class, () -> controller.newGame("Redonkulous", "Peaceful"));   
    }

    @Test
    public void testNewGameSuccessful() {

        DungeonManiaController controller = new DungeonManiaController();

        assertDoesNotThrow(() -> controller.newGame("maze", "Standard"));

    }

    @Test
    public void testNewGameDungeonResponse() {
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse dungeonInfo;
        assertDoesNotThrow(() -> controller.newGame("simple", "Standard"));
        dungeonInfo = controller.newGame("simple", "Standard");
        assertEquals("simple", dungeonInfo.getDungeonName());

        EntityResponse playerResponse = new EntityResponse("1", "player", new Position(1, 2), false);
        assertEquals(playerResponse.getType(), dungeonInfo.getEntities().get(0).getType());
        assertEquals(playerResponse.getPosition(), dungeonInfo.getEntities().get(0).getPosition());
        assertEquals(playerResponse.isInteractable(), dungeonInfo.getEntities().get(0).isInteractable());
    }

    @Test
    public void testSaveGameSuccessful() {

        DungeonManiaController controller = new DungeonManiaController();

        assertDoesNotThrow(() -> controller.newGame("boulders", "Peaceful"));
        assertDoesNotThrow(() -> controller.saveGame("boulders"));
    }

    @Test
    public void testLoadGameDoesntExist() {

        DungeonManiaController controller = new DungeonManiaController();

        assertThrows(IllegalArgumentException.class, () -> controller.loadGame("ThisDoesntExist"));

    }


    @Test
    public void testLoadGameWorks() {

        DungeonManiaController controller = new DungeonManiaController();

        assertDoesNotThrow(() -> controller.newGame("boulders", "Peaceful"));
        assertDoesNotThrow(() -> controller.saveGame("boulders2"));
        DungeonResponse dungeonInfo = controller.loadGame("boulders2");
        assertEquals("boulders", dungeonInfo.getDungeonName());
        assertEquals(new ArrayList<ItemResponse>(), dungeonInfo.getInventory());
        assertEquals(":boulders ", dungeonInfo.getGoals());
    }

}

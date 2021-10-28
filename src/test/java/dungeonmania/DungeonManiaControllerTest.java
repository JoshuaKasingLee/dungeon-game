package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.util.Position;

import java.lang.IllegalArgumentException;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;


public class DungeonManiaControllerTest {
    @Test
    public void testNewGame() {

        DungeonManiaController controller = new DungeonManiaController();

        DungeonResponse dungeonInfo = controller.newGame("maze", "Peaceful");
        assertEquals(dungeonInfo.getDungeonName(), "maze");
        assertEquals(dungeonInfo.getGoals(), "exit");
        assertEquals(dungeonInfo.getInventory(), new ArrayList<>());
        assertEquals(dungeonInfo.getBuildables(), new ArrayList<>());
       // need one for entities and dungeonID too
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

        assertDoesNotThrow(() -> controller.newGame("advanced", "Standard"));

    }

    @Test
    public void testNewGameDungeonResponse() {
        DungeonManiaController controller = new DungeonManiaController();
        assertDoesNotThrow(() -> controller.newGame("boulderGoalTester", "Standard"));

        EntityResponse playerResponse = new EntityResponse("0", "player", new Position(1, 2), false);
        EntityResponse boulderResponse = new EntityResponse("1", "boulder", new Position(1, 1), false);
        EntityResponse switchResponse = new EntityResponse("2", "switch", new Position(1, 0), false);

        List<EntityResponse> entityResponseList = new ArrayList<>(Arrays.asList(playerResponse, boulderResponse, switchResponse));
        List<ItemResponse> inventoryList = new ArrayList<>();
        List<String> buildablesList = new ArrayList<>();
        assertEquals(new DungeonResponse("0", "boulderGoalTester", entityResponseList, inventoryList, buildablesList, ":boulders "), controller.newGame("boulderGoalTester", "Standard"));
    }

    @Test
    public void testSaveGameDoesntExist() {

        DungeonManiaController controller = new DungeonManiaController();

        assertThrows(IllegalArgumentException.class, () -> controller.saveGame("Nothing"));

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
        assertDoesNotThrow(() -> controller.saveGame("boulders"));
        assertDoesNotThrow(() -> controller.loadGame("boulders"));

    }

    @Test
    public void testAllGamesEmpty() {
        DungeonManiaController controller = new DungeonManiaController();

        assertEquals(controller.allGames(), new ArrayList<String>());
    }

    @Test
    public void testAllGamesMultiple() {
        
        DungeonManiaController controller = new DungeonManiaController();
        
        assertDoesNotThrow(() -> controller.newGame("boulders", "Peaceful"));
        assertDoesNotThrow(() -> controller.saveGame("boulders"));
        assertDoesNotThrow(() -> controller.newGame("advanced", "Standard"));
        assertDoesNotThrow(() -> controller.saveGame("advanced"));


        List<String> listOfGames = new ArrayList<String>();
        listOfGames.add("boulders");
        listOfGames.add("advanced");
        assertEquals(controller.allGames(), listOfGames);
    }




}
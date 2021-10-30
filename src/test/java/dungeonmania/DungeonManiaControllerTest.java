package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.response.models.ItemResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

import java.lang.IllegalArgumentException;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;


public class DungeonManiaControllerTest {
    @Test
    public void testNewGame() {

        DungeonManiaController controller = new DungeonManiaController();

        DungeonResponse dungeonInfo = controller.newGame("maze", "Standard");
        assertEquals("maze", dungeonInfo.getDungeonName());
        assertEquals(":exit ", dungeonInfo.getGoals());
        assertEquals(new ArrayList<>(), dungeonInfo.getInventory());
        assertEquals(new ArrayList<>(), dungeonInfo.getBuildables());
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

        assertDoesNotThrow(() -> controller.newGame("maze", "Standard"));

    }

    @Test
    public void testNewGameDungeonResponse() {
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse dungeonInfo;
        assertDoesNotThrow(() -> controller.newGame("simple", "Standard"));
        dungeonInfo = controller.newGame("simple", "Standard");
        assertEquals("1", dungeonInfo.getDungeonId());
        assertEquals("simple", dungeonInfo.getDungeonName());
        // List<EntityResponse> expEntityResponses = new ArrayList<EntityResponse>();
        EntityResponse playerResponse = new EntityResponse("1", "Player", new Position(1, 2), false);
        // expEntityResponses.add(playerResponse);
        // List<EntityResponse> entityResponseList = new ArrayList<>(Arrays.asList(playerResponse, boulderResponse, switchResponse));
        assertEquals(playerResponse.getType(), dungeonInfo.getEntities().get(0).getType());
        assertEquals(playerResponse.getPosition(), dungeonInfo.getEntities().get(0).getPosition());
        assertEquals(playerResponse.getId(), dungeonInfo.getEntities().get(0).getId());
        assertEquals(playerResponse.isInteractable(), dungeonInfo.getEntities().get(0).isInteractable());
        // assertEquals(playerResponse, dungeonInfo.getEntities().get(0));

        

        // List<ItemResponse> inventoryList = new ArrayList<>();
        // List<String> buildablesList = new ArrayList<>();
        // assertEquals(new DungeonResponse("0", "boulderGoalTester", entityResponseList, inventoryList, buildablesList, ":boulders "), dungeonInfo);
    }

    @Test
    public void testSaveGameSuccessful() {

        DungeonManiaController controller = new DungeonManiaController();

        assertDoesNotThrow(() -> controller.newGame("boulders", "Peaceful"));
        // assertEquals(null, controller.getActiveGame().getOverallGoal());
        // assertEquals(null, controller.saveGame("boulders"));
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
        assertDoesNotThrow(() -> controller.loadGame("boulders2"));
        DungeonResponse dungeonInfo = controller.loadGame("boulders2");
        assertEquals("boulders", dungeonInfo.getDungeonName());
        assertEquals(new ArrayList<ItemResponse>(), dungeonInfo.getInventory());
        assertEquals(":boulders ", dungeonInfo.getGoals());



    }

    @Test
    public void testAllGamesEmpty() {
        DungeonManiaController controller = new DungeonManiaController();

        assertEquals(new ArrayList<String>(), controller.allGames());
    }

    @Test
    public void testAllGamesMultiple() {
        
        DungeonManiaController controller = new DungeonManiaController();
        
        assertDoesNotThrow(() -> controller.newGame("boulders", "Peaceful"));
        assertDoesNotThrow(() -> controller.saveGame("boulders"));
        assertDoesNotThrow(() -> controller.newGame("exit", "Standard"));
        assertDoesNotThrow(() -> controller.saveGame("advanced"));


        List<String> listOfGames = new ArrayList<String>();
        listOfGames.add("advanced");
        listOfGames.add("boulders");
        assertEquals(controller.allGames(), listOfGames);
    }

    @Test
    public void tickMovement() {
        DungeonManiaController controller = new DungeonManiaController();
        assertDoesNotThrow(() -> controller.newGame("boulderGoalTester", "Standard"));
        assertDoesNotThrow(() -> controller.tick(null, Direction.UP));

        // EntityResponse playerResponse = new EntityResponse("0", "player", new Position(1, 3), false);
        // EntityResponse boulderResponse = new EntityResponse("1", "boulder", new Position(1, 1), false);
        // EntityResponse switchResponse = new EntityResponse("2", "switch", new Position(1, 0), false);

        // List<EntityResponse> entityResponseList = new ArrayList<>(Arrays.asList(playerResponse, boulderResponse, switchResponse));
        // List<ItemResponse> inventoryList = new ArrayList<>();
        // List<String> buildablesList = new ArrayList<>();
        // assertEquals(new DungeonResponse("0", "boulderGoalTester", entityResponseList, inventoryList, buildablesList, ":boulders "), controller.newGame("boulderGoalTester", "Standard"));
    }

    // public void tickHealthPotion() {
    //     DungeonManiaController controller = new DungeonManiaController();
    //     assertDoesNotThrow(() -> controller.newGame("boulderGoalTester", "Standard"));
    //     assertDoesNotThrow(() -> controller.tick(null , Direction.UP));

    //     EntityResponse playerResponse = new EntityResponse("0", "player", new Position(1, 3), false);
    //     EntityResponse boulderResponse = new EntityResponse("1", "boulder", new Position(1, 1), false);
    //     EntityResponse switchResponse = new EntityResponse("2", "switch", new Position(1, 0), false);

    //     List<EntityResponse> entityResponseList = new ArrayList<>(Arrays.asList(playerResponse, boulderResponse, switchResponse));
    //     List<ItemResponse> inventoryList = new ArrayList<>();
    //     List<String> buildablesList = new ArrayList<>();
    //     assertEquals(new DungeonResponse("0", "boulderGoalTester", entityResponseList, inventoryList, buildablesList, ":boulders "), controller.newGame("boulderGoalTester", "Standard"));
    // }
    


}
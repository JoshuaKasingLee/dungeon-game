package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import dungeonmania.exceptions.InvalidActionException;
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
    public void testTickInvalidItem() {
        DungeonManiaController controller = new DungeonManiaController();
        assertDoesNotThrow(() -> controller.newGame("exit", "Standard"));

        assertThrows(IllegalArgumentException.class, () -> controller.tick("random", null));
    }

    @Test
    public void testTickItemNotInInventory() {
        DungeonManiaController controller = new DungeonManiaController();
        assertDoesNotThrow(() -> controller.newGame("exit", "Standard"));

        assertThrows(IllegalArgumentException.class, () -> controller.tick("invincibility-potion", null));
    }

    @Test
    public void testTickMovement() {
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse dungeonInfoPre = controller.newGame("boulderGoalTester", "Standard");

        assertEquals("Player", dungeonInfoPre.getEntities().get(0).getType());
        assertEquals("Boulder", dungeonInfoPre.getEntities().get(1).getType());
        assertEquals("Switch", dungeonInfoPre.getEntities().get(2).getType());
        assertEquals(3, dungeonInfoPre.getEntities().size());

        assertEquals(3, controller.getActiveGame().getEntities().size());


        DungeonResponse dungeonInfo = controller.tick(null, Direction.UP);
        assertEquals(3, dungeonInfo.getEntities().size());

        assertEquals(new ArrayList<>(), dungeonInfo.getInventory());

        assertEquals("Player", dungeonInfo.getEntities().get(0).getType());
        assertEquals(new Position(1, 1), dungeonInfo.getEntities().get(0).getPosition());
        assertEquals("0", dungeonInfo.getEntities().get(0).getId());
        assertEquals(false, dungeonInfo.getEntities().get(0).isInteractable());

        assertEquals("Player", dungeonInfo.getEntities().get(0).getType());
        assertEquals("Boulder", dungeonInfo.getEntities().get(1).getType());
        assertEquals("Switch", dungeonInfo.getEntities().get(2).getType());
        assertEquals(3, dungeonInfo.getEntities().size());
    }

    @Test
    public void testInteractInvalidEntityId() {
        DungeonManiaController controller = new DungeonManiaController();
        assertDoesNotThrow(() -> controller.newGame("exit", "Standard"));

        assertThrows(IllegalArgumentException.class, () -> controller.interact("random"));
    }

    @Test
    public void testInteractNoGoldOrWeapon() {
        DungeonManiaController controller = new DungeonManiaController();
        assertDoesNotThrow(() -> controller.newGame("interactInvalidTester", "Standard"));

        assertThrows(IllegalArgumentException.class, () -> controller.interact("mercenary"));
        assertThrows(IllegalArgumentException.class, () -> controller.interact("zombie_toast_spawner"));
    }

    @Test
    public void testInteractBribingSuccessful() {
        DungeonManiaController controller = new DungeonManiaController();
        assertDoesNotThrow(() -> controller.loadGame("interactTesterSuccessful"));

        Dungeon activeGame = controller.getActiveGame();
        assertEquals("Mercenary", activeGame.getEntities().get(1).getType());
        
        Inventory inv = activeGame.getInventory();

        assertEquals(Arrays.asList("Treasure"), inv.listInventory());

        assertEquals("Treasure", activeGame.getInventory().getInventoryList().get(0).getType());
        
        // Treasure treasure = new Treasure(new Position(3, 3), activeGame);
        // activeGame.moveToInventory(treasure);
        // assertEquals("Treasure", activeGame.getInventory().getInventoryList().get(1).getType());

        controller.interact("1");

        Dungeon dungeon = controller.getActiveGame();
        List<Entity> entities = dungeon.getEntities();
        boolean isAlly = false;

        for (Entity entity: entities) {
            if (entity instanceof Mercenary) {
                isAlly = ((Mercenary)entity).isAlly();
            }
        }

        assertEquals(true, isAlly);
    }

    @Test
    public void testBuildables() {
        DungeonManiaController controller = new DungeonManiaController();
        assertThrows(IllegalArgumentException.class, () -> controller.build("random"));

    }




    //TODO: Test exception thrown when merc and zombie spawner too far
    //TODO: Test zombie toast spawner is killed
    // TODO: Test potion usage.


}
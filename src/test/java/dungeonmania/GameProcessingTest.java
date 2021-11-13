package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.Test;

import dungeonmania.items.Item;

import dungeonmania.player.Player;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.response.models.ItemResponse;
import dungeonmania.static_entities.Wall;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;


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

    @Test
    public void createVarietyDungeon() {
        DungeonManiaController controller = new DungeonManiaController();
        assertDoesNotThrow(() -> controller.newGame("items", "Standard"));
        assertDoesNotThrow(() -> controller.saveGame("items2"));
        assertDoesNotThrow(() -> controller.loadGame("items2"));
    }

    @Test
    public void testLoadDifferentEntities() {
        DungeonManiaController controller = new DungeonManiaController();
        assertDoesNotThrow(() -> controller.newGame("manyEntitiesTester", "Standard"));
        assertDoesNotThrow(() -> controller.saveGame("differentEntitiesSaveTester"));
        assertDoesNotThrow(() -> controller.loadGame("differentEntitiesSaveTester"));
        assertDoesNotThrow(() -> controller.tick(null, Direction.DOWN));
        DungeonResponse dungeonInfo = controller.tick(null, Direction.DOWN);
        ItemResponse invincibilityPotion = dungeonInfo.getInventory().stream().filter(n -> n.getType().equals("invincibility_potion")).findFirst().orElse(null);

        assertDoesNotThrow(() -> controller.tick(invincibilityPotion.getId(), null));
        assertDoesNotThrow(() -> controller.loadGame("differentEntitiesSaveTester"));

    }

    @Test
    public void testOrGoalString() {
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse dungeonInfo = controller.newGame("orGoalTester", "standard");
        assertDoesNotThrow(() -> controller.tick(null, Direction.RIGHT));

        assertEquals(":treasure :exit ", dungeonInfo.getGoals());
    }

    @Test
    public void testInvincibleStateSaved() {
        DungeonManiaController controller = new DungeonManiaController();
        assertDoesNotThrow(() -> controller.newGame("potionUsed", "standard"));
        Player player = controller.getActiveGame().getPlayer();
        String playerState = player.getPlayerState().getType();
        assertEquals("Standard", playerState);

        DungeonResponse dungeonInfo = controller.tick(null, Direction.DOWN); 
        assertEquals(true, dungeonInfo.getInventory().stream().anyMatch(x -> x.getType().equals("invincibility_potion")));

        Item invincibilityPotion = player.getInventory().getInventoryList().stream().filter(n -> n.getType().equals("invincibility_potion")).findFirst().orElse(null);
        String invincibilityPotionId = invincibilityPotion.getId();
        dungeonInfo = controller.tick(invincibilityPotionId, null);
        assertEquals(false, dungeonInfo.getInventory().stream().anyMatch(x -> x.getType().equals("invincibility_potion")));
        
        assertDoesNotThrow(() -> controller.saveGame("invincibleStateSaved"));
        assertDoesNotThrow(() -> controller.loadGame("invincibleStateSaved"));

        player = controller.getActiveGame().getPlayer();
        playerState = player.getPlayerState().getType();
        assertEquals("Invincible", playerState);
    }


    @Test
    public void testInvisibleStateSaved() {
        DungeonManiaController controller = new DungeonManiaController();
        assertDoesNotThrow(() -> controller.newGame("potionUsed", "standard"));
        Player player = controller.getActiveGame().getPlayer();
        String playerState = player.getPlayerState().getType();
        assertEquals("Standard", playerState);

        assertDoesNotThrow(() -> controller.tick(null, Direction.DOWN));
        DungeonResponse dungeonInfo = controller.tick(null, Direction.RIGHT);

        assertEquals(true, dungeonInfo.getInventory().stream().anyMatch(x -> x.getType().equals("invisibility_potion")));

        Item invisibilityPotion = player.getInventory().getInventoryList().stream().filter(n -> n.getType().equals("invisibility_potion")).findFirst().orElse(null);
        String invisibilityPotionId = invisibilityPotion.getId();
        dungeonInfo = controller.tick(invisibilityPotionId, null);
        assertEquals(false, dungeonInfo.getInventory().stream().anyMatch(x -> x.getType().equals("invisibility_potion")));
        
        assertDoesNotThrow(() -> controller.saveGame("invisibleStateSaved"));
        assertDoesNotThrow(() -> controller.loadGame("invisibleStateSaved"));

        player = controller.getActiveGame().getPlayer();
        playerState = player.getPlayerState().getType();
        assertEquals("Invisible", playerState);
    }

    

    @Test
    public void testLoadingManyItems() {
        DungeonManiaController controller = new DungeonManiaController();
        assertDoesNotThrow(() -> controller.newGame("manyItems", "standard"));

        for (int i = 0; i < 10; i++) {
            assertDoesNotThrow(() -> controller.tick(null, Direction.DOWN)); 
        }
        DungeonResponse dungeonInfo = controller.tick(null, Direction.DOWN); 

        assertEquals(true, dungeonInfo.getInventory().stream().anyMatch(x -> x.getType().equals("one_ring")));
        assertEquals(true, dungeonInfo.getInventory().stream().anyMatch(x -> x.getType().equals("health_potion")));
        assertEquals(true, dungeonInfo.getInventory().stream().anyMatch(x -> x.getType().equals("invisibility_potion")));
        assertEquals(true, dungeonInfo.getInventory().stream().anyMatch(x -> x.getType().equals("wood")));
        assertEquals(true, dungeonInfo.getInventory().stream().anyMatch(x -> x.getType().equals("arrow")));
        assertEquals(true, dungeonInfo.getInventory().stream().anyMatch(x -> x.getType().equals("bomb")));
        assertEquals(true, dungeonInfo.getInventory().stream().anyMatch(x -> x.getType().equals("sword")));
        assertEquals(true, dungeonInfo.getInventory().stream().anyMatch(x -> x.getType().equals("sun_stone")));
        assertEquals(true, dungeonInfo.getInventory().stream().anyMatch(x -> x.getType().equals("anduril")));

        assertDoesNotThrow(() -> controller.saveGame("manyInventoryItems"));
        assertDoesNotThrow(() -> controller.loadGame("manyInventoryItems"));
    }

    @Test
    public void testInviciblePotionNoEffectHardMode() {
        DungeonManiaController controller = new DungeonManiaController();
        assertDoesNotThrow(() -> controller.newGame("potionUsed", "Hard"));
        Player player = controller.getActiveGame().getPlayer();
        String playerState = player.getPlayerState().getType();
        assertEquals("Standard", playerState);

        DungeonResponse dungeonInfo = controller.tick(null, Direction.DOWN); 
        assertEquals(true, dungeonInfo.getInventory().stream().anyMatch(x -> x.getType().equals("invincibility_potion")));

        Item invincibilityPotion = player.getInventory().getInventoryList().stream().filter(n -> n.getType().equals("invincibility_potion")).findFirst().orElse(null);
        String invincibilityPotionId = invincibilityPotion.getId();
        dungeonInfo = controller.tick(invincibilityPotionId, null);
        assertEquals(false, dungeonInfo.getInventory().stream().anyMatch(x -> x.getType().equals("invincibility_potion")));

        player = controller.getActiveGame().getPlayer();
        playerState = player.getPlayerState().getType();
        assertEquals("Standard", playerState);
    }

    @Test 
    public void illegalGenerateGamemode() {
        DungeonManiaController controller = new DungeonManiaController();
        assertThrows(IllegalArgumentException.class, () -> controller.generateDungeon(1, 2, 3, 4, "superduperhard"));
    }

    @Test 
    public void testPrimGenerate() {
        DungeonManiaController controller = new DungeonManiaController();
        controller.generateDungeon(1, 2, 3, 4, "peaceful");
        Dungeon activeGame = controller.getActiveGame();
        List<Entity> entities =  activeGame.getEntities();
        int exitCounter = 0;
        int playerCounter = 0;
        for (Entity e : entities) {
            if (e.getType().equals("exit")) {
                exitCounter++;
            }
            if (e.getType().equals("player")) {
                playerCounter++;
            }
        }
        assertEquals(1, exitCounter);
        assertEquals(1, playerCounter);

        Position positionChecker = new Position(0, 0);

        for (int i = 0; i < 50; i++) {
            positionChecker.translateBy(Direction.RIGHT);
            List<Entity> entitiesAtPos = activeGame.getEntities(positionChecker);
            boolean checkWall = entitiesAtPos.stream().anyMatch(e -> e instanceof Wall);
            assertTrue(checkWall);
        }

        for (int i = 0; i < 50; i++) {
            positionChecker.translateBy(Direction.DOWN);
            List<Entity> entitiesAtPos = activeGame.getEntities(positionChecker);
            boolean checkWall = entitiesAtPos.stream().anyMatch(e -> e instanceof Wall);
            assertTrue(checkWall);
        }
        
        for (int i = 0; i < 50; i++) {
            positionChecker.translateBy(Direction.LEFT);
            List<Entity> entitiesAtPos = activeGame.getEntities(positionChecker);
            boolean checkWall = entitiesAtPos.stream().anyMatch(e -> e instanceof Wall);
            assertTrue(checkWall);
        }

        for (int i = 0; i < 50; i++) {
            positionChecker.translateBy(Direction.UP);
            List<Entity> entitiesAtPos = activeGame.getEntities(positionChecker);
            boolean checkWall = entitiesAtPos.stream().anyMatch(e -> e instanceof Wall);
            assertTrue(checkWall);
        }

    }




}

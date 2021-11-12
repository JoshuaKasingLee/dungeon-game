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
        assertEquals("simple", dungeonInfo.getDungeonName());
        // List<EntityResponse> expEntityResponses = new ArrayList<EntityResponse>();
        EntityResponse playerResponse = new EntityResponse("1", "player", new Position(1, 2), false);
        // expEntityResponses.add(playerResponse);
        // List<EntityResponse> entityResponseList = new ArrayList<>(Arrays.asList(playerResponse, boulderResponse, switchResponse));
        assertEquals(playerResponse.getType(), dungeonInfo.getEntities().get(0).getType());
        assertEquals(playerResponse.getPosition(), dungeonInfo.getEntities().get(0).getPosition());
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
        DungeonResponse dungeonInfo = controller.loadGame("boulders2");
        assertEquals("boulders", dungeonInfo.getDungeonName());
        assertEquals(new ArrayList<ItemResponse>(), dungeonInfo.getInventory());
        assertEquals(":boulders ", dungeonInfo.getGoals());



    }


    @Test
    public void testTickInvalidItem() {
        DungeonManiaController controller = new DungeonManiaController();
        assertDoesNotThrow(() -> controller.newGame("exit", "Standard"));

        assertThrows(IllegalArgumentException.class, () -> controller.tick("random", Direction.UP));
    }

    @Test
    public void testTickItemNotInInventory() {
        DungeonManiaController controller = new DungeonManiaController();
        assertDoesNotThrow(() -> controller.newGame("potionUsed", "Standard"));
        List<Entity> entities = controller.getActiveGame().getEntities(); 
        String potionId = null;
        for (Entity entity: entities) {
            if (entity instanceof InvincibilityPotion) {
                potionId = entity.getId();
            }
        }

        final String unusableItem = potionId;

        assertThrows(InvalidActionException.class, () -> controller.tick(unusableItem, null));
    }

    @Test
    public void testTickMovement() {
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse dungeonInfoPre = controller.newGame("boulderGoalTester", "Standard");

        assertEquals("player", dungeonInfoPre.getEntities().get(0).getType());
        assertEquals("boulder", dungeonInfoPre.getEntities().get(1).getType());
        assertEquals("switch", dungeonInfoPre.getEntities().get(2).getType());
        assertEquals(3, dungeonInfoPre.getEntities().size());

        assertEquals(3, controller.getActiveGame().getEntities().size());


        DungeonResponse dungeonInfo = controller.tick(null, Direction.UP);
        assertEquals(3, dungeonInfo.getEntities().size());

        assertEquals(new ArrayList<>(), dungeonInfo.getInventory());

        assertEquals("player", dungeonInfo.getEntities().get(0).getType());
        assertEquals("boulder", dungeonInfo.getEntities().get(1).getType());
        assertEquals(new Position(1, 0), dungeonInfo.getEntities().get(1).getPosition());
        assertEquals(new Position(1, 1), dungeonInfo.getEntities().get(0).getPosition());
        assertEquals(false, dungeonInfo.getEntities().get(0).isInteractable());

        assertEquals("player", dungeonInfo.getEntities().get(0).getType());
        assertEquals("boulder", dungeonInfo.getEntities().get(1).getType());
        assertEquals("switch", dungeonInfo.getEntities().get(2).getType());
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
        DungeonResponse dungeonInfo =  controller.loadGame("briber");

        assertEquals(true, dungeonInfo.getEntities().stream().anyMatch(x -> x.getType().equals("mercenary")));
        assertEquals(true, dungeonInfo.getInventory().stream().anyMatch(x -> x.getType().equals("treasure")));

        Dungeon activeGame = controller.getActiveGame();

        Mercenary mercenary = (Mercenary)activeGame.getEntities().stream().filter(n -> n.getType().equals("mercenary")).findFirst().orElse(null);
        assertEquals(false, mercenary.isAlly());

        controller.interact("1");

        assertEquals(true, mercenary.isAlly());
    }

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
    public void testInteractOutofRange() {
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse dungeonInfo = controller.newGame("outofrangeinteract", "Standard");

        EntityResponse spawner = dungeonInfo.getEntities().stream().filter(n -> n.getType().equals("zombie_toast_spawner")).findFirst().orElse(null);
        String spawnerId = spawner.getId();
        assertThrows(InvalidActionException.class, () -> controller.interact(spawnerId));
    }

    @Test 
    public void testInteractSpawner() {
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse dungeonInfo = controller.newGame("spawnerinteract", "Standard");
        EntityResponse spawner = dungeonInfo.getEntities().stream().filter(n -> n.getType().equals("zombie_toast_spawner")).findFirst().orElse(null);
        String spawnerId = spawner.getId();
        assertDoesNotThrow(() -> controller.tick(null, Direction.DOWN));
        assertDoesNotThrow(() -> controller.interact(spawnerId));

        dungeonInfo =  controller.tick(null, Direction.LEFT);
        EntityResponse spawnerIfPresent = dungeonInfo.getEntities().stream().filter(n -> n.getType().equals("zombie_toast_spawner")).findFirst().orElse(null);
        assertEquals(null, spawnerIfPresent);
    }

    @Test
    public void testInteractInvalidEntity() {
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse dungeonInfo = controller.newGame("spawnerinteract", "Standard");
        EntityResponse sword = dungeonInfo.getEntities().stream().filter(n -> n.getType().equals("sword")).findFirst().orElse(null);
        String swordId = sword.getId();
        assertThrows(IllegalArgumentException.class, () -> controller.interact(swordId));
    }

    @Test
    public void testPotion() {
        DungeonManiaController controller = new DungeonManiaController();
        assertDoesNotThrow(() -> controller.newGame("potionUsed", "Standard"));
        DungeonResponse dungeonInfo = controller.tick(null, Direction.DOWN);
        EntityResponse player = dungeonInfo.getEntities().stream().filter(n -> n.getType().equals("player")).findFirst().orElse(null);
        assertEquals(new Position(1,2), player.getPosition());

        ItemResponse invincibilityPotion = dungeonInfo.getInventory().stream().filter(n -> n.getType().equals("invincibility_potion")).findFirst().orElse(null);
        assertDoesNotThrow(() -> controller.tick(invincibilityPotion.getId(), null));
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
        // assertDoesNotThrow(() -> controller.tick(null, Direction.DOWN));
        DungeonResponse dungeonInfo = controller.tick(null, Direction.DOWN);
        ItemResponse invincibilityPotion = dungeonInfo.getInventory().stream().filter(n -> n.getType().equals("invincibility_potion")).findFirst().orElse(null);

        assertDoesNotThrow(() -> controller.tick(invincibilityPotion.getId(), null));
        assertDoesNotThrow(() -> controller.loadGame("differentEntitiesSaveTester"));

    }

    @Test
    public void testInvalidMovement() {
        DungeonManiaController controller = new DungeonManiaController();
        assertDoesNotThrow(() -> controller.newGame("stuckEntity", "Standard"));
        assertDoesNotThrow(() -> controller.tick(null, Direction.DOWN));
        assertDoesNotThrow(() -> controller.tick(null, Direction.DOWN));
        assertDoesNotThrow(() -> controller.tick(null, Direction.DOWN));
    }

    @Test
    public void testInvalidMovement2() {
        DungeonManiaController controller = new DungeonManiaController();
        assertDoesNotThrow(() -> controller.newGame("stuckEntity2", "Standard"));
        assertDoesNotThrow(() -> controller.tick(null, Direction.DOWN));
        assertDoesNotThrow(() -> controller.tick(null, Direction.DOWN));
        assertDoesNotThrow(() -> controller.tick(null, Direction.DOWN));
    }

    @Test
    public void testInvalidMovementBlockedBoulder() {
        DungeonManiaController controller = new DungeonManiaController();
        assertDoesNotThrow(() -> controller.newGame("blockedBoulder", "Standard"));
        DungeonResponse dungeonInfo = controller.tick(null, Direction.DOWN);
        assertEquals(new ArrayList<>(), dungeonInfo.getInventory());

        assertEquals("player", dungeonInfo.getEntities().get(0).getType());
        assertEquals("boulder", dungeonInfo.getEntities().get(1).getType());
        assertEquals(new Position(0, 0), dungeonInfo.getEntities().get(0).getPosition());
        assertEquals(new Position(0, 1), dungeonInfo.getEntities().get(1).getPosition());

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

    
    
    @Test
    public void testZombieSpawnerBlocked() {
        DungeonManiaController controller = new DungeonManiaController();
        assertDoesNotThrow(() -> controller.newGame("blockedSpawner", "Standard"));
        assertDoesNotThrow(() -> controller.saveGame("blockedSpawner"));
        assertDoesNotThrow(() -> controller.loadGame("blockedSpawner"));
        

        assertDoesNotThrow(() -> controller.tick(null, Direction.DOWN));
        assertDoesNotThrow(() -> controller.tick(null, Direction.DOWN));
        assertDoesNotThrow(() -> controller.tick(null, Direction.DOWN));
        assertDoesNotThrow(() -> controller.tick(null, Direction.DOWN));
        assertDoesNotThrow(() -> controller.tick(null, Direction.DOWN));
        assertDoesNotThrow(() -> controller.tick(null, Direction.DOWN));
        assertDoesNotThrow(() -> controller.tick(null, Direction.DOWN));
        assertDoesNotThrow(() -> controller.tick(null, Direction.DOWN));
        assertDoesNotThrow(() -> controller.tick(null, Direction.DOWN));
        assertDoesNotThrow(() -> controller.tick(null, Direction.DOWN));
        assertDoesNotThrow(() -> controller.tick(null, Direction.DOWN));
        assertDoesNotThrow(() -> controller.tick(null, Direction.DOWN));
        DungeonResponse dungeonInfo =  controller.tick(null, Direction.DOWN);
        List<EntityResponse> entityResponses = dungeonInfo.getEntities();
        
        // check that no entities are spawned
        for (EntityResponse entityResponse : entityResponses) {
            assertTrue(!("Zombie").equals(entityResponse.getType()));
            assertTrue(!("zombie").equals(entityResponse.getType()));
        }
    }


    // bug fixing after milestone 2 below
    
    @Test
    public void testSpiderSpawn() {
        DungeonManiaController controller = new DungeonManiaController();
        assertDoesNotThrow(() -> controller.newGame("boulders", "Hard"));
        assertDoesNotThrow(() -> controller.saveGame("testingSpiders"));
        assertDoesNotThrow(() -> controller.loadGame("testingSpiders"));
        assertDoesNotThrow(() -> controller.tick(null, Direction.DOWN));
        assertDoesNotThrow(() -> controller.tick(null, Direction.DOWN));
        assertDoesNotThrow(() -> controller.tick(null, Direction.DOWN));
        assertDoesNotThrow(() -> controller.tick(null, Direction.DOWN));
        assertDoesNotThrow(() -> controller.tick(null, Direction.DOWN));
        assertDoesNotThrow(() -> controller.tick(null, Direction.DOWN));
        assertDoesNotThrow(() -> controller.tick(null, Direction.DOWN));
        assertDoesNotThrow(() -> controller.tick(null, Direction.DOWN));
        assertDoesNotThrow(() -> controller.tick(null, Direction.DOWN));
        assertDoesNotThrow(() -> controller.tick(null, Direction.DOWN));
        assertDoesNotThrow(() -> controller.tick(null, Direction.DOWN));
        assertDoesNotThrow(() -> controller.tick(null, Direction.DOWN));
        assertDoesNotThrow(() -> controller.tick(null, Direction.DOWN));
        assertDoesNotThrow(() -> controller.tick(null, Direction.DOWN));
        assertDoesNotThrow(() -> controller.tick(null, Direction.DOWN));
        assertDoesNotThrow(() -> controller.tick(null, Direction.DOWN));
        assertDoesNotThrow(() -> controller.tick(null, Direction.DOWN));
        DungeonResponse dungeonInfo =  controller.tick(null, Direction.DOWN);
        List<EntityResponse> entities = dungeonInfo.getEntities();
        assertEquals(true, entities.stream().anyMatch(x -> x.getType().equals("spider")));
        
        


    }

    @Test
    public void testKeepDoorOpen() {
        DungeonManiaController controller = new DungeonManiaController();
        assertDoesNotThrow(() -> controller.newGame("doors", "Hard"));
        assertDoesNotThrow(() -> controller.tick(null, Direction.RIGHT));
        assertDoesNotThrow(() -> controller.tick(null, Direction.UP));

        assertDoesNotThrow(() -> controller.tick(null, Direction.DOWN));
        assertDoesNotThrow(() -> controller.tick(null, Direction.RIGHT));

        
        Player player = controller.getActiveGame().getPlayer();
        assertEquals(new Position(3, 2), player.getPosition());
            

        
        assertDoesNotThrow(() -> controller.tick(null, Direction.RIGHT));
        assertDoesNotThrow(() -> controller.tick(null, Direction.LEFT));

        assertEquals(new Position(3, 2), player.getPosition());
    }

    @Test
    public void testTwoKeyPickup() {
        DungeonManiaController controller = new DungeonManiaController();
        assertDoesNotThrow(() -> controller.newGame("doors", "peaceful"));
        assertDoesNotThrow(() -> controller.tick(null, Direction.RIGHT));
        assertDoesNotThrow(() -> controller.tick(null, Direction.UP));

        DungeonResponse dungeonInfo = controller.tick(null, Direction.DOWN);
        List<ItemResponse> inventory = dungeonInfo.getInventory();
        assertEquals(true, inventory.stream().anyMatch(x -> x.getType().equals("key")));
        String keyId = null;

        for (ItemResponse item : inventory) {
            if (item.getType().equals("key")) {
                keyId = item.getId();
            }
        }
        
        assertDoesNotThrow(() -> controller.tick(null, Direction.DOWN));
        assertDoesNotThrow(() -> controller.tick(null, Direction.DOWN));
        assertDoesNotThrow(() -> controller.tick(null, Direction.DOWN));
        assertDoesNotThrow(() -> controller.tick(null, Direction.DOWN));

        dungeonInfo = controller.tick(null, Direction.DOWN);
        inventory = dungeonInfo.getInventory();
        assertEquals(true, inventory.stream().anyMatch(x -> x.getType().equals("key")));

        int countKeys = 0;
        for (ItemResponse item : inventory) {
            if (item.getType().equals("key")) {
                assertEquals(keyId, item.getId());
                countKeys++;
            }
        }

        assertEquals(1, countKeys);

    }


    @Test
    public void testOrGoalString() {
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse dungeonInfo = controller.newGame("orGoalTester", "standard");
        assertDoesNotThrow(() -> controller.tick(null, Direction.RIGHT));

        assertEquals(":treasure :exit ", dungeonInfo.getGoals());
        
    }

    @Test
    public void testDroppingBomb() {
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse dungeonInfo = controller.newGame("bombs", "standard");
        assertEquals(false, dungeonInfo.getInventory().stream().anyMatch(x -> x.getType().equals("bomb")));
 
        
        dungeonInfo = controller.tick(null, Direction.RIGHT);
        ItemResponse bomb = dungeonInfo.getInventory().stream().filter(n -> n.getType().equals("bomb")).findFirst().orElse(null);
        String bombId = bomb.getId();
        
        dungeonInfo = controller.tick(bombId, null);

        controller.getActiveGame().getEntities();

        assertEquals(false, dungeonInfo.getInventory().stream().anyMatch(x -> x.getType().equals("bomb")));

        assertDoesNotThrow(() -> controller.tick(null, Direction.RIGHT)); 
        dungeonInfo = controller.tick(null, Direction.LEFT); 
        assertEquals(true, dungeonInfo.getInventory().stream().anyMatch(x -> x.getType().equals("bomb")));
        dungeonInfo = controller.tick(bombId, null);
        assertEquals(false, dungeonInfo.getInventory().stream().anyMatch(x -> x.getType().equals("bomb")));
    }

    @Test
    public void testZombiesAndSpidersSpawnedStandard() {
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse dungeonInfo = controller.newGame("spawnerinteract", "standard");
        assertEquals(false, dungeonInfo.getEntities().stream().anyMatch(x -> x.getType().equals("spider")));
        assertEquals(false, dungeonInfo.getEntities().stream().anyMatch(x -> x.getType().equals("zombie_toast")));


        for (int i = 0; i < 19; i++) {
            assertDoesNotThrow(() -> controller.tick(null, Direction.RIGHT)); 
        }
        dungeonInfo = controller.tick(null, Direction.RIGHT);
        assertEquals(true, dungeonInfo.getEntities().stream().anyMatch(x -> x.getType().equals("spider")));
        assertEquals(true, dungeonInfo.getEntities().stream().anyMatch(x -> x.getType().equals("zombie_toast")));


    }

    @Test
    public void testZombiesAndSpidersSpawnedHard() {
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse dungeonInfo = controller.newGame("spawnerinteract", "hard");
        assertEquals(false, dungeonInfo.getEntities().stream().anyMatch(x -> x.getType().equals("spider")));
        assertEquals(false, dungeonInfo.getEntities().stream().anyMatch(x -> x.getType().equals("zombie_toast")));


        for (int i = 0; i < 14; i++) {
            assertDoesNotThrow(() -> controller.tick(null, Direction.RIGHT)); 
        }
        dungeonInfo = controller.tick(null, Direction.RIGHT);
        assertEquals(true, dungeonInfo.getEntities().stream().anyMatch(x -> x.getType().equals("spider")));
        assertEquals(true, dungeonInfo.getEntities().stream().anyMatch(x -> x.getType().equals("zombie_toast")));
    }

    @Test
    public void testHydraSpawning() {
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse dungeonInfo = controller.newGame("simple", "hard");
        assertEquals(false, dungeonInfo.getEntities().stream().anyMatch(x -> x.getType().equals("hydra")));

        for (int i = 0; i < 49; i++) {
            assertDoesNotThrow(() -> controller.tick(null, Direction.RIGHT)); 
        }
        dungeonInfo = controller.tick(null, Direction.RIGHT);
        assertEquals(true, dungeonInfo.getEntities().stream().anyMatch(x -> x.getType().equals("hydra")));
    }

    @Test
    public void testInvincibleStateSaved() {
        DungeonManiaController controller = new DungeonManiaController();
        assertDoesNotThrow(() -> controller.newGame("potionUsed", "standard"));
        Player player = controller.getActiveGame().getPlayer();
        String playerState = player.getCharacterState().getType();
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
        playerState = player.getCharacterState().getType();
        assertEquals("Invincible", playerState);
    }

    @Test
    public void testInvisibleStateSaved() {
        DungeonManiaController controller = new DungeonManiaController();
        assertDoesNotThrow(() -> controller.newGame("potionUsed", "standard"));
        Player player = controller.getActiveGame().getPlayer();
        String playerState = player.getCharacterState().getType();
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
        playerState = player.getCharacterState().getType();
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


}
package dungeonmania;


import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.items.InvincibilityPotion;
import dungeonmania.items.Item;
import dungeonmania.player.Player;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.response.models.ItemResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

import java.lang.IllegalArgumentException;
import java.util.List;
import java.util.ArrayList;

public class GameTickingTest {
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
    public void testZombieSpawnerBlocked() {
        DungeonManiaController controller = new DungeonManiaController();
        assertDoesNotThrow(() -> controller.newGame("blockedSpawner", "Standard"));
        assertDoesNotThrow(() -> controller.saveGame("blockedSpawner"));
        assertDoesNotThrow(() -> controller.loadGame("blockedSpawner"));
        
        for (int i = 0; i < 20; i++) {
            assertDoesNotThrow(() -> controller.tick(null, Direction.DOWN));
        }
        DungeonResponse dungeonInfo =  controller.tick(null, Direction.DOWN);
        List<EntityResponse> entityResponses = dungeonInfo.getEntities();
        
        assertEquals(false, entityResponses.stream().anyMatch(x -> x.getType().equals("zombie")));
    }

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
    public void testMaxFiveSpiders() {
        DungeonManiaController controller = new DungeonManiaController();
        assertDoesNotThrow(() -> controller.newGame("exit", "Hard"));

        for (int i = 0; i < 100; i++) {
            assertDoesNotThrow(() -> controller.tick(null, Direction.RIGHT));
        }
        DungeonResponse dungeonInfo =  controller.tick(null, Direction.RIGHT);
        assertEquals(5, dungeonInfo.getEntities().stream().filter(n -> n.getType().equals("spider")).count());

    }

    
    @Test
    public void testKeepDoorOpen() {
        DungeonManiaController controller = new DungeonManiaController();
        assertDoesNotThrow(() -> controller.newGame("doors", "hard"));
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
    public void testHydraSpawningHard() {
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse dungeonInfo = controller.newGame("simple", "hard");
        assertEquals(false, dungeonInfo.getEntities().stream().anyMatch(x -> x.getType().equals("hydra")));

        for (int i = 0; i < 49; i++) {
            assertDoesNotThrow(() -> controller.tick(null, Direction.RIGHT)); 
            assertEquals(false, dungeonInfo.getEntities().stream().anyMatch(x -> x.getType().equals("hydra")));
        }
        dungeonInfo = controller.tick(null, Direction.RIGHT);
        assertEquals(true, dungeonInfo.getEntities().stream().anyMatch(x -> x.getType().equals("hydra")));
        assertDoesNotThrow(() -> controller.saveGame("hydra"));
        assertDoesNotThrow(() -> controller.loadGame("hydra"));
        assertEquals(true, dungeonInfo.getEntities().stream().anyMatch(x -> x.getType().equals("hydra")));
        for (int i = 0; i < 49; i++) {
            assertDoesNotThrow(() -> controller.tick(null, Direction.RIGHT)); 
        }
    }

    @Test
    public void testHydraSpawningStandard() {
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse dungeonInfo = controller.newGame("simple", "standard");
        assertEquals(false, dungeonInfo.getEntities().stream().anyMatch(x -> x.getType().equals("hydra")));

        for (int i = 0; i < 200; i++) {
            assertDoesNotThrow(() -> controller.tick(null, Direction.RIGHT)); 
            assertEquals(false, dungeonInfo.getEntities().stream().anyMatch(x -> x.getType().equals("hydra")));
        }
        dungeonInfo = controller.tick(null, Direction.RIGHT);
        assertEquals(false, dungeonInfo.getEntities().stream().anyMatch(x -> x.getType().equals("hydra")));
        assertDoesNotThrow(() -> controller.saveGame("hydra"));
        assertDoesNotThrow(() -> controller.loadGame("hydra"));
        assertEquals(false, dungeonInfo.getEntities().stream().anyMatch(x -> x.getType().equals("hydra")));
        for (int i = 0; i < 49; i++) {
            assertDoesNotThrow(() -> controller.tick(null, Direction.RIGHT)); 
        }
    }

    @Test
    public void testHydraSpawningPeaceful() {
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse dungeonInfo = controller.newGame("simple", "peaceful");
        assertEquals(false, dungeonInfo.getEntities().stream().anyMatch(x -> x.getType().equals("hydra")));

        for (int i = 0; i < 200; i++) {
            assertDoesNotThrow(() -> controller.tick(null, Direction.RIGHT)); 
            assertEquals(false, dungeonInfo.getEntities().stream().anyMatch(x -> x.getType().equals("hydra")));
        }
        dungeonInfo = controller.tick(null, Direction.RIGHT);
        assertEquals(false, dungeonInfo.getEntities().stream().anyMatch(x -> x.getType().equals("hydra")));
        assertDoesNotThrow(() -> controller.saveGame("hydra"));
        assertDoesNotThrow(() -> controller.loadGame("hydra"));
        assertEquals(false, dungeonInfo.getEntities().stream().anyMatch(x -> x.getType().equals("hydra")));
        for (int i = 0; i < 49; i++) {
            assertDoesNotThrow(() -> controller.tick(null, Direction.RIGHT)); 
        }
    }

    @Test
    public void testInvincibleStateLastingSpecifiedTime() {
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

        player = controller.getActiveGame().getPlayer();
        playerState = player.getPlayerState().getType();
        assertEquals("Invincible", playerState);

        for (int i = 0; i < 10; i++) {
            player = controller.getActiveGame().getPlayer();
            playerState = player.getPlayerState().getType();
            assertEquals("Invincible", playerState);
            assertDoesNotThrow(() -> controller.tick(null, Direction.RIGHT)); 
        }

        player = controller.getActiveGame().getPlayer();
        playerState = player.getPlayerState().getType();
        assertEquals("Standard", playerState);
    }

    @Test
    public void testInvisibleStateLastingSpecifiedTime() {
        DungeonManiaController controller = new DungeonManiaController();
        assertDoesNotThrow(() -> controller.newGame("potionUsed", "standard"));
        Player player = controller.getActiveGame().getPlayer();
        String playerState = player.getPlayerState().getType();
        assertEquals("Standard", playerState);

        assertDoesNotThrow(() -> controller.tick(null, Direction.RIGHT)); 
        DungeonResponse dungeonInfo = controller.tick(null, Direction.DOWN); 
        assertEquals(true, dungeonInfo.getInventory().stream().anyMatch(x -> x.getType().equals("invisibility_potion")));

        Item invisibilityPotion = player.getInventory().getInventoryList().stream().filter(n -> n.getType().equals("invisibility_potion")).findFirst().orElse(null);
        String invisibilityPotionId = invisibilityPotion.getId();
        dungeonInfo = controller.tick(invisibilityPotionId, null);
        assertEquals(false, dungeonInfo.getInventory().stream().anyMatch(x -> x.getType().equals("invisibility_potion")));

        player = controller.getActiveGame().getPlayer();
        playerState = player.getPlayerState().getType();
        assertEquals("Invisible", playerState);

        for (int i = 0; i < 10; i++) {
            player = controller.getActiveGame().getPlayer();
            playerState = player.getPlayerState().getType();
            assertEquals("Invisible", playerState);
            assertDoesNotThrow(() -> controller.tick(null, Direction.RIGHT)); 
        }

        player = controller.getActiveGame().getPlayer();
        playerState = player.getPlayerState().getType();
        assertEquals("Standard", playerState);
    }

    @Test
    public void testBombExplodes() {
        DungeonManiaController controller = new DungeonManiaController();
        assertDoesNotThrow(() -> controller.newGame("bombExplode", "standard"));
        DungeonResponse dungeonInfo = controller.tick(null, Direction.RIGHT); 

        assertEquals(true, dungeonInfo.getInventory().stream().anyMatch(x -> x.getType().equals("bomb")));
        assertEquals(false, dungeonInfo.getEntities().stream().anyMatch(x -> x.getType().equals("bomb")));


        ItemResponse bomb = dungeonInfo.getInventory().stream().filter(n -> n.getType().equals("bomb")).findFirst().orElse(null);
        String bombId = bomb.getId();

        dungeonInfo = controller.tick(bombId, null); 
        assertEquals(false, dungeonInfo.getInventory().stream().anyMatch(x -> x.getType().equals("bomb")));

        assertDoesNotThrow(() -> controller.tick(null, Direction.LEFT)); 
        assertDoesNotThrow(() -> controller.tick(null, Direction.UP)); 
        assertDoesNotThrow(() -> controller.tick(null, Direction.UP)); 
        assertDoesNotThrow(() -> controller.tick(null, Direction.RIGHT)); 
        assertDoesNotThrow(() -> controller.tick(null, Direction.RIGHT)); 
        assertDoesNotThrow(() -> controller.tick(null, Direction.RIGHT)); 
        assertDoesNotThrow(() -> controller.tick(null, Direction.RIGHT)); 
        assertDoesNotThrow(() -> controller.tick(null, Direction.DOWN)); 
        assertDoesNotThrow(() -> controller.tick(null, Direction.DOWN)); 
        dungeonInfo = controller.tick(null, Direction.LEFT); 

        assertEquals(false, dungeonInfo.getEntities().stream().anyMatch(x -> x.getType().equals("bomb")));
        assertEquals(false, dungeonInfo.getEntities().stream().anyMatch(x -> x.getType().equals("wall")));
    }




}

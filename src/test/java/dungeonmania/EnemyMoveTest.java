package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;


import dungeonmania.util.Position;
import dungeonmania.util.Direction;
import dungeonmania.items.InvincibilityPotion;
import dungeonmania.items.InvisibilityPotion;
import dungeonmania.moving_entities.Mercenary;
import dungeonmania.moving_entities.Spider;
import dungeonmania.moving_entities.ZombieToast;
import dungeonmania.player.Inventory;
import dungeonmania.player.Player;
import dungeonmania.player.PlayerState;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.static_entities.Boulder;

import org.junit.jupiter.api.Test;

import java.sql.Time;
import java.util.List;

public class EnemyMoveTest {
    @Test
    public void testSpiderMovesClockwise() {
        // Initialise dungeon
        Dungeon dungeon = new Dungeon("Dungeon", "Standard", "1");

        // Create a player
        dungeon.addEntity(new Player(new Position(10, 10), dungeon));

        // Create spider
        Spider spider = new Spider(new Position(3, 3), dungeon);

        

        // Assert position is starting position
        assertEquals(new Position(3, 3), spider.getPosition());

        // Update spider's position
        spider.updatePosition();
        assertEquals(new Position(3, 2), spider.getPosition());
        spider.updatePosition();
        assertEquals(new Position(4, 2), spider.getPosition());
        spider.updatePosition();
        assertEquals(new Position(4, 3), spider.getPosition());
        spider.updatePosition();
        assertEquals(new Position(4, 4), spider.getPosition());
        spider.updatePosition();
        assertEquals(new Position(3, 4), spider.getPosition());
        spider.updatePosition();
        assertEquals(new Position(2, 4), spider.getPosition());
        spider.updatePosition();
        assertEquals(new Position(2, 3), spider.getPosition());
        spider.updatePosition();
        assertEquals(new Position(2, 2), spider.getPosition());
        spider.updatePosition();
        assertEquals(new Position(3, 2), spider.getPosition());
    }

    @Test
    public void testSpiderBlockedAtSpawn() {
        // Initialise dungeon
        Dungeon dungeon = new Dungeon("Dungeon", "Standard", "1");

        // Create a player
        dungeon.addEntity(new Player(new Position(10, 10), dungeon));

        // Create spider
        Spider spider = new Spider(new Position(3, 3), dungeon);

        // Assert position is starting position
        assertEquals(new Position(3, 3), spider.getPosition());

        // Create boulder
        new Boulder(new Position(3, 2), dungeon);

        // Spider cannot leave its sstarting position
        spider.updatePosition();
        assertEquals(new Position(3, 3), spider.getPosition());
    }

    @Test
    public void testSpiderReversesDirection() {
        // Initialise dungeon
        Dungeon dungeon = new Dungeon("Dungeon", "Standard", "1");

        // Create a player
        dungeon.addEntity(new Player(new Position(10, 10), dungeon));

        // Create spider
        Spider spider = new Spider(new Position(3, 3), dungeon);

        // Assert position is starting position
        assertEquals(new Position(3, 3), spider.getPosition());

        // Create boulder
        new Boulder(new Position(4, 2), dungeon);

        // Spider moves upwards
        spider.updatePosition();
        assertEquals(new Position(3, 2), spider.getPosition());
        // Spider reverses direction
        spider.updatePosition();
        assertEquals(new Position(2, 2), spider.getPosition());
        spider.updatePosition();
        assertEquals(new Position(2, 3), spider.getPosition());
        spider.updatePosition();
        assertEquals(new Position(2, 4), spider.getPosition());
        spider.updatePosition();
        assertEquals(new Position(3, 4), spider.getPosition());
        spider.updatePosition();
        assertEquals(new Position(4, 4), spider.getPosition());
        spider.updatePosition();
        assertEquals(new Position(4, 3), spider.getPosition());
        // Spider reverses direction
        spider.updatePosition();
        assertEquals(new Position(4, 4), spider.getPosition());
    }

    @Test
    public void spiderBlockedInBothDirections() {
        // Initialise dungeon
        Dungeon dungeon = new Dungeon("Dungeon", "Standard", "1");

        // Create a player
        dungeon.addEntity(new Player(new Position(10, 10), dungeon));

        // Create spider
        Spider spider = new Spider(new Position(3, 3), dungeon);

        // Assert position is starting position
        assertEquals(new Position(3, 3), spider.getPosition());

        // Create boulders
        new Boulder(new Position(4, 2), dungeon);
        new Boulder(new Position(2, 2), dungeon);

        // Spider moves up
        spider.updatePosition();
        assertEquals(new Position(3, 2), spider.getPosition());
        // Spider is blocked by boulders
        spider.updatePosition();
        assertEquals(new Position(3, 2), spider.getPosition());
        // Spider is blocked by boulders
        spider.updatePosition();
        assertEquals(new Position(3, 2), spider.getPosition());
    }

    @Test
    public void mercenaryMoveTowardsPlayer() {
        // Initialise dungeon
        Dungeon dungeon = new Dungeon("Dungeon", "Standard", "1");

        // Create player
        Player player = new Player(new Position(0, 0), dungeon);
        dungeon.addEntity(player);

        // Create mercenary
        Mercenary mercenary = new Mercenary(new Position(5, 0), dungeon);
        dungeon.addEntity(mercenary);

        mercenary.updatePosition();
        assertEquals(new Position(4,0), mercenary.getPosition());
        mercenary.updatePosition();
        assertEquals(new Position(3,0), mercenary.getPosition());
        mercenary.updatePosition();
        assertEquals(new Position(2,0), mercenary.getPosition());
    }

    @Test
    public void mercenaryMoveFromPlayer() {
        // Initialise dungeon
        Dungeon dungeon = new Dungeon("Dungeon", "Standard", "1");

        // Create player
        Player player = new Player(new Position(0, 0), dungeon);
        dungeon.addEntity(player);
        Inventory inv = player.getInventory();
        InvincibilityPotion i1 = new InvincibilityPotion(new Position(0, 0), player.getDungeon());
        inv.add(i1);
        player.useItem("invincibility_potion");
        PlayerState state = player.getPlayerState();
        assertEquals(state.getType(), "Invincible");

        // Create mercenary
        Mercenary mercenary = new Mercenary(new Position(5, 0), dungeon);
        dungeon.addEntity(mercenary);

        mercenary.updatePosition();
        assertEquals(new Position(6,0), mercenary.getPosition());
        mercenary.updatePosition();
        assertEquals(new Position(7,0), mercenary.getPosition());
        mercenary.updatePosition();
        assertEquals(new Position(8,0), mercenary.getPosition());
    }

    @Test
    public void zombieMoveFromPlayer() {
        // Initialise dungeon
        Dungeon dungeon = new Dungeon("Dungeon", "Standard", "1");

        // Create player
        Player player = new Player(new Position(0, 0), dungeon);
        dungeon.addEntity(player);
        Inventory inv = player.getInventory();
        InvincibilityPotion i1 = new InvincibilityPotion(new Position(0, 0), player.getDungeon());
        inv.add(i1);
        player.useItem("invincibility_potion");
        PlayerState state = player.getPlayerState();
        assertEquals(state.getType(), "Invincible");

        // Create mercenary
        ZombieToast zombie = new ZombieToast(new Position(5, 0), dungeon);
        dungeon.addEntity(zombie);

        zombie.updatePosition();
        assertEquals(new Position(6,0), zombie.getPosition());
        zombie.updatePosition();
        assertEquals(new Position(7,0), zombie.getPosition());
        zombie.updatePosition();
        assertEquals(new Position(8,0), zombie.getPosition());
    }

    @Test
    public void spiderMoveFromPlayer() {
        // Initialise dungeon
        Dungeon dungeon = new Dungeon("Dungeon", "Standard", "1");

        // Create player
        Player player = new Player(new Position(0, 0), dungeon);
        dungeon.addEntity(player);
        Inventory inv = player.getInventory();
        InvincibilityPotion i1 = new InvincibilityPotion(new Position(0, 0), player.getDungeon());
        inv.add(i1);
        player.useItem("invincibility_potion");
        PlayerState state = player.getPlayerState();
        assertEquals(state.getType(), "Invincible");

        // Create mercenary
        Spider spider = new Spider(new Position(5, 0), dungeon);
        dungeon.addEntity(spider);

        spider.updatePosition();
        assertEquals(new Position(6,0), spider.getPosition());
        spider.updatePosition();
        assertEquals(new Position(7,0), spider.getPosition());
        spider.updatePosition();
        assertEquals(new Position(8,0), spider.getPosition());
    }

    @Test
    public void mercenaryDoesNotMove() {
        // Initialise dungeon
        Dungeon dungeon = new Dungeon("Dungeon", "Standard", "1");

        // Create player
        Player player = new Player(new Position(0, 0), dungeon);
        dungeon.addEntity(player);
        Inventory inv = player.getInventory();
        InvisibilityPotion i1 = new InvisibilityPotion(new Position(0, 0), player.getDungeon());
        inv.add(i1);
        player.useItem("invisibility_potion");
        PlayerState state = player.getPlayerState();
        assertEquals(state.getType(), "Invisible");

        // Create mercenary
        Mercenary mercenary = new Mercenary(new Position(5, 0), dungeon);
        dungeon.addEntity(mercenary);

        mercenary.updatePosition();
        assertEquals(new Position(5,0), mercenary.getPosition());
        mercenary.updatePosition();
        assertEquals(new Position(5,0), mercenary.getPosition());
        mercenary.updatePosition();
        assertEquals(new Position(5,0), mercenary.getPosition());
    }

    @Test
    public void dijkstrasMovement() {
        // Create a controller
        DungeonManiaController controller = new DungeonManiaController();

        // Create a new game
        DungeonResponse dungeonInfo = controller.newGame("advanced-2", "Standard");

        // Get player entity
        EntityResponse player = null;
        player = dungeonInfo.getEntities().stream().filter(n -> n.getType().equals("player")).findFirst().orElse(null);

        // Assert player location is 1,1
        assertEquals(new Position(1, 1), player.getPosition());

        // Get mercenary entity
        EntityResponse mercenary = null;
        mercenary = dungeonInfo.getEntities().stream().filter(n -> n.getType().equals("mercenary")).findFirst().orElse(null);

        // Assert mercenary location is 3,5
        assertEquals(new Position(3, 5), mercenary.getPosition());

        // Move character into wall and position remains the same
        dungeonInfo = controller.tick(null, Direction.UP);
        // Assert player location is 1,1
        player = dungeonInfo.getEntities().stream().filter(n -> n.getType().equals("player")).findFirst().orElse(null);
        assertEquals(new Position(1, 1), player.getPosition());
        // Assert mercenary location updated
        mercenary = dungeonInfo.getEntities().stream().filter(n -> n.getType().equals("mercenary")).findFirst().orElse(null);
        assertEquals(new Position(2, 5), mercenary.getPosition());

        // Move character into wall and position remains the same
        dungeonInfo = controller.tick(null, Direction.LEFT);
        // Assert player location is 1,1
        player = dungeonInfo.getEntities().stream().filter(n -> n.getType().equals("player")).findFirst().orElse(null);
        assertEquals(new Position(1, 1), player.getPosition());
        // Assert mercenary location updated
        mercenary = dungeonInfo.getEntities().stream().filter(n -> n.getType().equals("mercenary")).findFirst().orElse(null);
        assertEquals(new Position(1, 5), mercenary.getPosition());

        // Move character into wall and position remains the same
        dungeonInfo = controller.tick(null, Direction.DOWN);
        // Assert player location is 0,0
        player = dungeonInfo.getEntities().stream().filter(n -> n.getType().equals("player")).findFirst().orElse(null);
        assertEquals(new Position(1, 2), player.getPosition());
        // Assert mercenary location updated
        mercenary = dungeonInfo.getEntities().stream().filter(n -> n.getType().equals("mercenary")).findFirst().orElse(null);
        assertEquals(new Position(1, 4), mercenary.getPosition());

        // Move character into wall and position remains the same
        dungeonInfo = controller.tick(null, Direction.RIGHT);
        // Assert player location is 0,0
        player = dungeonInfo.getEntities().stream().filter(n -> n.getType().equals("player")).findFirst().orElse(null);
        assertEquals(new Position(2, 2), player.getPosition());
        // Assert mercenary location updated
        mercenary = dungeonInfo.getEntities().stream().filter(n -> n.getType().equals("mercenary")).findFirst().orElse(null);
        assertEquals(new Position(1, 3), mercenary.getPosition());
        
        // Move character into wall and position remains the same
        dungeonInfo = controller.tick(null, Direction.UP);
        // Assert player location is 0,0
        player = dungeonInfo.getEntities().stream().filter(n -> n.getType().equals("player")).findFirst().orElse(null);
        assertEquals(new Position(2, 1), player.getPosition());
        // Assert mercenary location updated
        mercenary = dungeonInfo.getEntities().stream().filter(n -> n.getType().equals("mercenary")).findFirst().orElse(null);
        assertEquals(new Position(1, 2), mercenary.getPosition());
        
        // Move character into wall and position remains the same
        dungeonInfo = controller.tick(null, Direction.RIGHT);
        // Assert player location is 0,0
        player = dungeonInfo.getEntities().stream().filter(n -> n.getType().equals("player")).findFirst().orElse(null);
        assertEquals(new Position(3, 1), player.getPosition());
        // Assert mercenary location updated
        mercenary = dungeonInfo.getEntities().stream().filter(n -> n.getType().equals("mercenary")).findFirst().orElse(null);
        assertEquals(new Position(1, 1), mercenary.getPosition());
        
        // Move character into wall and position remains the same
        dungeonInfo = controller.tick(null, Direction.RIGHT);
        // Assert player location is 0,0
        player = dungeonInfo.getEntities().stream().filter(n -> n.getType().equals("player")).findFirst().orElse(null);
        assertEquals(new Position(4, 1), player.getPosition());
        // Assert mercenary location updated
        mercenary = dungeonInfo.getEntities().stream().filter(n -> n.getType().equals("mercenary")).findFirst().orElse(null);
        assertEquals(new Position(2, 1), mercenary.getPosition());
    }

    @Test
    public void merceneryThroughSwampTest() {
        // Initialise dungeon
        DungeonManiaController controller = new DungeonManiaController();

        // Create a new game
        DungeonResponse dungeonInfo = controller.newGame("swampAndEnemies", "Standard");

        // Get player entity
        EntityResponse player = null;
        player = dungeonInfo.getEntities().stream().filter(n -> n.getType().equals("player")).findFirst().orElse(null);

        // Assert player location is 1,1
        assertEquals(new Position(1, 1), player.getPosition());
        
        // Get mercenary entity
        EntityResponse mercenary = null;
        mercenary = dungeonInfo.getEntities().stream().filter(n -> n.getType().equals("mercenary")).findFirst().orElse(null);
        
        assertDoesNotThrow(()->Thread.sleep(1000));

        // Assert mercenary location
        assertEquals(new Position(1, 10), mercenary.getPosition());

        // Move character into wall and position remains the same
        dungeonInfo = controller.tick(null, Direction.RIGHT);
        
        assertDoesNotThrow(()->Thread.sleep(1000));

        // Get mercenary entity
        mercenary = dungeonInfo.getEntities().stream().filter(n -> n.getType().equals("mercenary")).findFirst().orElse(null);


        // Assert mercenary location
        assertEquals(new Position(1, 9), mercenary.getPosition());
        
        // Move character into wall and position remains the same
        dungeonInfo = controller.tick(null, Direction.RIGHT);

        assertDoesNotThrow(()->Thread.sleep(1000));
        
        // Get mercenary entity
        mercenary = dungeonInfo.getEntities().stream().filter(n -> n.getType().equals("mercenary")).findFirst().orElse(null);

        // Assert mercenary location
        assertEquals(new Position(1, 9), mercenary.getPosition());
        
        // Move character into wall and position remains the same
        dungeonInfo = controller.tick(null, Direction.RIGHT);

        assertDoesNotThrow(()->Thread.sleep(1000));
        
        // Get mercenary entity
        mercenary = dungeonInfo.getEntities().stream().filter(n -> n.getType().equals("mercenary")).findFirst().orElse(null);

        // Assert mercenary location
        assertEquals(new Position(1, 8), mercenary.getPosition());
    }

    @Test
    public void spiderThroughSwamp() {
        // Initialise dungeon
        DungeonManiaController controller = new DungeonManiaController();

        // Create a new game
        DungeonResponse dungeonInfo = controller.newGame("swampAndEnemies", "Standard");

        // Get player entity
        EntityResponse player = null;
        player = dungeonInfo.getEntities().stream().filter(n -> n.getType().equals("player")).findFirst().orElse(null);

        // Assert player location is 1,1
        assertEquals(new Position(1, 1), player.getPosition());

        // Get spider entity
        EntityResponse spider = null;
        spider = dungeonInfo.getEntities().stream().filter(n -> n.getType().equals("spider")).findFirst().orElse(null);

        // Assert spider location
        assertEquals(new Position(5, 5), spider.getPosition());

        // Move character into wall and position remains the same
        dungeonInfo = controller.tick(null, Direction.RIGHT);
        
        // Get spider entity
        spider = dungeonInfo.getEntities().stream().filter(n -> n.getType().equals("spider")).findFirst().orElse(null);

        // Assert spider location
        assertEquals(new Position(5, 4), spider.getPosition());
        
        // Move character into wall and position remains the same
        dungeonInfo = controller.tick(null, Direction.RIGHT);
        
        // Get spider entity
        spider = dungeonInfo.getEntities().stream().filter(n -> n.getType().equals("spider")).findFirst().orElse(null);

        // Assert spider location
        assertEquals(new Position(5, 4), spider.getPosition());
        
        // Move character into wall and position remains the same
        dungeonInfo = controller.tick(null, Direction.RIGHT);
        
        // Get spider entity
        spider = dungeonInfo.getEntities().stream().filter(n -> n.getType().equals("spider")).findFirst().orElse(null);

        // Assert spider location
        assertEquals(new Position(5, 4), spider.getPosition());
        
        // Move character into wall and position remains the same
        dungeonInfo = controller.tick(null, Direction.RIGHT);
        
        // Get spider entity
        spider = dungeonInfo.getEntities().stream().filter(n -> n.getType().equals("spider")).findFirst().orElse(null);

        // Assert spider location
        assertEquals(new Position(6, 4), spider.getPosition());
    }

}

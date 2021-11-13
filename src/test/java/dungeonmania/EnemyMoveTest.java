package dungeonmania;

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



public class EnemyMoveTest {
    @Test
    public void testSpiderMovesClockwise() {
        Dungeon dungeon = new Dungeon("Dungeon", "Standard", "1");

        dungeon.addEntity(new Player(new Position(10, 10), dungeon));

        Spider spider = new Spider(new Position(3, 3), dungeon);

        assertEquals(new Position(3, 3), spider.getPosition());

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
        Dungeon dungeon = new Dungeon("Dungeon", "Standard", "1");

        dungeon.addEntity(new Player(new Position(10, 10), dungeon));

        Spider spider = new Spider(new Position(3, 3), dungeon);

        assertEquals(new Position(3, 3), spider.getPosition());

        new Boulder(new Position(3, 2), dungeon);

        spider.updatePosition();
        assertEquals(new Position(3, 3), spider.getPosition());
    }

    @Test
    public void testSpiderReversesDirection() {
        Dungeon dungeon = new Dungeon("Dungeon", "Standard", "1");

        dungeon.addEntity(new Player(new Position(10, 10), dungeon));

        Spider spider = new Spider(new Position(3, 3), dungeon);

        assertEquals(new Position(3, 3), spider.getPosition());

        new Boulder(new Position(4, 2), dungeon);

        spider.updatePosition();
        assertEquals(new Position(3, 2), spider.getPosition());
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
        spider.updatePosition();
        assertEquals(new Position(4, 4), spider.getPosition());
    }

    @Test
    public void spiderBlockedInBothDirections() {
        Dungeon dungeon = new Dungeon("Dungeon", "Standard", "1");

        dungeon.addEntity(new Player(new Position(10, 10), dungeon));

        Spider spider = new Spider(new Position(3, 3), dungeon);

        assertEquals(new Position(3, 3), spider.getPosition());

        new Boulder(new Position(4, 2), dungeon);
        new Boulder(new Position(2, 2), dungeon);

        spider.updatePosition();
        assertEquals(new Position(3, 2), spider.getPosition());
        spider.updatePosition();
        assertEquals(new Position(3, 2), spider.getPosition());
        spider.updatePosition();
        assertEquals(new Position(3, 2), spider.getPosition());
    }

    @Test
    public void mercenaryMoveTowardsPlayer() {
        Dungeon dungeon = new Dungeon("Dungeon", "Standard", "1");

        Player player = new Player(new Position(0, 0), dungeon);
        dungeon.addEntity(player);

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
        Dungeon dungeon = new Dungeon("Dungeon", "Standard", "1");

        Player player = new Player(new Position(0, 0), dungeon);
        dungeon.addEntity(player);
        Inventory inv = player.getInventory();
        InvincibilityPotion i1 = new InvincibilityPotion(new Position(0, 0), player.getDungeon());
        inv.add(i1);
        player.useItem("invincibility_potion");
        PlayerState state = player.getPlayerState();
        assertEquals(state.getType(), "Invincible");

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
        Dungeon dungeon = new Dungeon("Dungeon", "Standard", "1");

        Player player = new Player(new Position(0, 0), dungeon);
        dungeon.addEntity(player);
        Inventory inv = player.getInventory();
        InvincibilityPotion i1 = new InvincibilityPotion(new Position(0, 0), player.getDungeon());
        inv.add(i1);
        player.useItem("invincibility_potion");
        PlayerState state = player.getPlayerState();
        assertEquals(state.getType(), "Invincible");

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
        Dungeon dungeon = new Dungeon("Dungeon", "Standard", "1");

        Player player = new Player(new Position(0, 0), dungeon);
        dungeon.addEntity(player);
        Inventory inv = player.getInventory();
        InvincibilityPotion i1 = new InvincibilityPotion(new Position(0, 0), player.getDungeon());
        inv.add(i1);
        player.useItem("invincibility_potion");
        PlayerState state = player.getPlayerState();
        assertEquals(state.getType(), "Invincible");

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
        Dungeon dungeon = new Dungeon("Dungeon", "Standard", "1");

        Player player = new Player(new Position(0, 0), dungeon);
        dungeon.addEntity(player);
        Inventory inv = player.getInventory();
        InvisibilityPotion i1 = new InvisibilityPotion(new Position(0, 0), player.getDungeon());
        inv.add(i1);
        player.useItem("invisibility_potion");
        PlayerState state = player.getPlayerState();
        assertEquals(state.getType(), "Invisible");

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
        DungeonManiaController controller = new DungeonManiaController();

        DungeonResponse dungeonInfo = controller.newGame("advanced-2", "Standard");

        EntityResponse player = null;
        player = dungeonInfo.getEntities().stream().filter(n -> n.getType().equals("player")).findFirst().orElse(null);

        assertEquals(new Position(1, 1), player.getPosition());

        EntityResponse mercenary = null;
        mercenary = dungeonInfo.getEntities().stream().filter(n -> n.getType().equals("mercenary")).findFirst().orElse(null);

        assertEquals(new Position(3, 5), mercenary.getPosition());

        dungeonInfo = controller.tick(null, Direction.UP);
        player = dungeonInfo.getEntities().stream().filter(n -> n.getType().equals("player")).findFirst().orElse(null);
        assertEquals(new Position(1, 1), player.getPosition());
        mercenary = dungeonInfo.getEntities().stream().filter(n -> n.getType().equals("mercenary")).findFirst().orElse(null);
        assertEquals(new Position(2, 5), mercenary.getPosition());

        dungeonInfo = controller.tick(null, Direction.LEFT);
        player = dungeonInfo.getEntities().stream().filter(n -> n.getType().equals("player")).findFirst().orElse(null);
        assertEquals(new Position(1, 1), player.getPosition());
        mercenary = dungeonInfo.getEntities().stream().filter(n -> n.getType().equals("mercenary")).findFirst().orElse(null);
        assertEquals(new Position(1, 5), mercenary.getPosition());

        dungeonInfo = controller.tick(null, Direction.DOWN);
        player = dungeonInfo.getEntities().stream().filter(n -> n.getType().equals("player")).findFirst().orElse(null);
        assertEquals(new Position(1, 2), player.getPosition());
        mercenary = dungeonInfo.getEntities().stream().filter(n -> n.getType().equals("mercenary")).findFirst().orElse(null);
        assertEquals(new Position(1, 4), mercenary.getPosition());

        dungeonInfo = controller.tick(null, Direction.RIGHT);
        player = dungeonInfo.getEntities().stream().filter(n -> n.getType().equals("player")).findFirst().orElse(null);
        assertEquals(new Position(2, 2), player.getPosition());
        mercenary = dungeonInfo.getEntities().stream().filter(n -> n.getType().equals("mercenary")).findFirst().orElse(null);
        assertEquals(new Position(1, 3), mercenary.getPosition());
        
        dungeonInfo = controller.tick(null, Direction.UP);
        player = dungeonInfo.getEntities().stream().filter(n -> n.getType().equals("player")).findFirst().orElse(null);
        assertEquals(new Position(2, 1), player.getPosition());
        mercenary = dungeonInfo.getEntities().stream().filter(n -> n.getType().equals("mercenary")).findFirst().orElse(null);
        assertEquals(new Position(1, 2), mercenary.getPosition());
        
        dungeonInfo = controller.tick(null, Direction.RIGHT);
        player = dungeonInfo.getEntities().stream().filter(n -> n.getType().equals("player")).findFirst().orElse(null);
        assertEquals(new Position(3, 1), player.getPosition());
        mercenary = dungeonInfo.getEntities().stream().filter(n -> n.getType().equals("mercenary")).findFirst().orElse(null);
        assertEquals(new Position(1, 1), mercenary.getPosition());
        
        dungeonInfo = controller.tick(null, Direction.RIGHT);
        player = dungeonInfo.getEntities().stream().filter(n -> n.getType().equals("player")).findFirst().orElse(null);
        assertEquals(new Position(4, 1), player.getPosition());
        mercenary = dungeonInfo.getEntities().stream().filter(n -> n.getType().equals("mercenary")).findFirst().orElse(null);
        assertEquals(new Position(2, 1), mercenary.getPosition());
    }

    @Test
    public void merceneryThroughSwampTest() {
        DungeonManiaController controller = new DungeonManiaController();

        DungeonResponse dungeonInfo = controller.newGame("swampAndEnemies", "Standard");

        EntityResponse player = null;
        player = dungeonInfo.getEntities().stream().filter(n -> n.getType().equals("player")).findFirst().orElse(null);

        assertEquals(new Position(1, 1), player.getPosition());
        
        EntityResponse mercenary = null;
        mercenary = dungeonInfo.getEntities().stream().filter(n -> n.getType().equals("mercenary")).findFirst().orElse(null);
        
        assertDoesNotThrow(()->Thread.sleep(1000));

        assertEquals(new Position(1, 10), mercenary.getPosition());

        dungeonInfo = controller.tick(null, Direction.RIGHT);
        
        assertDoesNotThrow(()->Thread.sleep(1000));

        mercenary = dungeonInfo.getEntities().stream().filter(n -> n.getType().equals("mercenary")).findFirst().orElse(null);

        assertEquals(new Position(1, 9), mercenary.getPosition());
        
        dungeonInfo = controller.tick(null, Direction.RIGHT);

        assertDoesNotThrow(()->Thread.sleep(1000));
        
        mercenary = dungeonInfo.getEntities().stream().filter(n -> n.getType().equals("mercenary")).findFirst().orElse(null);

        assertEquals(new Position(1, 9), mercenary.getPosition());
        
        dungeonInfo = controller.tick(null, Direction.RIGHT);

        assertDoesNotThrow(()->Thread.sleep(1000));
        
        mercenary = dungeonInfo.getEntities().stream().filter(n -> n.getType().equals("mercenary")).findFirst().orElse(null);

        assertEquals(new Position(1, 8), mercenary.getPosition());
    }

    @Test
    public void spiderThroughSwamp() {
        DungeonManiaController controller = new DungeonManiaController();

        DungeonResponse dungeonInfo = controller.newGame("swampAndEnemies", "Standard");

        EntityResponse player = null;
        player = dungeonInfo.getEntities().stream().filter(n -> n.getType().equals("player")).findFirst().orElse(null);

        assertEquals(new Position(1, 1), player.getPosition());

        EntityResponse spider = null;
        spider = dungeonInfo.getEntities().stream().filter(n -> n.getType().equals("spider")).findFirst().orElse(null);

        assertEquals(new Position(5, 5), spider.getPosition());

        dungeonInfo = controller.tick(null, Direction.RIGHT);
        
        spider = dungeonInfo.getEntities().stream().filter(n -> n.getType().equals("spider")).findFirst().orElse(null);

        assertEquals(new Position(5, 4), spider.getPosition());
        
        dungeonInfo = controller.tick(null, Direction.RIGHT);
        
        spider = dungeonInfo.getEntities().stream().filter(n -> n.getType().equals("spider")).findFirst().orElse(null);

        assertEquals(new Position(5, 4), spider.getPosition());
        
        dungeonInfo = controller.tick(null, Direction.RIGHT);
        
        spider = dungeonInfo.getEntities().stream().filter(n -> n.getType().equals("spider")).findFirst().orElse(null);

        assertEquals(new Position(5, 4), spider.getPosition());
        
        dungeonInfo = controller.tick(null, Direction.RIGHT);
        
        spider = dungeonInfo.getEntities().stream().filter(n -> n.getType().equals("spider")).findFirst().orElse(null);

        assertEquals(new Position(6, 4), spider.getPosition());
    }

}

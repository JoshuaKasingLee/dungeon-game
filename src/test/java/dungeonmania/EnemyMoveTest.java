package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;

import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;

import dungeonmania.util.Position;
import dungeonmania.util.Direction;

import org.junit.jupiter.api.Test;

import java.util.List;

public class EnemyMoveTest {
    @Test
    public void testSpiderMovesClockwise() {
        // Initialise dungeon
        Dungeon dungeon = new Dungeon("Dungeon", "Standard", "1");

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

        // Create boulder
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
    }
}

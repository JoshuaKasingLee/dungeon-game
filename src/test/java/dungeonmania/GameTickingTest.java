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

}

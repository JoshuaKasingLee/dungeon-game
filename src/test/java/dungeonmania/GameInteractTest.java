package dungeonmania;


import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import dungeonmania.exceptions.InvalidActionException;

import dungeonmania.moving_entities.Mercenary;

import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;

import dungeonmania.util.Direction;


import java.lang.IllegalArgumentException;


public class GameInteractTest {
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


}

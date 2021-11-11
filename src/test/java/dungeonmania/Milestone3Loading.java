package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.Test;

// import java.util.ArrayList;
// import java.util.List;
import java.util.Arrays;

import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.util.Position;
import dungeonmania.util.Direction;

public class Milestone3Loading {

    @Test
    public void testNewEntitiesNewGame() {

        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse dungeonInfo = controller.newGame("milestone3Entities", "Standard");
        assertEquals(true, dungeonInfo.getEntities().stream().anyMatch(x -> x.getType().equals("assassin")));
        assertEquals(true, dungeonInfo.getEntities().stream().anyMatch(x -> x.getType().equals("hydra")));
        assertEquals(true, dungeonInfo.getEntities().stream().anyMatch(x -> x.getType().equals("sun_stone")));
        assertEquals(true, dungeonInfo.getEntities().stream().anyMatch(x -> x.getType().equals("anduril")));

        assertDoesNotThrow(() -> controller.saveGame("testingNewEntities"));
        dungeonInfo = controller.loadGame("testingNewEntities");
        assertEquals(true, dungeonInfo.getEntities().stream().anyMatch(x -> x.getType().equals("assassin")));
        assertEquals(true, dungeonInfo.getEntities().stream().anyMatch(x -> x.getType().equals("hydra")));
        assertEquals(true, dungeonInfo.getEntities().stream().anyMatch(x -> x.getType().equals("sun_stone")));
        assertEquals(true, dungeonInfo.getEntities().stream().anyMatch(x -> x.getType().equals("anduril")));



    }
    
}

package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import dungeonmania.response.models.DungeonResponse;

import java.lang.IllegalArgumentException;
import java.util.Arrays;
import java.util.ArrayList;

public class DungeonTest {
    @Test
    public void testNewGame() {

        DungeonManiaController controller = new DungeonManiaController();

        DungeonResponse dungeonInfo = controller.newGame("maze", "Peaceful");
        assertEquals(dungeonInfo.getDungeonName(), "maze");
        assertEquals(dungeonInfo.getGoals(), "exit");
        assertEquals(dungeonInfo.getInventory(), new ArrayList<>());
        assertEquals(dungeonInfo.getBuildables(), new ArrayList<>());
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

    
}
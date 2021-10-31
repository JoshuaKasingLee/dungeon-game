package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.util.Direction;

import java.lang.IllegalArgumentException;
import java.util.List;

import dungeonmania.util.Position;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;



public class GoalTest {

    // Test ExitGoal
    @Test
    public void testExitGoal() {

        DungeonManiaController controller = new DungeonManiaController();

        DungeonResponse dungeonInfo = controller.newGame("exitGoalTester", "Peaceful");
        
        List<Entity> list = controller.getActiveGame().getEntities(new Position (1, 2));
        assertEquals("exit", list.get(0).getType());
        assertEquals(1, list.get(0).getGoalObservers().size());
        dungeonInfo = controller.tick(null, Direction.DOWN);
        // EntityResponse player = dungeonInfo.getEntities().stream().filter(n -> n.getType().equals("player")).findFirst().orElse(null);
        // EntityResponse exit = dungeonInfo.getEntities().stream().filter(n -> n.getType().equals("exit")).findFirst().orElse(null);
        // assertEquals(new Position(1, 2), player.getPosition());
        // assertEquals(new Position(1, 2), exit.getPosition());
        
        assertEquals("", dungeonInfo.getGoals());
    }


    @Test
    public void testBoulderGoal() {
        DungeonManiaController controller = new DungeonManiaController();

        DungeonResponse dungeonInfo = controller.newGame("boulderGoalTester", "Peaceful");

        dungeonInfo = controller.tick(null, Direction.UP);
        assertEquals(dungeonInfo.getGoals(), "");
        
    } 

    @Test
    public void testEnemyGoal() {
        DungeonManiaController controller = new DungeonManiaController();

        DungeonResponse dungeonInfo = controller.newGame("enemyGoalTester", "Peaceful");
        dungeonInfo = controller.tick(null , Direction.UP);
        assertEquals(dungeonInfo.getGoals(), "");
        
    }     
    
    @Test
    public void testTreasureGoal() {
        DungeonManiaController controller = new DungeonManiaController();

        DungeonResponse dungeonInfo = controller.newGame("treasureGoalTester", "Peaceful");
        dungeonInfo = controller.tick(null , Direction.UP);
        assertEquals(dungeonInfo.getGoals(), "");
        
    }     


    @Test
    public void testAndGoal() {
        DungeonManiaController controller = new DungeonManiaController();

        DungeonResponse dungeonInfo = controller.newGame("andGoalTester", "Peaceful");
        // assertTrue(dungeonInfo.getGoals().contains(":treasure") && dungeonInfo.getGoals().contains(":exit"));
        dungeonInfo = controller.tick(null , Direction.UP);
        // assertTrue(!dungeonInfo.getGoals().contains(":treasure") && dungeonInfo.getGoals().contains(":exit"));
        dungeonInfo = controller.tick(null , Direction.UP);
        assertEquals(dungeonInfo.getGoals(), "");

    } 

    @Test
    public void testOrGoal() {
        DungeonManiaController controller = new DungeonManiaController();

        DungeonResponse dungeonInfo = controller.newGame("orGoalTester", "Peaceful");
        assertTrue(dungeonInfo.getGoals().contains(":treasure") && dungeonInfo.getGoals().contains(":exit"));
        dungeonInfo = controller.tick(null , Direction.UP);
        assertEquals(dungeonInfo.getGoals(), "");

    } 

}
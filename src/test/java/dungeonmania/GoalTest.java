package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import dungeonmania.response.models.DungeonResponse;
import dungeonmania.util.Direction;

import java.lang.IllegalArgumentException;
import java.util.List;
import java.util.ArrayList;



public class GoalTest {

    // Test ExitGoal
    @Test
    public void testExitGoal() {

        DungeonManiaController controller = new DungeonManiaController();

        DungeonResponse dungeonInfo = controller.newGame("exitGoalTester", "Peaceful");

        controller.tick(null, Direction.DOWN);
        assertEquals(dungeonInfo.getGoals(), "");
    
    }


    @Test
    public void testBoulderGoal() {
        DungeonManiaController controller = new DungeonManiaController();

        DungeonResponse dungeonInfo = controller.newGame("boulderGoalTester", "Peaceful");

        controller.tick(null , Direction.UP);
        assertEquals(dungeonInfo.getGoals(), "");
        
    } 

    @Test
    public void testEnemyGoal() {
        DungeonManiaController controller = new DungeonManiaController();

        DungeonResponse dungeonInfo = controller.newGame("enemyGoalTester", "Peaceful");
        controller.tick(null , Direction.DOWN);
        assertEquals(dungeonInfo.getGoals(), "");
        
    }     
    
    @Test
    public void testTreasureGoal() {
        DungeonManiaController controller = new DungeonManiaController();

        DungeonResponse dungeonInfo = controller.newGame("treasureGoalTester", "Peaceful");
        controller.tick(null , Direction.DOWN);
        assertEquals(dungeonInfo.getGoals(), "");
        
    }     


    @Test
    public void testAndGoal() {
        DungeonManiaController controller = new DungeonManiaController();

        DungeonResponse dungeonInfo = controller.newGame("AndGoalTester", "Peaceful");
        assertTrue(dungeonInfo.getGoals().contains(":treasure") && dungeonInfo.getGoals().contains(":exit"));
        controller.tick(null , Direction.DOWN);
        assertTrue(!dungeonInfo.getGoals().contains(":treasure") && dungeonInfo.getGoals().contains(":exit"));
        controller.tick(null , Direction.DOWN);
        assertEquals(dungeonInfo.getGoals(), "");

    } 

    @Test
    public void testOrGoal() {
        DungeonManiaController controller = new DungeonManiaController();

        DungeonResponse dungeonInfo = controller.newGame("orGoalTester", "Peaceful");
        assertTrue(dungeonInfo.getGoals().contains(":treasure") && dungeonInfo.getGoals().contains(":exit"));
        controller.tick(null , Direction.DOWN);
        assertEquals(dungeonInfo.getGoals(), "");

    } 

}
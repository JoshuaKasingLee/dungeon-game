package dungeonmania;

import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.FileLoader;

import dungeonmania.response.models.EntityResponse;
import dungeonmania.response.models.ItemResponse;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.json.JSONObject;
import org.json.JSONArray;

import dungeonmania.util.Position;

public class DungeonManiaController {
    private static int dungeonIdCounter = 0; 
    public static synchronized String newDungeonId() {
        return String.valueOf(dungeonIdCounter++);
    }

    private Dungeon activeGame = null;

    public DungeonManiaController() {
    }

    public String getSkin() {
        return "default";
    }

    public String getLocalisation() {
        return "en_US";
    }

    public List<String> getGameModes() {
        return Arrays.asList("Standard", "Peaceful", "Hard");
    }

    /**
     * /dungeons
     * 
     * Done for you.
     */
    public static List<String> dungeons() {
        try {
            return FileLoader.listFileNamesInResourceDirectory("/dungeons");
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }


    public DungeonResponse newGame(String dungeonName, String gameMode) throws IllegalArgumentException {
    
        if (!dungeons().contains(dungeonName)) {
            throw new IllegalArgumentException("Invalid dungeonName");
        }
        if (!getGameModes().contains(gameMode)) {
            throw new IllegalArgumentException();
        }

        String dungeonId = newDungeonId();

        // TODO: Make the Dungeon Class
        activeGame = new Dungeon(dungeonName, gameMode, dungeonId);
        
        String fileContentsOutput = FileLoader.loadResourceFile("/dungeons/" + dungeonName + ".json");
        JSONObject dungeonObj = new JSONObject(fileContentsOutput);

        // Extract data from Json Object

        JSONArray entityList = dungeonObj.getJSONArray("entities");
        for (int i = 0; i < entityList.length(); i++) {
            String entityType = entityList.getJSONObject(i).getString("type");
            int xPosition = Integer.parseInt(entityList.getJSONObject(i).getString("x"));
            int yPosition = Integer.parseInt(entityList.getJSONObject(i).getString("y"));


            // TODO: Create entities based on type in JSON File using a bunch of if statements
            //create entity object and add it into activegame

            Entity currEntity = new Entity(new Position(xPosition, yPosition), gameMode);
            activeGame.addEntity(currEntity);
            // make a counter for the entityId in entity class similar to how we did it here
        }
        
        JSONObject goalCondition = dungeonObj.getJSONObject("goal-condition");
        if (isCompositeGoal(goalCondition.getString("goal"))) {
            
        }

        

        
        
        return new DungeonResponse(dungeonId, dungeonName, new ArrayList<EntityResponse>(), new ArrayList<ItemResponse>(), new ArrayList<String>(), "Temp");
    }
    
    public DungeonResponse saveGame(String name) throws IllegalArgumentException {


        // do we even need to test for invalid save name? you can always save?
        // if (allGames().contains(name)) {
        //     throw new IllegalArgumentException("Invalid saveName");
        // }
        // TODO: save activeGame as a JSON and solve it

        return new DungeonResponse(dungeonId, dungeonName, new ArrayList<EntityResponse>(), new ArrayList<ItemResponse>(), new ArrayList<String>(), "Temp");
    }

    public DungeonResponse loadGame(String name) throws IllegalArgumentException {
        if (!allGames().contains(name)) {
            throw new IllegalArgumentException("Invalid saveName");
        }

        
        // TODO: update ActiveGame
        return new DungeonResponse(dungeonId, dungeonName, new ArrayList<EntityResponse>(), new ArrayList<ItemResponse>(), new ArrayList<String>(), "Temp");
    }

    public List<String> allGames() {
        try {
            return FileLoader.listFileNamesInResourceDirectory("/saveFiles");
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }

    public DungeonResponse tick(String itemUsed, Direction movementDirection) throws IllegalArgumentException, InvalidActionException {
        return null;
    }

    public DungeonResponse interact(String entityId) throws IllegalArgumentException, InvalidActionException {
        return null;
    }

    public DungeonResponse build(String buildable) throws IllegalArgumentException, InvalidActionException {
        return null;
    }


    public boolean isCompositeGoal(String goal) {
        return (goal == "AND" || goal == "OR");
    }


}
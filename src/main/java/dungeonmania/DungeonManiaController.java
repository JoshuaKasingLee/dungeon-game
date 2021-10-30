package dungeonmania;

import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.FileLoader;

import dungeonmania.response.models.EntityResponse;
import dungeonmania.response.models.ItemResponse;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;
import org.json.JSONTokener;
import org.eclipse.jetty.io.NetworkTrafficListener;
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
        String fileContents = null;
        
        try {
            fileContents = FileLoader.loadResourceFile("/dungeons/" + dungeonName + ".json");
        } catch(IOException e) {
            System.out.println("File couldn't be loaded");
        }
        String dungeonId = newDungeonId();

        // Make the Dungeon Class
        activeGame = new Dungeon(dungeonName, gameMode, dungeonId);

        
        
        JSONObject dungeonObj = new JSONObject(fileContents);
        // Extract data from Json Object

        JSONArray entityList = dungeonObj.getJSONArray("entities");
        
        // Create a list of EntityResponse
        List<EntityResponse> entityResponses = new ArrayList<EntityResponse>();
        
        for (int i = 0; i < entityList.length(); i++) {
            String entityType = entityList.getJSONObject(i).getString("type");

            Position currPosition = new Position(entityList.getJSONObject(i).getInt("x"), entityList.getJSONObject(i).getInt("y"));


            // Create entities based on type in JSON File using a bunch of if statements
            //create entity object and add it into activegame
            Entity currEntity = null;
            int key;
            String colour;

            switch (entityType) {
                case "wall":
                    currEntity = new Wall(currPosition, activeGame);  
                    break;
                case "exit":
                    currEntity = new Exit(currPosition, activeGame);
                    break;
                case "boulder":
                    currEntity = new Boulder(currPosition, activeGame);
                    break;
                case "switch":
                    currEntity = new Switch(currPosition, activeGame);
                    break;
                case "door":
                    key = entityList.getJSONObject(i).getInt("key");
                    currEntity = new Door(currPosition, activeGame, key);
                    break;
                case "portal":
                    colour = entityList.getJSONObject(i).getString("colour");
                    currEntity = new Portal(currPosition, activeGame, colour);
                    break;
                case "zombie_toast_spawner":
                    currEntity = new ZombieToastSpawner(currPosition, activeGame);
                    break;
                case "one_ring":
                    currEntity = new OneRing(currPosition, activeGame);
                    break;
                case "spider":
                    currEntity = new Spider(currPosition, activeGame);
                    break;
                case "zombie_toast":
                    currEntity = new ZombieToast(currPosition, activeGame);
                    break;
                case "mercenary":
                    currEntity = new Mercenary(currPosition, activeGame);
                    break;
                case "treasure":
                    currEntity = new Treasure(currPosition, activeGame);
                    break;
                case "key":
                    key = entityList.getJSONObject(i).getInt("key");
                    currEntity = new Key(currPosition, activeGame, key);
                    break;
                case "health_position":
                    currEntity = new HealthPotion(currPosition, activeGame);
                    break;
                case "invincibility_potion":
                    currEntity = new InvincibilityPotion(currPosition, activeGame);
                    break;
                case "invisibility_potion":
                    currEntity = new InvisibilityPotion(currPosition, activeGame);
                    break;
                case "wood":
                    currEntity = new Wood(currPosition, activeGame);
                    break;
                case "arrow":
                    currEntity = new Arrow(currPosition, activeGame); 
                    break;
                case "bomb":
                    currEntity = new Bomb(currPosition, activeGame);
                    break;
                case "sword":
                    currEntity = new Sword(currPosition, activeGame);
                    break;
                // case "armour":
                //     currEntity = new Armour(activeGame);
                //     break;
                // case "bow":
                //     currEntity = new Bow(activeGame);
                //     break;
                // case "shield":
                //     currEntity = new Shield(activeGame);
                //     break;
                case "player":
                    currEntity = new Player(currPosition, activeGame);
                    break;
            }

            entityResponses.add(new EntityResponse(currEntity.getId(), currEntity.getType(), currEntity.getPosition(), currEntity.isInteractable()));

            // activeGame.addEntity(currEntity);
        }
        
        JSONObject goalCondition = dungeonObj.getJSONObject("goal-condition");

        GoalComponent overallGoal = extractAllGoals(goalCondition, activeGame);
        activeGame.setOverallGoal(overallGoal); 
        
        String goalString = "";
        for (GoalComponent simpleGoal : activeGame.getSimpleGoals()) {
            String simpleGoalString = simpleGoal.simpleGoalToString();
            if (!goalString.contains(simpleGoalString)) {
                goalString += simpleGoal.simpleGoalToString();
            }
        }
        
        return new DungeonResponse(dungeonId, dungeonName, entityResponses, new ArrayList<ItemResponse>(), new ArrayList<String>(), goalString);
    }
    
    public DungeonResponse saveGame(String name) {

        
        List<Entity> entities = activeGame.getEntities();

        JSONArray entitiesJSON = new JSONArray();
        // Map<String, Object> entityJSON = new Hashmap<String, Object>();
        // entityJSON.put("x", currEntity.getXPosition);
        // entityJSON.put("y", currEntity.getYPosition);
        // entityJSON.put("type", currEntity.findType());
        // entitiesJSON.put(entityJSON);
        List<EntityResponse> entityResponses = new ArrayList<EntityResponse>();
        
        for (int i = 0; i < entities.size(); i++) {
            Entity currEntity = entities.get(i);

            entityResponses.add(new EntityResponse(currEntity.getId(), currEntity.getType(), currEntity.getPosition(), currEntity.isInteractable()));

            
            String currType = currEntity.getType();
            Map<String, Object> entityData = new HashMap<String, Object>();
            entityData.put("x", currEntity.getXPosition());
            entityData.put("y", currEntity.getYPosition());
            entityData.put("type", currEntity.getType());
            entityData.put("entityId", currEntity.getId());


            switch (currType) {
                case "Portal":
                    entityData.put("colour", ((Portal)currEntity).getPortalColour());
                    break;
                case "Door": 
                    entityData.put("key", ((Door)currEntity).getKey());
                    break;
                case "Key":
                    entityData.put("key", ((Key)currEntity).getKey());
                    break;
                // MAYBE REMOVE LATER!!! COULD BE UNNECESSARY
                case "Switch":
                    entityData.put("hasBoulder", ((Switch)currEntity).hasBoulder());
                    break;
                case "ZombieToast": 
                    entityData.put("totalArmour", ((ZombieToast)currEntity).getArmour());
                    break;       
                case "Mercenary":
                    entityData.put("totalArmour", ((Mercenary)currEntity).getArmour());
                    entityData.put("ally", ((Mercenary)currEntity).isAlly());
                    break; 
                case "Player":
                    CharacterState characterState = ((Player)currEntity).getCharacterState();
                    String stateType = characterState.getType();
                    entityData.put("health", ((Player)currEntity).getHealth());
                    entityData.put("characterState", stateType);
                    if (stateType.equals("Invincible")) {
                        entityData.put("timeLeft", ((InvincibleState)characterState).getTimeLeft());
                    } else if (stateType.equals("Invisibile")) {
                        entityData.put("timeLeft", ((InvisibleState)characterState).getTimeLeft());
                    }
                    break;
                case "ZombieToastSpawner":
                    entityData.put("counter", ((ZombieToastSpawner)currEntity).getCounter());
                    break;
            }

            JSONObject entityJSON = new JSONObject(entityData);

            entitiesJSON.put(entityJSON);
        }


        
        // TODO: REFACTORY LAW OF DEMETER
        List<Item> items = activeGame.getInventory().getInventoryList();
        JSONArray itemsJSON = new JSONArray();

        List<ItemResponse> itemResponses = new ArrayList<ItemResponse>();

        for (int i = 0; i < items.size(); i++) {
            Entity currItem = items.get(i);

            itemResponses.add(new ItemResponse(currItem.getId(), currItem.getType()));


            String currType = currItem.getType();
            Map<String, Object> itemData = new HashMap<String, Object>();
            itemData.put("type", currItem.getType());
            itemData.put("entityId", currItem.getId());


            switch (currType) {
                case "Key":
                    itemData.put("key", ((Key)currItem).getKey());
                    break;
                case "Sword":  
                    itemData.put("usesLeft", ((Sword)currItem).getUsesLeft());
                    break;
                case "Armour":   
                    itemData.put("usesLeft", ((Armour)currItem).getUsesLeft());
                    break;
                case "Bow":
                    itemData.put("usesLeft", ((Bow)currItem).getUsesLeft());
                    break;
                case "Shield":
                    itemData.put("usesLeft", ((Shield)currItem).getUsesLeft());
                    break;
            }
            JSONObject itemJSON = new JSONObject(itemData);
            itemsJSON.put(itemJSON);

        }


        GoalComponent overallGoal = activeGame.getOverallGoal();
        JSONObject goalsJSON = overallGoal.toJSON();

        Map<String, Object> dungeonMap = new HashMap<String, Object>();
        dungeonMap.put("entities", entitiesJSON);
        dungeonMap.put("items", itemsJSON);
        dungeonMap.put("goal-condition", goalsJSON);
        dungeonMap.put("gamemode", activeGame.getGamemode().toString());
        String dungeonId = activeGame.getDungeonId();
        String dungeonName = activeGame.getDungeonName();
        dungeonMap.put("dungeonId", dungeonId);
        dungeonMap.put("dungeonName", dungeonName);

        JSONObject dungeonJSON = new JSONObject(dungeonMap);

        String dungeonSave = dungeonJSON.toString();

        try {
            PrintWriter fileLocation = new PrintWriter(new FileWriter("./src/main/resources/saveFiles/" + name + ".json"));
            fileLocation.print(dungeonSave);
            fileLocation.close();
        } catch (IOException e) {
            System.out.println("Working Directory = " + System.getProperty("user.dir"));
            System.out.println(e.toString());
            return null;
        }

        String goalString = "";
       

        for (GoalComponent simpleGoal : activeGame.getSimpleGoals()) {
            String simpleGoalString = simpleGoal.simpleGoalToString();
            if (!goalString.contains(simpleGoalString)) {
                goalString += simpleGoal.simpleGoalToString();
            }
        }

        List<String> buildables = activeGame.getInventory().getBuildables();
        return new DungeonResponse(dungeonId, dungeonName, entityResponses, itemResponses, buildables, goalString);
    }

    public DungeonResponse loadGame(String name) throws IllegalArgumentException {
        if (!allGames().contains(name)) {
            throw new IllegalArgumentException("Invalid saveName");
        }

        //TODO: Load Mercenary List in player

        String fileContents;
        try {
            fileContents = FileLoader.loadResourceFile("/saveFiles/" + name + ".json");
        } catch (IOException e) {
            System.out.println("Working Directory = " + System.getProperty("user.dir"));
            System.out.println(e.toString());
            return null;
        }
        JSONObject dungeonObj = new JSONObject(fileContents);




        // WE NEED TO CONVERT TO STRING!!
        String dungeonName = dungeonObj.getString("dungeonName");
        String dungeonId = dungeonObj.getString("dungeonId");
        String dungeonMode = dungeonObj.getString("gamemode");

        activeGame = new Dungeon(dungeonName, dungeonMode, dungeonId);
        JSONArray entityList = dungeonObj.getJSONArray("entities");
        List<EntityResponse> entityResponses = new ArrayList<EntityResponse>();

        for (int i = 0; i < entityList.length(); i++) {
            String entityType = entityList.getJSONObject(i).getString("type");

            // TODO: Create entities based on type in JSON File using a bunch of if statements
            //create entity object and add it into activegame
            Entity currEntity = null;
            int key;
            String colour;
            int durability;


            Position currPosition = new Position(entityList.getJSONObject(i).getInt("x"), entityList.getJSONObject(i).getInt("y"));

            switch (entityType) {
                case "Wall":
                    currEntity = new Wall(currPosition, activeGame);  
                    break;
                case "Exit":
                    currEntity = new Exit(currPosition, activeGame);
                    break;
                case "Boulder":
                    currEntity = new Boulder(currPosition, activeGame);
                    break;
                case "Switch":
                    currEntity = new Switch(currPosition, activeGame);
                    break;
                case "Door":
                    key = entityList.getJSONObject(i).getInt("key");
                    currEntity = new Door(currPosition, activeGame, key);
                    break;
                case "Portal":
                    colour = entityList.getJSONObject(i).getString("colour");
                    currEntity = new Portal(currPosition, activeGame, colour);
                    break;
                case "Zombie_toast_spawner":
                    currEntity = new ZombieToastSpawner(currPosition, activeGame, entityList.getJSONObject(i).getInt("counter"));
                    break;
                case "One_ring":
                    currEntity = new OneRing(currPosition, activeGame);
                    break;
                case "Spider":
                    currEntity = new Spider(currPosition, activeGame);
                    break;
                case "Zombie_toast":
                    currEntity = new ZombieToast(currPosition, activeGame, entityList.getJSONObject(i).getInt("totalArmour"));
                    break;
                case "Mercenary":
                    durability = entityList.getJSONObject(i).getInt("totalArmour");
                    boolean isAlly = entityList.getJSONObject(i).getBoolean("ally");
                    currEntity = new Mercenary(currPosition, activeGame, durability, isAlly);
                    break;
                case "Treasure":
                    currEntity = new Treasure(currPosition, activeGame);
                    break;
                case "Key":
                    key = entityList.getJSONObject(i).getInt("key");
                    currEntity = new Key(currPosition, activeGame, key);
                    break;
                case "Health_position":
                    currEntity = new HealthPotion(currPosition, activeGame);
                    break;
                case "Invincibility_potion":
                    currEntity = new InvincibilityPotion(currPosition, activeGame);
                    break;
                case "Invisibility_potion":
                    currEntity = new InvisibilityPotion(currPosition, activeGame);
                    break;
                case "Wood":
                    currEntity = new Wood(currPosition, activeGame);
                    break;
                case "Arrow":
                    currEntity = new Arrow(currPosition, activeGame); 
                    break;
                case "Bomb":
                    currEntity = new Bomb(currPosition, activeGame);
                    break;
                case "Sword":
                    currEntity = new Sword(currPosition, activeGame);
                    break;

                // case "Bow":
                //     currEntity = new Bow(activeGame);
                //     break;
                // case "Shield":
                //     currEntity = new Shield(activeGame);
                //     break;
                case "Player":
                    currEntity = new Player(currPosition, activeGame, entityList.getJSONObject(i).getInt("health"));
                    break;
            }
            currEntity.setId(entityList.getJSONObject(i).getString("entityId"));

            entityResponses.add(new EntityResponse(currEntity.getId(), currEntity.getType(), currEntity.getPosition(), currEntity.isInteractable()));

            // activeGame.addEntity(currEntity);
        }

        List<ItemResponse> itemResponses = new ArrayList<ItemResponse>();
        JSONArray itemList = dungeonObj.getJSONArray("items");
        
        for (int i = 0; i < itemList.length(); i++) {
            String itemType = itemList.getJSONObject(i).getString("type");
            
            // TODO: Create entities based on type in JSON File using a bunch of if statements
            //create entity object and add it into activegame
            Item currItem = null;
            int key;
            Position posPlaceholder = new Position(-1, -1);
            switch (itemType) {
                case "One_ring":
                    currItem = new OneRing(posPlaceholder, activeGame);
                    break;
                case "Treasure":
                    currItem = new Treasure(posPlaceholder, activeGame);
                    break;
                case "Key":
                    key = entityList.getJSONObject(i).getInt("key");
                    currItem = new Key(posPlaceholder, activeGame, key);
                    break;
                case "Health_position":
                    currItem = new HealthPotion(posPlaceholder, activeGame);
                    break;
                case "Invincibility_potion":
                    currItem = new InvincibilityPotion(posPlaceholder, activeGame);
                    break;
                case "Invisibility_potion":
                    currItem = new InvisibilityPotion(posPlaceholder, activeGame);
                    break;
                case "Wood":
                    currItem = new Wood(posPlaceholder, activeGame);
                    break;
                case "Arrow":
                    currItem = new Arrow(posPlaceholder, activeGame); 
                    break;
                case "Bomb":
                    currItem = new Bomb(posPlaceholder, activeGame);
                    break;
                case "Sword":
                    currItem = new Sword(posPlaceholder, activeGame);
                    ((Item) currItem).setUsesLeft(itemList.getJSONObject(i).getInt("usesLeft"));
                    break;
                case "Armour":
                    currItem = new Armour(activeGame, itemList.getJSONObject(i).getInt("usesLeft"));
                    break;
                case "Bow":
                    currItem = new Bow(activeGame);
                    currItem.setUsesLeft(itemList.getJSONObject(i).getInt("usesLeft"));
                    break;
                case "Shield":
                    currItem = new Shield(activeGame);
                    currItem.setUsesLeft(itemList.getJSONObject(i).getInt("usesLeft"));
                    break;
            }
            currItem.setId(entityList.getJSONObject(i).getString("entityId"));
            itemResponses.add(new ItemResponse(currItem.getId(), currItem.getType()));
            activeGame.moveToInventory(currItem);
            // activeGame.addEntity(currItem);
        }

        JSONObject goalCondition = dungeonObj.getJSONObject("goal-condition");
        GoalComponent overallGoal = extractAllGoals(goalCondition, activeGame);
        activeGame.setOverallGoal(overallGoal); 

        String goalString = "";
        for (GoalComponent simpleGoal : activeGame.getSimpleGoals()) {
            String simpleGoalString = simpleGoal.simpleGoalToString();
            if (!goalString.contains(simpleGoalString)) {
                goalString += simpleGoal.simpleGoalToString();
            }
        }

        List<String> buildables = activeGame.getInventory().getBuildables();
        
        // TODO: update ActiveGame
        return new DungeonResponse(dungeonId, dungeonName, entityResponses, itemResponses, buildables, goalString);
    }

    public List<String> allGames() {
        try {
            return FileLoader.listFileNamesInResourceDirectory("/saveFiles");
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }

    public DungeonResponse tick(String itemUsed, Direction movementDirection) throws IllegalArgumentException, InvalidActionException {
        //  Move character and all moving entities.
        
        Player player = activeGame.getPlayer();

        // Use item if appropriate. This does nothing if itemUsed is null or empty.
        // Throws exceptions where appropriate.
        if (itemUsed != null) {
            player.useItem(itemUsed);
        }
        
        List<EntityResponse> entityResponses = new ArrayList<EntityResponse>();
        List<Entity> entities = activeGame.getEntities();
        for (Entity entity: entities) {
            // Move all enemies
            if (entity instanceof Enemy) {
                ((Enemy) entity).updatePosition();
            }
            
            // Move character and update boulders accordingly.
            else if (movementDirection != null) {
                if (entity instanceof Player) {
                    ((Player)entity).move(movementDirection);
                    // move(movementDirection);
                } else if (entity instanceof Boulder) {
                    // ((Boulder) entity).update(movementDirection);
                }
            }

            entityResponses.add(new EntityResponse(entity.getId(), entity.getType(), entity.getPosition(), entity.isInteractable()));
        }
    

        List<Item> items = activeGame.getInventory().getInventoryList();        
        List<ItemResponse> itemResponses = new ArrayList<ItemResponse>();
        for (Item item : items) {
            itemResponses.add(new ItemResponse(item.getId(), item.getType()));
        }


        String goalString = "";

        for (GoalComponent simpleGoal : activeGame.getSimpleGoals()) {
            String simpleGoalString = simpleGoal.simpleGoalToString();
            if (!goalString.contains(simpleGoalString)) {
                goalString += simpleGoal.simpleGoalToString();
            }
        }

        List<String> buildables = activeGame.getInventory().getBuildables();
        return new DungeonResponse(activeGame.getDungeonId(), activeGame.getDungeonName(), entityResponses, itemResponses, buildables, goalString);

        
        // // Move character and update boulders accordingly.
        // if (movementDirection != null) {
            //     player.move(movementDirection);
            //     for (Entity entity: entities) {
                //         if (entity instanceof Boulder) {
        //             ((Boulder) entity).update(movementDirection);
        //         }
        //     }
        // }




        
        // // potion statechange??
        // switch (itemUsed) {
        //     case "invincibility_potion":
        //         InvincibleState invincibleState = new InvincibleState(player);
        //         activeGame.getPlayer().setCharacterState(invincibleState);
        //         break;
        //     case "invisibility_potion":
        //         InvisibleState invisibleState = new InvisibleState(player);
        //         activeGame.getPlayer().setCharacterState(invisibleState);
        //         break;
        //     case "health_potion":
        //         for (Item item : items) {
        //             if (item instanceof HealthPotion) {
        //                 ((HealthPotion) item).activate(player);
        //             }
        //         }
        //         break;
        //     case "bomb":
        //         for (Item item : items) {
        //             if (item instanceof Bomb) {
        //                 ((Bomb) item).explode();
        //             }
        //         }
        //         break;
        //     case null:
        //         break;
        //     default:
        //         throw new IllegalArgumentException("Invalid item used");
        // }
        


        // List<Entity> entities = activeGame.getEntities();
        // List<EntityResponse> entityResponses = new ArrayList<EntityResponse>();
        // for (Entity entity : entities) {
        //     if (entity instanceof Player) {
        //         ((Player) entity).move(movementDirection); 
        //     } else if (entity instanceof Enemy) {
        //         ((Enemy) entity).updatePosition(); 
        //     } else if (entity instanceof StaticEntity){
        //         // CHECK THIS: e.g. when boulder is blocked
        //         ((StaticEntity) entity).update(movementDirection);
        //     }
        //     entityResponses.add(new EntityResponse(entity.getId(), entity.getType(), entity.getPosition(), entity.isInteractable()));
        // }


        // return null;
    }

    public DungeonResponse interact(String entityId) throws IllegalArgumentException, InvalidActionException {

        List<Entity> entities = activeGame.getEntities();
        Player player = activeGame.getPlayer();


        boolean found = false;
        for (Entity entity : entities) {
            String Id = entity.getId();
            // Find the relevant entity.
            if (Id.equals(entityId)) {
                found = true;
                if (entity instanceof Mercenary) {
                    player.bribe((Mercenary)entity);
                } else if (entity instanceof ZombieToastSpawner) {
                    player.destroySpawner((ZombieToastSpawner)entity);
                }
            }
        }

        if (found == false) {
            throw new IllegalArgumentException("Entity Id is not valid.");
        }

        // TODO: Use helper functions to more easily create the dungeon response for the rest
        // for the above functions.
        String dungeonId = activeGame.getDungeonId();
        String dungeonName = activeGame.getDungeonName();
        List<EntityResponse> entityResponses = createEntityResponseList();
        List<ItemResponse> itemResponses = createItemResponseList();
        List<String> buildables = createBuildableList();
        String goalString = createGoalString();

        return new DungeonResponse(dungeonId, dungeonName, entityResponses, itemResponses, buildables, goalString);
        // return null;
    }

    public DungeonResponse build(String buildable) throws IllegalArgumentException, InvalidActionException {
        if (!buildable.equals("bow") && !buildable.equals("shield")) {
            throw new IllegalArgumentException("Can only build bow or shield!");
        }

        Inventory inventory = activeGame.getInventory();
        Player player = activeGame.getPlayer();
        
        if (buildable.equals("bow")) {
            inventory.craftBow(player);
        } else if (buildable.equals("shield")) {
            inventory.craftShield(player);
        }

        String dungeonId = activeGame.getDungeonId();
        String dungeonName = activeGame.getDungeonName();
        List<EntityResponse> entityResponses = createEntityResponseList();
        List<ItemResponse> itemResponses = createItemResponseList();
        List<String> buildables = createBuildableList();
        String goalString = createGoalString();

        return new DungeonResponse(dungeonId, dungeonName, entityResponses, itemResponses, buildables, goalString);
    }


    public boolean isCompositeGoal(String goal) {
        return (goal == "AND" || goal == "OR");
    }


    public GoalComponent extractAllGoals(JSONObject goalCondition, Dungeon activeGame) {
        GoalComponent overallGoal = null;
        switch (goalCondition.getString("goal")) {                
            case "AND":
                CompositeGoal newAndGoal = new AndGoal();

                JSONArray subgoalsAnd = goalCondition.getJSONArray("subgoals");
                for (int i = 0; i < subgoalsAnd.length(); i++) {
                    GoalComponent goal = extractAllGoals(subgoalsAnd.getJSONObject(i), activeGame);
                    newAndGoal.addSubgoal(goal);
                }

                overallGoal = newAndGoal;
                break;

            case "OR":
                CompositeGoal newOrGoal = new OrGoal();

                JSONArray subgoalsOr = goalCondition.getJSONArray("subgoals");
                for (int i = 0; i < subgoalsOr.length(); i++) {
                    GoalComponent goal = extractAllGoals(subgoalsOr.getJSONObject(i), activeGame);
                    newOrGoal.addSubgoal(goal);
                }

                overallGoal = newOrGoal;
                break;

            case "enemies":
                overallGoal = new EnemiesAndSpawnerGoal();
                activeGame.addSimpleGoals(overallGoal);
                // if (!goalString.contains("enemies")) {
                //     goalString += ":enemies ";
                // }
                break;
            case "treasure":
                overallGoal = new CollectTreasureGoal();
                activeGame.addSimpleGoals(overallGoal);
                // if (!goalString.contains("treasure")) {
                //     goalString += ":treasure ";
                // }
                break;
            case "boulders":
                overallGoal = new BoulderOnSwitchGoal();
                activeGame.addSimpleGoals(overallGoal);
                // if (!goalString.contains("boulder")) {
                //     goalString += ":boulder ";
                // }
                break;
            case "exit":
                overallGoal = new ExitGoal();
                activeGame.addSimpleGoals(overallGoal);
                // if (!goalString.contains("exit")) {
                //     goalString += ":exit ";
                // }
                break;  
        }
        return overallGoal;
    }

    // public String goalsToJSON(GoalComponent goal) {
    //     String goalJSONString = "";
    //     if (goal instanceof AndGoal) {

    //     }
    // }

    public List<EntityResponse> createEntityResponseList() {
        List<Entity> entities = activeGame.getEntities();
        List<EntityResponse> entityResponses = new ArrayList<EntityResponse>();
        for (Entity entity : entities) {
            entityResponses.add(new EntityResponse(entity.getId(), entity.getType(), entity.getPosition(), entity.isInteractable()));
        }
        return entityResponses;
    }

    public List<ItemResponse> createItemResponseList() {
        List<Item> items = activeGame.getInventory().getInventoryList();
        List<ItemResponse> itemResponses = new ArrayList<ItemResponse>();
        for (Item item : items) {
            itemResponses.add(new ItemResponse(item.getId(), item.getType()));
        }
        return itemResponses;
    }

    public String createGoalString() {
        String goalString = "";
        for (GoalComponent simpleGoal : activeGame.getSimpleGoals()) {
            String simpleGoalString = simpleGoal.simpleGoalToString();
            if (!goalString.contains(simpleGoalString)) {
                goalString += simpleGoal.simpleGoalToString();
            }
        }
        return goalString;
    }

    public List<String> createBuildableList() {
        return activeGame.getInventory().getBuildables();
    }


    /**
     * @return Dungeon return the activeGame
     */
    public Dungeon getActiveGame() {
        return activeGame;
    }

    /**
     * @param activeGame the activeGame to set
     */
    public void setActiveGame(Dungeon activeGame) {
        this.activeGame = activeGame;
    }

}
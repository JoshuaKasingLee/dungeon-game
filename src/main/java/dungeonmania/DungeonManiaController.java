package dungeonmania;

import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.goals.AndGoal;
import dungeonmania.goals.BoulderOnSwitchGoal;
import dungeonmania.goals.CollectTreasureGoal;
import dungeonmania.goals.CompositeGoal;
import dungeonmania.goals.EnemiesAndSpawnerGoal;
import dungeonmania.goals.ExitGoal;
import dungeonmania.goals.GoalComponent;
import dungeonmania.goals.OrGoal;
import dungeonmania.items.Anduril;
import dungeonmania.items.Armour;
import dungeonmania.items.Arrow;
import dungeonmania.items.Bomb;
import dungeonmania.items.Bow;
import dungeonmania.items.HealthPotion;
import dungeonmania.items.InvincibilityPotion;
import dungeonmania.items.InvisibilityPotion;
import dungeonmania.items.Item;
import dungeonmania.items.Key;
import dungeonmania.items.MidnightArmour;
import dungeonmania.items.OneRing;
import dungeonmania.items.Sceptre;
import dungeonmania.items.Shield;
import dungeonmania.items.SunStone;
import dungeonmania.items.Sword;
import dungeonmania.items.Treasure;
import dungeonmania.items.Wood;
import dungeonmania.moving_entities.Assassin;
import dungeonmania.moving_entities.Enemy;
import dungeonmania.moving_entities.Hydra;
import dungeonmania.moving_entities.MercControlledState;
import dungeonmania.moving_entities.Mercenary;
import dungeonmania.moving_entities.MercenaryState;
import dungeonmania.moving_entities.Spider;
import dungeonmania.moving_entities.ZombieToast;
import dungeonmania.player.Inventory;
import dungeonmania.player.InvincibleState;
import dungeonmania.player.InvisibleState;
import dungeonmania.player.Player;
import dungeonmania.player.PlayerState;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.FileLoader;

import dungeonmania.response.models.EntityResponse;
import dungeonmania.response.models.ItemResponse;
import dungeonmania.static_entities.Boulder;
import dungeonmania.static_entities.Door;
import dungeonmania.static_entities.Exit;
import dungeonmania.static_entities.Portal;
import dungeonmania.static_entities.StaticEntity;
import dungeonmania.static_entities.SwampTile;
import dungeonmania.static_entities.Switch;
import dungeonmania.static_entities.Wall;
import dungeonmania.static_entities.ZombieToastSpawner;


import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.json.JSONObject;
import org.json.JSONArray;


import dungeonmania.util.Position;


public class DungeonManiaController {
    private static int dungeonIdCounter = 0; 
    
    /** 
     * makes a unique id
     * @return String
     */
    public static synchronized String newDungeonId() {
        return String.valueOf(dungeonIdCounter++);
    }

    private Dungeon activeGame = null;

    public DungeonManiaController() {
    }

    
    /** 
     * @return String
     */
    public String getSkin() {
        return "default";
    }

    
    /** 
     * @return String
     */
    public String getLocalisation() {
        return "en_US";
    }

    
    /** 
     * return a list of gamemodes
     * @return List<String>
     */
    public List<String> getGameModes() {
        return Arrays.asList("standard","peaceful","hard");
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


    
    /** 
     * makes a new game with dungeonName and gameMode
     * @param dungeonName
     * @param gameMode
     * @return DungeonResponse
     * @throws IllegalArgumentException
     */
    public DungeonResponse newGame(String dungeonName, String gameMode) throws IllegalArgumentException {
    
        if (!dungeons().contains(dungeonName)) {
            throw new IllegalArgumentException("Invalid dungeonName");
        }

        String standardisedGamemodeString = gameMode.toLowerCase();
        if (!getGameModes().contains(standardisedGamemodeString)) {
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
        activeGame = new Dungeon(dungeonName, standardisedGamemodeString, dungeonId);

        
        
        JSONObject dungeonObj = new JSONObject(fileContents);
        // Extract data from Json Object

        JSONObject goalCondition = dungeonObj.getJSONObject("goal-condition");

        GoalComponent overallGoal = extractAllGoals(goalCondition, activeGame);
        activeGame.setOverallGoal(overallGoal); 

        JSONArray entityList = dungeonObj.getJSONArray("entities");
        
        for (int i = 0; i < entityList.length(); i++) {
            String entityType = entityList.getJSONObject(i).getString("type");

            Position currPosition = new Position(entityList.getJSONObject(i).getInt("x"), entityList.getJSONObject(i).getInt("y"));


            // Create entities based on type in JSON File using a bunch of if statements
            int key;
            String colour;

            switch (entityType) {
                case "wall":
                    new Wall(currPosition, activeGame);  
                    break;
                case "exit":
                    new Exit(currPosition, activeGame);
                    break;
                case "boulder":
                    new Boulder(currPosition, activeGame);
                    break;
                case "switch":
                    new Switch(currPosition, activeGame);
                    break;
                case "door":
                    key = entityList.getJSONObject(i).getInt("key");
                    new Door(currPosition, activeGame, key);
                    break;
                case "portal":
                    colour = entityList.getJSONObject(i).getString("colour");
                    new Portal(currPosition, activeGame, colour);
                    break;
                case "zombie_toast_spawner":
                    new ZombieToastSpawner(currPosition, activeGame);
                    break;
                case "swamp_tile":
                    new SwampTile(currPosition, activeGame, entityList.getJSONObject(i).getInt("movement_factor"));
                    break;
                case "one_ring":
                    new OneRing(currPosition, activeGame);
                    break;
                case "spider":
                    new Spider(currPosition, activeGame);
                    break;
                case "zombie_toast":
                    new ZombieToast(currPosition, activeGame);
                    break;
                case "mercenary":
                    // 10% chance that assassin is spawned in.
                    int randomOneDigitNum = new Random().nextInt(10);
                    if (randomOneDigitNum == 1) {
                        new Assassin(currPosition, activeGame);
                    } else {
                        new Mercenary(currPosition, activeGame);
                    }
                    break;
                case "treasure":
                    new Treasure(currPosition, activeGame);
                    break;
                case "key":
                    key = entityList.getJSONObject(i).getInt("key");
                    new Key(currPosition, activeGame, key);
                    break;
                case "health_potion":
                    new HealthPotion(currPosition, activeGame);
                    break;
                case "invincibility_potion":
                    new InvincibilityPotion(currPosition, activeGame);
                    break;
                case "invisibility_potion":
                    new InvisibilityPotion(currPosition, activeGame);
                    break;
                case "wood":
                    new Wood(currPosition, activeGame);
                    break;
                case "arrow":
                    new Arrow(currPosition, activeGame); 
                    break;
                case "bomb":
                    new Bomb(currPosition, activeGame);
                    break;
                case "sword":
                    new Sword(currPosition, activeGame);
                    break;
                case "player":
                    new Player(currPosition, activeGame);
                    break;
                case "assassin": 
                    new Assassin(currPosition, activeGame);
                    break;
                case "hydra":
                    new Hydra(currPosition, activeGame);
                    break;
                case "sun_stone":
                    new SunStone(currPosition, activeGame);
                    break;
                case "anduril":
                    new Anduril(currPosition, activeGame);
                    break;
            }

        }

        return createDungeonResponse();
        
    }
    

    public DungeonResponse generateDungeon(int xStart, int yStart, int xEnd, int yEnd, String gameMode) throws IllegalArgumentException {
        if (!getGameModes().contains(gameMode)) {
            throw new IllegalArgumentException();
        }

        String dungeonId = newDungeonId();
        activeGame = new Dungeon("PrimsMaze", gameMode, dungeonId);
        activeGame.randomizedPrims(50, 50, new Position(xStart, yStart), new Position(xEnd, yEnd));

        return createDungeonResponse();
    }
    
    /** 
     * saves a game to a filename
     * @param name
     * @return DungeonResponse
     */
    public DungeonResponse saveGame(String name) {

        
        List<Entity> entities = activeGame.getEntities();

        JSONArray entitiesJSON = new JSONArray();
        
        for (int i = 0; i < entities.size(); i++) {
            Entity currEntity = entities.get(i);
            
            String currType = currEntity.getType();
            Map<String, Object> entityData = new HashMap<String, Object>();
            entityData.put("x", currEntity.getXPosition());
            entityData.put("y", currEntity.getYPosition());
            entityData.put("type", currEntity.getType());
            entityData.put("entityId", currEntity.getId());


            switch (currType) {
                case "portal":
                    entityData.put("colour", ((Portal)currEntity).getPortalColour());
                    break;
                case "door": 
                    entityData.put("key", ((Door)currEntity).getKey());
                    break;
                case "door_unlocked":
                    entityData.put("key", ((Door)currEntity).getKey());
                    break;
                case "key":
                    entityData.put("key", ((Key)currEntity).getKey());
                    break;
                case "switch":
                    entityData.put("hasBoulder", ((Switch)currEntity).hasBoulder());
                    break;
                case "zombie_toast": 
                    entityData.put("totalArmour", ((ZombieToast)currEntity).getArmour());
                    entityData.put("slowed", ((ZombieToast)currEntity).getSlowed());
                    break;       
                case "mercenary":
                    entityData.put("totalArmour", ((Mercenary)currEntity).getArmour());
                    entityData.put("ally", ((Mercenary)currEntity).isAlly());
                    entityData.put("slowed", ((Mercenary)currEntity).getSlowed());
                    MercenaryState state = ((Mercenary)currEntity).getMercenaryState();
                    String mercState = state.getType();
                    entityData.put("mercState", mercState);
                    if (mercState.equals("MercControlled")) {
                        entityData.put("timeLeft", ((MercControlledState)state).getTimeLeft());
                        entityData.put("wasAlly", ((MercControlledState)state).isWasAlly());
                    }
                    break; 
                case "assassin":
                    entityData.put("totalArmour", ((Assassin)currEntity).getArmour());
                    entityData.put("ally", ((Assassin)currEntity).isAlly());
                    entityData.put("slowed", ((Assassin)currEntity).getSlowed());

                    MercenaryState assassinState = ((Assassin)currEntity).getMercenaryState();
                    String assState = assassinState.getType();
                    entityData.put("mercState", assState);
                    if (assState.equals("MercControlled")) {
                        entityData.put("timeLeft", ((MercControlledState)assassinState).getTimeLeft());
                        entityData.put("wasAlly", ((MercControlledState)assassinState).isWasAlly());
                    }
                    break;  
                case "player":
                    PlayerState characterState = ((Player)currEntity).getPlayerState();
                    String stateType = characterState.getType();
                    entityData.put("health", ((Player)currEntity).getHealth());
                    entityData.put("characterState", stateType);
                    entityData.put("teleported", ((Player)currEntity).getTeleported());
                    if (stateType.equals("Invincible")) {
                        entityData.put("timeLeft", ((InvincibleState)characterState).getTimeLeft());
                    } else if (stateType.equals("Invisible")) {
                        entityData.put("timeLeft", ((InvisibleState)characterState).getTimeLeft());
                    }
                    break;
                case "spider":
                    entityData.put("startingPositionx", ((Spider)currEntity).getStartingPosition().getX());
                    entityData.put("startingPositiony", ((Spider)currEntity).getStartingPosition().getY());
                    entityData.put("positionNumber", ((Spider)currEntity).getPositionNumber());
                    entityData.put("slowed", ((Spider)currEntity).getSlowed());
                    break;
                case "hydra": {
                    entityData.put("slowed", ((Hydra)currEntity).getSlowed());
                    break;
                }
                case "swamp_tile":
                    entityData.put("movement_factor", ((SwampTile)currEntity).getMovementFactor());
                    break;
            }

            JSONObject entityJSON = new JSONObject(entityData);

            entitiesJSON.put(entityJSON);
        }

        List<Item> items = activeGame.getInventory().getInventoryList();
        JSONArray itemsJSON = new JSONArray();

        for (int i = 0; i < items.size(); i++) {
            Entity currItem = items.get(i);

            String currType = currItem.getType();
            Map<String, Object> itemData = new HashMap<String, Object>();
            itemData.put("type", currItem.getType());
            itemData.put("entityId", currItem.getId());


            switch (currType) {
                case "key":
                    itemData.put("key", ((Key)currItem).getKey());
                    break;
                case "sword":  
                    itemData.put("usesLeft", ((Sword)currItem).getUsesLeft());
                    break;
                case "armour":   
                    itemData.put("usesLeft", ((Armour)currItem).getUsesLeft());
                    break;
                case "bow":
                    itemData.put("usesLeft", ((Bow)currItem).getUsesLeft());
                    break;
                case "shield":
                    itemData.put("usesLeft", ((Shield)currItem).getUsesLeft());
                    break;
                case "midnight_armour":
                    itemData.put("usesLeft", ((MidnightArmour)currItem).getUsesLeft());
                    break;
                case "anduril":
                    itemData.put("usesLeft", ((Anduril)currItem).getUsesLeft());
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
        dungeonMap.put("counter", activeGame.getCounter());

        JSONObject dungeonJSON = new JSONObject(dungeonMap);

        String dungeonSave = dungeonJSON.toString();

        
        try {
            Path savePath = Files.createDirectories(Paths.get("saveFiles"));

            PrintWriter fileLocation = new PrintWriter(new FileWriter(savePath.resolve(name + ".json").toFile()));
            fileLocation.print(dungeonSave);
            fileLocation.close();

        } catch (IOException e) {
            System.out.println("Working Directory = " + System.getProperty("user.dir"));
            System.out.println(e.toString());
            return null;
        }

        return createDungeonResponse();
    }

    
    /** 
     * loads a game from filename
     * @param name
     * @return DungeonResponse
     * @throws IllegalArgumentException
     */
    public DungeonResponse loadGame(String name) throws IllegalArgumentException {
        if (!allGames().contains(name)) {
            throw new IllegalArgumentException("Invalid saveName");
        }
        
        String fileContents;
        try {
            fileContents = new String(Files.readAllBytes(Paths.get("saveFiles/" + name + ".json")));
        } catch (Exception e) {
            System.out.println("Working Directory = " + System.getProperty("user.dir"));
            System.out.println(e.toString());
            return null;
        }
        JSONObject dungeonObj = new JSONObject(fileContents);
        
        String dungeonName = dungeonObj.getString("dungeonName");
        String dungeonId = dungeonObj.getString("dungeonId");
        String dungeonMode = dungeonObj.getString("gamemode");
        int counter = dungeonObj.getInt("counter");

        activeGame = new Dungeon(dungeonName, dungeonMode, dungeonId);
        activeGame.setCounter(counter);
        
        JSONObject goalCondition = dungeonObj.getJSONObject("goal-condition");
        GoalComponent overallGoal = extractAllGoals(goalCondition, activeGame);
        activeGame.setOverallGoal(overallGoal); 

        JSONArray entityList = dungeonObj.getJSONArray("entities");

        for (int i = 0; i < entityList.length(); i++) {
            String entityType = entityList.getJSONObject(i).getString("type");
            Entity currEntity = null;
            int key;
            String colour;
            int durability;

            Position currPosition = new Position(entityList.getJSONObject(i).getInt("x"), entityList.getJSONObject(i).getInt("y"));

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
                case "door_unlocked":
                    key = entityList.getJSONObject(i).getInt("key");
                    currEntity = new Door(currPosition, activeGame, key);
                    ((Door)currEntity).setLocked(false);
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
                    currEntity = new Spider(currPosition, activeGame, new Position(entityList.getJSONObject(i).getInt("startingPositionx"), entityList.getJSONObject(i).getInt("startingPositiony")),
                        entityList.getJSONObject(i).getInt("positionNumber"));
                    ((Spider)currEntity).setSlowed(entityList.getJSONObject(i).getInt("slowed"));
                    break;
                case "zombie_toast":
                    currEntity = new ZombieToast(currPosition, activeGame, entityList.getJSONObject(i).getInt("totalArmour"));
                    ((ZombieToast)currEntity).setSlowed(entityList.getJSONObject(i).getInt("slowed"));
                    break;
                case "mercenary":
                    durability = entityList.getJSONObject(i).getInt("totalArmour");
                    boolean isAlly = entityList.getJSONObject(i).getBoolean("ally");
                    currEntity = new Mercenary(currPosition, activeGame, durability, isAlly);
                    ((Mercenary)currEntity).setSlowed(entityList.getJSONObject(i).getInt("slowed"));

                    Mercenary mercenary = (Mercenary)currEntity;
                    String mercState = entityList.getJSONObject(i).getString("mercState");

                    if (mercState.equals("MercControlled")) {
                        mercenary.setMercenaryState(new MercControlledState(mercenary, entityList.getJSONObject(i).getInt("timeLeft")));
                    }
                    break;
                case "assassin":
                    durability = entityList.getJSONObject(i).getInt("totalArmour");
                    isAlly = entityList.getJSONObject(i).getBoolean("ally");
                    currEntity = new Assassin(currPosition, activeGame, durability, isAlly);
                    ((Assassin)currEntity).setSlowed(entityList.getJSONObject(i).getInt("slowed"));

                    Assassin assassin = (Assassin)currEntity;
                    String assState = entityList.getJSONObject(i).getString("mercState");

                    if (assState.equals("MercControlled")) {
                        assassin.setMercenaryState(new MercControlledState(assassin, entityList.getJSONObject(i).getInt("timeLeft")));
                    }
                    break;
                case "treasure":
                    currEntity = new Treasure(currPosition, activeGame);
                    break;
                case "key":
                    key = entityList.getJSONObject(i).getInt("key");
                    currEntity = new Key(currPosition, activeGame, key);
                    break;
                case "health_potion":
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
                case "player":
                    currEntity = new Player(currPosition, activeGame, entityList.getJSONObject(i).getInt("health"),entityList.getJSONObject(i).getBoolean("teleported"));
                    Player player = (Player)currEntity;
                    String characterState = entityList.getJSONObject(i).getString("characterState");
                    if (characterState.equals("Invincible")) {
                        player.setCharacterState(new InvincibleState(player, entityList.getJSONObject(i).getInt("timeLeft")));
                    } else if (characterState.equals("Invisible")) {
                        player.setCharacterState(new InvisibleState(player, entityList.getJSONObject(i).getInt("timeLeft")));
                    }
                    break;
                case "hydra":
                    currEntity = new Hydra(currPosition, activeGame);
                    ((Hydra)currEntity).setSlowed(entityList.getJSONObject(i).getInt("slowed"));
                    break;
                case "sun_stone":
                    currEntity = new SunStone(currPosition, activeGame);
                    break;
                case "anduril":
                    currEntity = new Anduril(currPosition, activeGame);
                    break;
                case "swamp_tile":
                    currEntity = new SwampTile(currPosition, activeGame, entityList.getJSONObject(i).getInt("movement_factor") );
                    break;
            }
            currEntity.setId(entityList.getJSONObject(i).getString("entityId"));
        }

        JSONArray itemList = dungeonObj.getJSONArray("items");
        
        for (int i = 0; i < itemList.length(); i++) {
            String itemType = itemList.getJSONObject(i).getString("type");
            Item currItem = null;
            int key;
            Position posPlaceholder = new Position(-1, -1);
            switch (itemType) {
                case "one_ring":
                    currItem = new OneRing(posPlaceholder, activeGame);
                    break;
                case "treasure":
                    currItem = new Treasure(posPlaceholder, activeGame);
                    break;
                case "key":
                    key = itemList.getJSONObject(i).getInt("key");
                    currItem = new Key(posPlaceholder, activeGame, key);
                    break;
                case "health_potion":
                    currItem = new HealthPotion(posPlaceholder, activeGame);
                    break;
                case "invincibility_potion":
                    currItem = new InvincibilityPotion(posPlaceholder, activeGame);
                    break;
                case "invisibility_potion":
                    currItem = new InvisibilityPotion(posPlaceholder, activeGame);
                    break;
                case "wood":
                    currItem = new Wood(posPlaceholder, activeGame);
                    break;
                case "arrow":
                    currItem = new Arrow(posPlaceholder, activeGame); 
                    break;
                case "bomb":
                    currItem = new Bomb(posPlaceholder, activeGame);
                    break;
                case "sword":
                    currItem = new Sword(posPlaceholder, activeGame);
                    ((Item) currItem).setUsesLeft(itemList.getJSONObject(i).getInt("usesLeft"));
                    break;
                case "armour":
                    currItem = new Armour(activeGame, itemList.getJSONObject(i).getInt("usesLeft"));
                    break;
                case "bow":
                    currItem = new Bow(activeGame);
                    currItem.setUsesLeft(itemList.getJSONObject(i).getInt("usesLeft"));
                    break;
                case "shield":
                    currItem = new Shield(activeGame);
                    currItem.setUsesLeft(itemList.getJSONObject(i).getInt("usesLeft"));
                    break;
                case "sun_stone":
                    currItem = new SunStone(posPlaceholder, activeGame);
                    break;
                case "anduril":
                    currItem = new Anduril(posPlaceholder, activeGame);
                    currItem.setUsesLeft(itemList.getJSONObject(i).getInt("usesLeft"));
                    break;
                case "sceptre":
                    currItem = new Sceptre(activeGame);
                    break;
                case "midnight_armour":
                    currItem = new MidnightArmour(activeGame);
                    currItem.setUsesLeft(itemList.getJSONObject(i).getInt("usesLeft"));
                    break;
            }
            currItem.setId(itemList.getJSONObject(i).getString("entityId"));
            activeGame.moveToInventory(currItem);
        }

        return createDungeonResponse();
    }

    
    /** 
     * returns a list of all the saves
     * @return List<String>
     */
    public List<String> allGames() {
        try {
            return FileLoader.listFileNamesInDirectoryOutsideOfResources("saveFiles");
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }

    
    /** 
     * tick the gamestate
     * @param itemUsed
     * @param movementDirection
     * @return DungeonResponse
     * @throws IllegalArgumentException
     * @throws InvalidActionException
     */
    public DungeonResponse tick(String itemUsed, Direction movementDirection) throws IllegalArgumentException, InvalidActionException {
        //  Move character and all moving entities.
        
        Player player = activeGame.getPlayer();

        // Use item if appropriate. This does nothing if itemUsed is null or empty.
        // Throws exceptions where appropriate.
        if (itemUsed != null) {
            player.useItem(activeGame.getEntityTypeFromId(itemUsed));
        }
        
        // List<EntityResponse> entityResponses = new ArrayList<EntityResponse>();
        List<Entity> entities = activeGame.getEntities();
        List<Entity> entitiesCopy = new ArrayList<>(entities);
        


        if (movementDirection != Direction.NONE && movementDirection != null) {
            
            activeGame.tickCounter();
            
            for (Entity entity: entitiesCopy) {
                if (entity instanceof Enemy) {
                    ((Enemy) entity).updatePosition();
                }
            }
            for (Entity entity: entitiesCopy) {
                if (entity instanceof Player) {
                    ((Player) entity).move(movementDirection);
                } 
                
            }
            for (Entity entity: entitiesCopy) {
                if (entity instanceof StaticEntity) {
                    ((StaticEntity) entity).update(movementDirection);
                }
            }

            activeGame.triggerBombExplosion();

        }
        
        for (Entity entity : entities) {
            if (entity instanceof Exit || entity instanceof Switch) {
                entity.notifyObservers();
            }
        }

        return createDungeonResponse();
    }

    
    /** 
     * interact with spawner or mercenary if in range
     * @param entityId
     * @return DungeonResponse
     * @throws IllegalArgumentException
     * @throws InvalidActionException
     */
    public DungeonResponse interact(String entityId) throws IllegalArgumentException, InvalidActionException {

        List<Entity> entities = activeGame.getEntities();
        Player player = activeGame.getPlayer();


        boolean found = false;
        List<Entity> entitiesCopy = new ArrayList<>(entities);
        for (Entity entity : entitiesCopy) {
            String Id = entity.getId();
            // Find the relevant entity.
            if (Id.equals(entityId)) {
                if (entity instanceof Mercenary) {
                    player.bribe((Mercenary)entity);
                    found = true;
                } else if (entity instanceof ZombieToastSpawner) {
                    player.destroySpawner((ZombieToastSpawner)entity);
                    found = true;
                }
            }
        }


        if (found == false) {
            throw new IllegalArgumentException("Entity Id is not valid.");
        }

        return createDungeonResponse();

    }

    
    /** 
     * player will build items 
     * @param buildable
     * @return DungeonResponse
     * @throws IllegalArgumentException
     * @throws InvalidActionException
     */
    public DungeonResponse build(String buildable) throws IllegalArgumentException, InvalidActionException {
        if (!buildable.equals("bow") && !buildable.equals("shield") && !buildable.equals("sceptre") && !buildable.equals("midnight_armour")) {
            throw new IllegalArgumentException("Can only build bow, shield, sceptre or midnight_armour!");
        }

        Inventory inventory = activeGame.getInventory();
        Player player = activeGame.getPlayer();
        
        if (buildable.equals("bow")) {
            inventory.craftBow(player);
        } else if (buildable.equals("shield")) {
            inventory.craftShield(player);
        } else if (buildable.equals("sceptre")) {
            inventory.craftSceptre(player);
        } else if (buildable.equals("midnight_armour")) {
            inventory.craftMidnightArmour(player);
        }

        return createDungeonResponse();

    }





    
    /** 
     * from JSON, extract a composite goal
     * @param goalCondition
     * @param activeGame
     * @return GoalComponent
     */
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
                break;
            case "treasure":
                overallGoal = new CollectTreasureGoal();
                activeGame.addSimpleGoals(overallGoal);
                break;
            case "boulders":
                overallGoal = new BoulderOnSwitchGoal();
                activeGame.addSimpleGoals(overallGoal);
                break;
            case "exit":
                overallGoal = new ExitGoal();
                activeGame.addSimpleGoals(overallGoal);
                break;  
        }
        return overallGoal;
    }

    public DungeonResponse createDungeonResponse() {
        String dungeonId = activeGame.getDungeonId();
        String dungeonName = activeGame.getDungeonName();
        List<EntityResponse> entityResponses = createEntityResponseList();
        List<ItemResponse> itemResponses = createItemResponseList();
        List<String> buildables = createBuildableList();
        String goalString = createGoalString();

        return new DungeonResponse(dungeonId, dungeonName, entityResponses, itemResponses, buildables, goalString);
    }


    
    /** 
     * creates list of entity responses
     * @return List<EntityResponse>
     */
    public List<EntityResponse> createEntityResponseList() {
        List<Entity> entities = activeGame.getEntities();
        List<EntityResponse> entityResponses = new ArrayList<EntityResponse>();
        for (Entity entity : entities) {
            entityResponses.add(new EntityResponse(entity.getId(), entity.getType(), entity.getPosition(), entity.isInteractable()));
        }
        return entityResponses;
    }

    
    /** 
     * create a list of itemresponses
     * @return List<ItemResponse>
     */
    public List<ItemResponse> createItemResponseList() {
        List<Item> items = activeGame.getInventory().getInventoryList();
        List<ItemResponse> itemResponses = new ArrayList<ItemResponse>();
        for (Item item : items) {
            itemResponses.add(new ItemResponse(item.getId(), item.getType()));
        }
        return itemResponses;
    }

    
    /** 
     * create a string of goals
     * @return String
     */
    public String createGoalString() {
        String goalString = "";
        for (GoalComponent simpleGoal : activeGame.getSimpleGoals()) {
            String simpleGoalString = simpleGoal.simpleGoalToString();
            if (!goalString.contains(simpleGoalString)) {
                goalString += simpleGoal.simpleGoalToString();
            }
        }
        if (activeGame.getOverallGoal().isComplete()) {
            goalString = "";
        }
        return goalString;

    }

    
    /** 
     * create a list of buildables
     * @return List<String>
     */
    public List<String> createBuildableList() {
        return activeGame.getInventory().getBuildables(activeGame.getPlayer());
    }


    /**
     * return the current game
     * @return Dungeon return the activeGame
     */
    public Dungeon getActiveGame() {
        return activeGame;
    }

}
package dungeonmania;

import java.util.List;

import dungeonmania.util.Position;

import java.util.ArrayList;
import java.util.Arrays;


import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Dungeon {

    private String dungeonName;
    private Gamemode gamemode;
    private String dungeonId;
    private List<Entity> entities = new ArrayList<Entity>();
    private Inventory inventory = new Inventory();    
    private List<GoalComponent> simpleGoals = new ArrayList<GoalComponent>();
    private GoalComponent overallGoal;
    private int counter;

    public Dungeon(String dungeonName, String gamemodeString, String dungeonId) {
        this.dungeonName = dungeonName;
        initialiseGameMode(gamemodeString); 
        this.dungeonId = dungeonId;
        this.counter = 0;
    }

    
    /**
     * get the entity type string from a given entity id
     * @param id
     * @return String
     */
    public String getEntityTypeFromId(String id) {
        for (Entity entity : entities) {
            if (entity.getId().equals(id)) {
                return entity.getType();
            }
        }
        List<Item> items = inventory.getInventoryList();
        for (Item item : items) {
            if (item.getId().equals(id)) {
                return item.getType();
            }
        }

        throw new IllegalArgumentException("Not a valid id!");
    }

    public void triggerBombExplosion() {
        
        List<Entity> bombs = getEntitiesOfType("bomb");
        List<Entity> switches = getEntitiesOfType("switch");

        for (Entity b : bombs) {
            for (Entity s : switches) {
                String positionB = b.getPosition().toString();
                String positionS = b.getPosition().toString();
                throw new IllegalArgumentException(positionB + positionS);

                // if (Position.isAdjacent(b.getPosition(), s.getPosition())) {
                //     ((Bomb)b).explode();
                // }
            }
        }

    }

    public List<Entity> getEntitiesOfType(String type) {
        return entities.stream().filter(n -> n.getType().equals(type)).collect(Collectors.toList());
        

    }
    /**
     * tick the global spawn counter and spawn a spider or hydra if needed
     */
    public void tickCounter() {
        counter++;
        spiderSpawn();
        hydraSpawn();
    }

    /**
     * spawn a spider if the spawn timer is reached
     */
    public void spiderSpawn() {
        if (getSpawnTimer() == 0) {
            return;
        } 

        if (countSpiders() >= 5) {
            return;
        }
                
        if (counter % getSpawnTimer() == 0) {
            Position randPos = randomSpawnPosition();
            while (getEntities(randPos).stream().anyMatch(x -> x.getType().equals("boulder"))) {
                randPos = randomSpawnPosition();
            }
            new Spider(randPos, this);
        }
    }

    /**
     * spawn a hydra if the spawn timer is reached
     */
    public void hydraSpawn() {
        if (getHydraSpawnTimer() == 0) {
            return;
        }

        if (counter % getHydraSpawnTimer() == 0) {
            Position randPos = randomSpawnPosition();
            // Cannot spawn in this position. There is already an entity there.
            while (getEntities(randPos).size() != 0) {
                randPos = randomSpawnPosition();
            }
            new Hydra(randPos, this);
        }
    }

    
    /** 
     * return a random spawn position
     * @return Position
     */
    public Position randomSpawnPosition() {
        Random number = new Random();
        int bound = 20;
        int xRandom = number.nextInt(bound);
        int yRandom = number.nextInt(bound);
        Position randPos = new Position(xRandom, yRandom);
        return randPos;
    }

    
    /**
     * count the number of spiders in the game 
     * @return long
     */
    public long countSpiders() {
        return entities.stream().filter(x -> x instanceof Spider).count();
    }

    
    /**
     * get the gamemode's spawn time number for zombie and spiders    
     * @return int
     */
    public int getSpawnTimer() {
        return getGamemode().getSpawnTimer();
    }

    
    /**
     * get the gamemode's spawn time number for hydra   
     * @return int
     */
    public int getHydraSpawnTimer() {
        return getGamemode().getHydraSpawnTimer();
    }
    
    /**
     * set the gamemode to one of the difficulties 
     * @param gamemodeString
     */
    public void initialiseGameMode(String gamemodeString) {
        String standardisedGamemodeString = gamemodeString.toLowerCase();
        gamemode = GamemodeFactory.makeGamemode(standardisedGamemodeString);
    }

    
    /**
     * adds an entity to the game and try to attach it to goals 
     * @param entity
     */
    public void addEntity(Entity entity) {
        entities.add(entity);
        conditionalAttach(entity);
    }

    
    /**
     * try to attach entities to goals 
     * @param entity
     */
    public void conditionalAttach(Entity entity) {
        for (GoalComponent goal : entity.getDungeon().getSimpleGoals()) {
            goal.tryToAttach(entity);
        }
    }
    
    

    
    /** 
     * adds a simple goal to the list of simple goals
     * @param simpleGoal
     */
    public void addSimpleGoals(GoalComponent simpleGoal) {
        simpleGoals.add(simpleGoal);
    }
    
    
    /**
     * get entities at a position 
     * @param position
     * @return List<Entity>
     */
    public List<Entity> getEntities(Position position) {
        List<Entity> entitiesAtPos = new ArrayList<Entity>();
        for (Entity entity : entities) {
            if (entity.getPosition().equals(position)) {
                entitiesAtPos.add(entity);
            }
        }
        return entitiesAtPos;
    }


    
    /**
     * remove an entity from the game 
     * @param entity
     */
    public void removeEntity(Entity entity) {
        entities.remove(entity);
    }

    
    /** 
     * remove entity from a position
     * @param position
     */
    public void removeFrom(Position position) {
        List<Entity> entitiesCopy = new ArrayList<Entity>(entities);
        for (Entity entity : entitiesCopy) {
            if (entity.getPosition().equals(position)) {
                removeEntity(entity);
            }
        }
    }


    
    /** 
     * make a prim algorithm dungeon
     * @param width
     * @param height
     * @param start
     * @param end
     */
    public void randomizedPrims(int width, int height, Position start, Position end) {
        boolean[][] mazeMap = new boolean[height][width];
        for (boolean[] row : mazeMap) {
            Arrays.fill(row, false);
        }

        PrimDungeon primDungeon = new PrimDungeon(start, end, mazeMap);
        primDungeon.primGenerate();

        GoalComponent exit = new ExitGoal();
        setOverallGoal(exit);
        addSimpleGoals(exit);

        extractPrimMaze(primDungeon.getMazeMap(), start, end);

    }




    
    /** 
     * extract the dungeon map made using prim's algorithm into this dungeon
     * @param mazeMap
     * @param start
     * @param end
     */
    private void extractPrimMaze(boolean[][] mazeMap, Position start, Position end) {
        for (int i = 0; i < mazeMap.length; i++) {
            for (int j = 0; j < mazeMap[i].length; j++) {
                if (mazeMap[i][j] == false) {
                    new Wall(new Position(j, i) , this);
                }
            }
        }
        new Player(start, this);
        new Exit(end, this);
    }

    
    /** 
     * find and return the player
     * @return Player
     */
    public Player getPlayer() {
        for (Entity entity : entities) {
            if (entity instanceof Player) {
                return (Player) entity;
            }
        }
        return null;
    }

    
    /** 
     * move an item to the inventory and out of the entities in the dungeon
     * @param item
     */
    public void moveToInventory(Item item) {
        inventory.add(item);
        entities.remove(item);
    }

    /**
     * @return Inventory return the inventory
     */
    public Inventory getInventory() {
        return inventory;
    }
    

    /**
     * @return String return the dungeonName
     */
    public String getDungeonName() {
        return dungeonName;
    }

    /**
     * @param dungeonName the dungeonName to set
     */
    public void setDungeonName(String dungeonName) {
        this.dungeonName = dungeonName;
    }

    /**
     * @return Gamemode return the gamemode
     */
    public Gamemode getGamemode() {
        return gamemode;
    }

    /**
     * @param gamemode the gamemode to set
     */
    public void setGamemode(Gamemode gamemode) {
        this.gamemode = gamemode;
    }

    /**
     * @return String return the dungeonId
     */
    public String getDungeonId() {
        return dungeonId;
    }

    /**
     * @param dungeonId the dungeonId to set
     */
    public void setDungeonId(String dungeonId) {
        this.dungeonId = dungeonId;
    }

    /**
     * @return entities return the entities
     */
    public List<Entity> getEntities() {
        return entities;
    }




    /**
     * @param entities the entities to set
     */
    public void setEntities(List<Entity> entities) {
        this.entities = entities;
    }

    /**
     * @return List<GoalComponent> return the simpleGoals
     */
    public List<GoalComponent> getSimpleGoals() {
        return simpleGoals;
    }

    /**
     * @param simpleGoals the simpleGoals to set
     */
    public void setSimpleGoals(List<GoalComponent> simpleGoals) {
        this.simpleGoals = simpleGoals;
    }

    /**
     * @return GoalComponent return the overallGoal
     */
    public GoalComponent getOverallGoal() {
        return overallGoal;
    }

    /**
     * @param overallGoal the overallGoal to set
     */
    public void setOverallGoal(GoalComponent overallGoal) {
        this.overallGoal = overallGoal;
    }


    /**
     * @return int return the counter
     */
    public int getCounter() {
        return counter;
    }

    /**
     * @param counter the counter to set
     */
    public void setCounter(int counter) {
        this.counter = counter;
    }

}

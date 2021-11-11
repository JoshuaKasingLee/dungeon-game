package dungeonmania;

import java.util.List;

import dungeonmania.util.Position;

import java.util.ArrayList;
import java.util.Arrays;
import java.lang.Math;

import java.util.Random;

public class Dungeon {

    private String dungeonName;
    private Gamemode gamemode;
    private String dungeonId;
    private List<Entity> entities = new ArrayList<Entity>();
    private Inventory inventory = new Inventory();    // subject to change name or class to entity
    private List<GoalComponent> simpleGoals = new ArrayList<GoalComponent>();
    private GoalComponent overallGoal;
    private int counter;

    public Dungeon(String dungeonName, String gamemodeString, String dungeonId) {
        this.dungeonName = dungeonName;
        initialiseGameMode(gamemodeString); 
        this.dungeonId = dungeonId;
        this.counter = 0;
    }

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

    

    public void tickCounter() {
        counter++;
        spiderSpawn();
    }

    public void spiderSpawn() {
        if (getSpawnTimer() == 0) {
            return;
        } 

        if (countSpiders() >= 5) {
            return;
        }
                
        if (counter % getSpawnTimer() == 0) {
            Position randPos = randomSpiderPosition();
            while (getEntities(randPos).stream().anyMatch(x -> x.getType().equals("boulder"))) {
                randPos = randomSpiderPosition();
            }
            new Spider(randPos, this);
        }
    }

    public Position randomSpiderPosition() {
        Random number = new Random();
        int bound = 20;
        int xRandom = number.nextInt(bound);
        int yRandom = number.nextInt(bound);
        Position randPos = new Position(xRandom, yRandom);
        return randPos;
    }

    public long countSpiders() {
        return entities.stream().filter(x -> x instanceof Spider).count();
    }

    public int getSpawnTimer() {
        return getGamemode().getSpawnTimer();
    }
    
    /** 
     * @param gamemodeString
     */
    public void initialiseGameMode(String gamemodeString) {
        if (gamemodeString.equals("Peaceful") || gamemodeString.equals("peaceful")) {
            gamemode = new Peaceful();
        }
        else if (gamemodeString.equals("Standard") || gamemodeString.equals("standard")) {
            gamemode = new Standard();
        }
        else if (gamemodeString.equals("Hard") || gamemodeString.equals("hard")) {
            gamemode = new Hard();
        }
    }

    
    /** 
     * @param entity
     */
    public void addEntity(Entity entity) {
        entities.add(entity);
        conditionalAttach(entity);
    }

    
    /** 
     * @param entity
     */
    public void conditionalAttach(Entity entity) {
        for (GoalComponent goal : entity.getDungeon().getSimpleGoals()) {
            goal.tryToAttach(entity);
        }
    }
    
    
    /** 
     * @param treasure
     * @return boolean
     */
    public boolean isEntityInDungeon(Treasure treasure) {
        return entities.stream().anyMatch(entity -> (entity instanceof Treasure));
    }

    
    /** 
     * @param simpleGoal
     */
    public void addSimpleGoals(GoalComponent simpleGoal) {
        simpleGoals.add(simpleGoal);
    }
    
    
    /** 
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
     * @param position
     * @return Door
     */
    public Door getDoor(Position position) {
        List<Entity> entityList = getEntities(position);
        for (Entity entity : entityList) {
            if (entity instanceof Door) {
                return (Door) entity;
            }
        }
         return null;
    }

    
    /** 
     * @param entity
     * @param position
     */
    public void addTo(Entity entity, Position position) {
        entity.setPosition(position);
        addEntity(entity);
    }

    
    /** 
     * @param entity
     */
    public void removeEntity(Entity entity) {
        entities.remove(entity);
    }

    
    /** 
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


    public void randomizedPrims(int width, int height, Position start, Position end) {
        boolean[][] mazeMap = new boolean[height][width];
        for (boolean[] row : mazeMap) {
            Arrays.fill(row, false);
        }

        int xStart = start.getX();
        int yStart = start.getY();
        mazeMap[yStart][xStart] = true;

        List<Position> options = new ArrayList<>();
        options = primAdjacentPositions(start, mazeMap, false, 2);

        while (!options.isEmpty()) {
            int randomInt = (int)(Math.random()*(options.size()));
            Position next = options.get(randomInt);
            options.remove(randomInt);
            List<Position> neighbours = primAdjacentPositions(next, mazeMap, true, 2);
            if (!neighbours.isEmpty()) {
                randomInt = (int)(Math.random()*(neighbours.size()));
                Position neighbour = neighbours.get(randomInt);
                mazeMap[next.getY()][next.getX()] = true;
                Position inBetween = getInBetween(next, neighbour);
                mazeMap[inBetween.getY()][inBetween.getX()] = true;
            }
            List<Position> newNeighbours = primAdjacentPositions(next, mazeMap, false, 2);
            for (Position n : newNeighbours) {
                if (!options.contains(n)) {
                    options.add(n);
                }
            }
        }
        
        int xEnd = end.getX();
        int yEnd = end.getY();
        if (mazeMap[yEnd][xEnd] == false) {
            mazeMap[yEnd][xEnd] = true;
            List<Position> endNeighboursEmpty = primAdjacentPositions(end, mazeMap, true, 1);
            List<Position> endNeighboursWalls = primAdjacentPositions(end, mazeMap, false, 1);
            if (endNeighboursEmpty.isEmpty()) {
                int randomInt = (int)(Math.random()*(endNeighboursWalls.size()));
                Position neighbour = endNeighboursWalls.get(randomInt);
                mazeMap[neighbour.getY()][neighbour.getX()] = true;
            }

        }
        
        GoalComponent exit = new ExitGoal();
        setOverallGoal(exit);
        addSimpleGoals(exit);

        // now to make it all into active game.
        extractPrimMaze(mazeMap, start, end);



    }

    private static Position getInBetween(Position next, Position neighbour) {
        int newX = (next.getX() + neighbour.getX())/2;
        int newY = (next.getY() + neighbour.getY())/2;
        return new Position(newX, newY);
    }

    private static List<Position> primAdjacentPositions(Position pos, boolean[][] mazeMap, boolean empty, int distance) {
        List<Position> primAdjPositions = new ArrayList<Position>();
        int currX = pos.getX();
        int currY = pos.getY();



        int[] xChange = {-distance, distance, 0, 0};
        int[] yChange = {0, 0, -distance, distance};

        for (int i = 0; i < 4; i++) {
            int newX = currX + xChange[i];
            int newY = currY + yChange[i];
            if (!posPastMapBoundary(newY, newX, mazeMap) && mazeMap[newY][newX] == empty) {
                primAdjPositions.add(new Position(newX, newY));
            }
        }
        return primAdjPositions;
    }


    private static boolean posPastMapBoundary(int currY, int currX, boolean[][] mazeMap) {
        return (currX <= 0 || currX >= mazeMap.length - 1 || currY <= 0 || currY >= mazeMap.length - 1);
    }



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

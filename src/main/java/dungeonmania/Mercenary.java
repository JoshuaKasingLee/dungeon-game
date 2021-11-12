package dungeonmania;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.HashMap;

import dungeonmania.util.Position;

//TODO: Remove imports and main function
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.util.Direction;


public class Mercenary extends Enemy {
    public static final int ORIGINAL_HEALTH = 10;
    public static final int MERCENARY_ATTACK_DAMAGE = 4;
    public static final int BATTLE_RADIUS = 3;

    private int x = getXPosition();
    private int y = getYPosition();

    // All possible positions and their distances from player
    private List<Position> possiblePositions;
    private List<Double> distanceOfPositions;

    private Position playerPosition;

    public Mercenary(Position position, Dungeon dungeon) {
        super(position, dungeon);
        this.setHealth(ORIGINAL_HEALTH);
        this.setAttackDamage(MERCENARY_ATTACK_DAMAGE);
        this.setArmour(50); // assume zombie has 50% chance spawning with armour
        setInteractable(true);
    }

    public Mercenary(Position position, Dungeon dungeon, int durability, boolean isAlly) {
        this(position, dungeon);
        giveArmour(durability);
        setAlly(isAlly);
    }

    /** 
     * updates mercenary position for 1 tick
     */
    @Override
    public void updatePosition() {
        playerPosition = getDungeon().getPlayer().getPosition();
        this.possiblePositions = getPossiblePositions();
        this.distanceOfPositions = getDistanceOfPositions();

        // create an array of 4 possible positions between character and mercenary (up, down, left, right)
        // go in the shortest direction between the two // if blocked, don't move
        Entity player = getDungeon().getEntities().stream().filter(n -> n.getType().equals("player")).findFirst().orElse(null);
        
        int direction;

        if (((Player)player).getCharacterState().getType().equals("Invincible")) {
            direction = distanceOfPositions.indexOf(Collections.max(distanceOfPositions));
        } else if (((Player)player).getCharacterState().getType().equals("Invisible")) {
            return;
        } else {
            // Use Dijkstra's Algorithm to find the fastest path to the player
            // direction = distanceOfPositions.indexOf(Collections.min(distanceOfPositions));

            Map<Position, Position> pathMap = dijkstras(getDungeon().getGrid(), getPosition(), player.getPosition());
            
            direction = getNextDirection(pathMap, player.getPosition());
        }
        
        if (getPosition().equals(player.getPosition())) {
            return;
        }

        switch(direction) {
            case 0:
                moveUp();
                break;
            case 1:
                moveDown();
                break;
            case 2:
                moveLeft();
                break;
            case 3:
                moveRight();
                break;
            case 4:
                return;
        }
    }

    /** 
     * returns list of positions mercenary can move into
     * @return List<Position>
     */
    public List<Position> getPossiblePositions() {
        List<Position> possiblePositions = new ArrayList<Position>();
        // Up position
        possiblePositions.add(new Position(x, y-1));
        // Down position
        possiblePositions.add(new Position(x, y+1));
        // Left position
        possiblePositions.add(new Position(x-1, y));
        // Right position
        possiblePositions.add(new Position(x+1, y));
        return possiblePositions;
    }
    
    /** 
     * returns list of distance between player and possible mercenary positions
     * @return List<Double>
     */
    public List<Double> getDistanceOfPositions() {
        List<Double> distanceOfPositions = new ArrayList<Double>();
        // Loop through and add the distance of between two positions
        for (Position possiblePosition : possiblePositions) {
            distanceOfPositions.add(calculateDistance(possiblePosition, playerPosition));
        }
        return distanceOfPositions;
    }

    /** 
     * returns true if player is within mercenary's battle radius, false if else
     * @param player
     * @return boolean
     */
    public boolean withinPlayerRadius(Player player) {
        for (Position p : battleRadiusPositions(getPosition())) {
            if (player.getPosition().equals(p)) {
                return true;
            }
        }
        return false;
    }

    /** 
     * @return String
     */
    @Override
    public String setType() {
        return "mercenary";
    }

    // helper functions

    /** 
     * returns list of all positions within mercenary's battle radius
     * @param pos
     * @return List<Position>
     */
    private static List<Position> battleRadiusPositions(Position pos) {
        List<Position> battleRadiusPositions = new ArrayList<Position>();
        int currX = pos.getX();
        int currY = pos.getY();
        for (int i = currX - BATTLE_RADIUS; i <= currX + BATTLE_RADIUS; i++) {
            for (int j = currY - BATTLE_RADIUS; j <= currY + BATTLE_RADIUS; j++) {
                Position p = new Position(i, j);
                if (calculateDistance(p, pos) <= BATTLE_RADIUS) {
                    battleRadiusPositions.add(p);
                }
            }
        }
        return battleRadiusPositions;
    }

    /** 
     * returns distance between two positions, rounded to nearest whole number
     * @param pos1
     * @param pos2
     * @return double
     */
    private static double calculateDistance(Position pos1, Position pos2) {
        Position dirVector = Position.calculatePositionBetween(pos1, pos2);
        double squaredDist = (dirVector.getX() * dirVector.getX()) + (dirVector.getY() * dirVector.getY());
        // always rounds up
        return (double) (Math.sqrt(squaredDist));
    }

    
    /** 
     * @param grid
     * @param source
     * @return Map<Position, Position>
     * @throws Exception
     */
    public Map<Position, Position> dijkstras(List<Position> grid, Position source, Position destination) {
        Map<Position, Double> dist = new HashMap<Position, Double>();
        Map<Position, Position> prev = new HashMap<Position, Position>();

        for (Position position : grid) {
            dist.put(position, null);
            prev.put(position, null);
        }
        dist.put(source, 0.0);

        List<Position> queue = new ArrayList<Position>(grid);
        
        while (!queue.isEmpty()) {
            Position lowestPosition = null;
            // Find the node with the smallest distance
            // Traverse queue to find node with the lowest distance
            for (Position position : queue) {
                if (lowestPosition == null && dist.get(position) != null) {
                    lowestPosition = position;
                } else if (dist.get(position) != null && dist.get(position) < dist.get(lowestPosition)) {
                    lowestPosition = position;
                }
            }
            
            // assert lowest position is null
            if (lowestPosition == null) {
                System.out.println("ERROR");
                return null;
            }

            // Check each cardinal neighbour of the lowest position
            for (Position neighbour : getCardinalNeighbours(lowestPosition.getX(), lowestPosition.getY())) {
                if (dist.containsKey(neighbour)) {
                    double moveFactor = getDungeon().getMovementFactor(neighbour);
                    if (moveFactor == -1) {
                        moveFactor = Integer.MAX_VALUE;
                    }
                    if (dist.get(neighbour) == null) {
                        dist.put(neighbour, dist.get(lowestPosition) + moveFactor);
                        prev.put(neighbour, lowestPosition);
                    } else if (dist.get(lowestPosition) + moveFactor < dist.get(neighbour)) {
                        dist.put(neighbour, dist.get(lowestPosition) + moveFactor);
                        prev.put(neighbour, lowestPosition);
                    }
                }
            }
            
            // If found player, quit
            if (lowestPosition.equals(destination)) {
                queue.clear();
            }
            queue.remove(lowestPosition);
        }
        return prev;
    }

    /** 
     * returns list of positions zombie toast can move into
     * @return List<Position>
     */
    public List<Position> getCardinalNeighbours(int x, int y) {
        List<Position> cardinalNeighbours = new ArrayList<Position>();
        // Up position
        cardinalNeighbours.add(new Position(x, y-1));
        // Down position
        cardinalNeighbours.add(new Position(x, y+1));
        // Left position
        cardinalNeighbours.add(new Position(x-1, y));
        // Right position
        cardinalNeighbours.add(new Position(x+1, y));
        return cardinalNeighbours;
    }

    
    
    /** 
     * Get the next direction of given a hashmap of next positions and a destination
     * @param prev
     * @param destination
     * @return Direction
     */
    public int getNextDirection(Map<Position, Position> prev, Position destination) {
        // Pointer to next position
        // TODO: remove
        Position nextPosition = destination;

        // Go through the previous hashmap to find the next move
        while (!prev.get(nextPosition).equals(getPosition())) {
            // System.out.println("StuckHere: " + nextPosition);
            nextPosition = prev.get(nextPosition);
        }

        Position offset = Position.calculatePositionBetween(getPosition(), nextPosition);

        // Initialise error direction
        int nextDirection = -1;

        if (offset.equals(new Position(0, -1))) {
            nextDirection = 0;
        } else if (offset.equals(new Position(0, 1))) {
            nextDirection = 1;
        } else if (offset.equals(new Position(-1, 0))) {
            nextDirection = 2;
        } else if (offset.equals(new Position(1, 0))) {
            nextDirection = 3;
        } else if (offset.equals(new Position(0, 0))) {
            nextDirection = 4;
        }

        if (nextDirection == -1) {
            System.out.println("ERROR");
            return -1;
        }

        return nextDirection;
    }

    public static void main(String[] args) {
        // Initialise dungeon
        DungeonManiaController controller = new DungeonManiaController();

        // Create a new game
        DungeonResponse dungeonInfo = controller.newGame("swampAndMercenary", "Standard");

        // Get player entity
        EntityResponse player = null;
        player = dungeonInfo.getEntities().stream().filter(n -> n.getType().equals("player")).findFirst().orElse(null);

        // Assert player location is 1,1
        // assertEquals(new Position(1, 1), player.getPosition());
        System.out.println("Expected: " + new Position(1, 1) + " Actual: " + player.getPosition());

        // Get mercenary entity
        EntityResponse mercenary = null;
        mercenary = dungeonInfo.getEntities().stream().filter(n -> n.getType().equals("mercenary")).findFirst().orElse(null);

        // Assert mercenary location is 3,5
        // assertEquals(new Position(1, 10), mercenary.getPosition());
        System.out.println("Expected: " + new Position(1, 10) + " Actual: " + mercenary.getPosition());

        // Move character into wall and position remains the same
        dungeonInfo = controller.tick(null, Direction.DOWN);
        
        // Get mercenary entity
        mercenary = dungeonInfo.getEntities().stream().filter(n -> n.getType().equals("mercenary")).findFirst().orElse(null);
        System.out.println("Expected: " + new Position(1, 9) + " Actual: " + mercenary.getPosition());

        dungeonInfo = controller.tick(null, Direction.DOWN);
        // Get mercenary entity
        mercenary = dungeonInfo.getEntities().stream().filter(n -> n.getType().equals("mercenary")).findFirst().orElse(null);
        System.out.println("Expected: " + new Position(1, 9) + " Actual: " + mercenary.getPosition());

        dungeonInfo = controller.tick(null, Direction.NONE);
        // Get mercenary entity
        mercenary = dungeonInfo.getEntities().stream().filter(n -> n.getType().equals("mercenary")).findFirst().orElse(null);
        System.out.println("Expected: " + new Position(1, 8) + " Actual: " + mercenary.getPosition());

        dungeonInfo = controller.tick(null, Direction.NONE);
        // Get mercenary entity
        mercenary = dungeonInfo.getEntities().stream().filter(n -> n.getType().equals("mercenary")).findFirst().orElse(null);
        System.out.println("Expected: " + new Position(1, 7) + " Actual: " + mercenary.getPosition());

        dungeonInfo = controller.tick(null, Direction.NONE);
        // Get mercenary entity
        mercenary = dungeonInfo.getEntities().stream().filter(n -> n.getType().equals("mercenary")).findFirst().orElse(null);
        System.out.println("Expected: " + new Position(1, 6) + " Actual: " + mercenary.getPosition());

    }
}

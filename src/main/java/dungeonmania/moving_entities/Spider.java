package dungeonmania.moving_entities;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

import dungeonmania.Dungeon;
import dungeonmania.Entity;
import dungeonmania.player.Player;
import dungeonmania.static_entities.Boulder;
import dungeonmania.static_entities.StaticEntity;
import dungeonmania.static_entities.SwampTile;
import dungeonmania.util.Position;

public class Spider extends Enemy {
    public static final int ORIGINAL_HEALTH = 5;
    public static final int SPIDER_ATTACK_DAMAGE = 2;
    private static final int CLOCKWISE = 1;

    private int x = getXPosition();
    private int y = getYPosition();

    // The position number of a spider (0-7)
    // 0 1 2
    // 7 p 3
    // 6 5 4
    private int positionNumber;
    // 1 is clockwise, -1 is anticlockwise
    private int direction = CLOCKWISE;

    // All possible positions and their distances from player
    private List<Position> possiblePositions;
    private List<Double> distanceOfPositions;

    // Positions
    private Position startingPosition;
    private ArrayList<Position> adjacentPositions;

    public Spider(Position position, Dungeon dungeon) {
        super(position, dungeon);
        this.setHealth(ORIGINAL_HEALTH);
        this.setAttackDamage(SPIDER_ATTACK_DAMAGE);
        this.startingPosition = position;
        this.adjacentPositions = setPositions(position);
        this.positionNumber = 0;
    }

    public Spider(Position position, Dungeon dungeon, Position startingPosition, int positionNumber) {
        super(position, dungeon);
        this.setHealth(ORIGINAL_HEALTH);
        this.setAttackDamage(SPIDER_ATTACK_DAMAGE);
        this.startingPosition = startingPosition;
        this.adjacentPositions = setPositions(position);
        this.positionNumber = positionNumber;
    }

    // spider functions
    
    /** 
     * returns list of positions spider can move into
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
     * returns list of distance between player and possible spider positions
     * @return List<Double>
     */
    public List<Double> getDistanceOfPositions() {
        List<Double> distanceOfPositions = new ArrayList<Double>();
        // Loop through and add the distance of between two positions
        for (Position possiblePosition : possiblePositions) {
            distanceOfPositions.add(calculateDistance(possiblePosition, getDungeon().getPlayer().getPosition()));
        }
        return distanceOfPositions;
    }

    /** 
     * update position of spider for one tick
     */
    @Override
    public void updatePosition() {
        Entity player = getDungeon().getEntities().stream().filter(n -> n.getType().equals("player")).findFirst().orElse(null);
        
        if (!checkValidMove()) {
            return;
        }

        if (((Player)player).getPlayerState().getType().equals("Invincible")) {
            this.possiblePositions = getPossiblePositions();
            this.distanceOfPositions = getDistanceOfPositions();
            int direction = distanceOfPositions.indexOf(Collections.max(distanceOfPositions));
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
            }
            this.startingPosition = getPosition();
            this.adjacentPositions = setPositions(getPosition());
            this.positionNumber = 0;
            return;
        }

        // Check if spider is in its starting position
        if (getPosition().equals(startingPosition)) {
            if (checkForBoulder(adjacentPositions.get(1))) {
                return;
            } else {
                setPosition(adjacentPositions.get(1));
                setPositionNumber(1);
            }
        } else {
            setPositionNumber(getPositionNumber());
            // Check if the next position has a boulder
            if (!checkForBoulder(adjacentPositions.get(getNextPositionNumber()))) {
                setPosition(adjacentPositions.get(getNextPositionNumber()));
                setPositionNumber(getNextPositionNumber());
            } else {
                // Reverse direction of spider
                direction *= -1;
                // Check if the next position has a boulder
                if (!checkForBoulder(adjacentPositions.get(getNextPositionNumber()))) {
                    setPosition(adjacentPositions.get(getNextPositionNumber()));
                    setPositionNumber(getNextPositionNumber());
                }
            }
        }
    }

    /** 
     * get the spider's next position number (depending on the direction)
     * @return int
     */
    public int getNextPositionNumber() {
        if ((this.positionNumber + direction) < 0) {
            return ((this.positionNumber + direction) % 8 + 8);
        }
        return ((this.positionNumber + direction) % 8);
    }

    /** 
     * check if there are any boulders in a given position
     * @param position
     * @return boolean
     */
    public boolean checkForBoulder(Position position) {
        for (Entity entity : getDungeon().getEntities(position)) {
            if (entity instanceof Boulder) {
                return true;
            }
        }
        return false;
    }

    // basic getters and getters

    /** 
     * @param position
     * @return ArrayList<Position>
     */
    public ArrayList<Position> setPositions(Position position) {
        return new ArrayList<Position>(position.getAdjacentPositions());
    }
    
    /** 
     * @return List<Position>
     */
    public List<Position> getAdjacentPositions() {
        return adjacentPositions;
    }

    /** 
     * @param number
     */
    // Set the spider's position number
    public void setPositionNumber(int number) {
        this.positionNumber = number;
    }

    /** 
     * get the spider's position number
     * @return int
     */
    public int getPositionNumber() {
        int returnInteger = 0;
        for (int i = 0; i <= 7; i++) {
            if (getPosition().equals(adjacentPositions.get(i))) {
                returnInteger = i;
                break;
            }
        }
        return returnInteger;
    }
    
    /** 
     * @return String
     */
    @Override
    public String setType() {
        return "spider";
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
     * @return startingPosition
     */
    public Position getStartingPosition() {
        return startingPosition;
    }

    public boolean checkValidMove() {
        for (Entity e : getDungeon().getEntities(getPosition())) {
            if (e instanceof SwampTile) {
                if (getSlowed() < ((StaticEntity)e).getMovementFactor()) {
                    incrementSlowed();
                    return false;
                }
            }
        }
        setSlowed(1);
        return true;
    }
}

package dungeonmania;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

import dungeonmania.util.Position;

public class Mercenary extends Enemy {
    public static final int ORIGINAL_HEALTH = 10;
    public static final int MERCENARY_ATTACK_DAMAGE = 4;
    public static final int BATTLE_RADIUS = 3;

    private int x = getXPosition();
    private int y = getYPosition();

    // All possible positions and their distances from player
    private List<Position> possiblePositions;
    private List<Double> distanceOfPositions;

    private Position playerPosition = getDungeon().getPlayer().getPosition();


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

    @Override
    public void updatePosition() {
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
            direction = distanceOfPositions.indexOf(Collections.min(distanceOfPositions));
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
        }
    }

    
    /** 
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
     * @return String
     */
    @Override
    public String setType() {
        return "mercenary";
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
}

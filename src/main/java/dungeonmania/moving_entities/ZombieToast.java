package dungeonmania.moving_entities;

import java.util.Random;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

import dungeonmania.Dungeon;
import dungeonmania.Entity;
import dungeonmania.player.Player;
import dungeonmania.util.Position;

public class ZombieToast extends Enemy {
    public static final int ORIGINAL_HEALTH = 7;
    public static final int ZOMBIE_TOAST_ATTACK_DAMAGE = 3;

    private int x = getXPosition();
    private int y = getYPosition();

    // All possible positions and their distances from player
    private List<Position> possiblePositions;
    private List<Double> distanceOfPositions;

    public ZombieToast(Position position, Dungeon dungeon) {
        super(position, dungeon);
        this.setHealth(ORIGINAL_HEALTH);
        this.setAttackDamage(ZOMBIE_TOAST_ATTACK_DAMAGE);
        this.setArmour(25); // assume zombie has 25% chance spawning with armour
    }

    public ZombieToast(Position position, Dungeon dungeon, int durability) {
        this(position, dungeon);
        giveArmour(durability);
    }

    /** 
     * updates zombietoast's position for 1 tick
     * moves zombie in random valid direction
     */
    @Override
    public void updatePosition() {
        Entity player = getDungeon().getEntities().stream().filter(n -> n.getType().equals("player")).findFirst().orElse(null);

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
            return;
        }
        
        Random rand = new Random();
        int randN = rand.nextInt();
        if (randN % 4 == 0) {
            moveUp();
        } else if (randN % 4 == 1) {
            moveDown();
        } else if (randN % 4 == 2) {
            moveLeft();
        } else if (randN % 4 == 3) {
            moveRight();
        }
    }
    
    /** 
     * @return String
     */
    @Override
    public String setType() {
        return "zombie_toast";
    }

    /** 
     * returns list of positions zombie toast can move into
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
     * returns list of distance between player and possible zombie toast positions
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

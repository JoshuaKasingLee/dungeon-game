package dungeonmania;

import dungeonmania.util.Position;
import java.util.Random;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class Hydra extends Enemy {
    public static final int ORIGINAL_HEALTH = 15;
    public static final int HYDRA_ATTACK_DAMAGE = 4;
    private int x = getXPosition();
    private int y = getYPosition();
    private List<Position> possiblePositions;
    private List<Double> distanceOfPositions;

    public Hydra(Position position, Dungeon dungeon) {
        super(position, dungeon);
        this.setHealth(ORIGINAL_HEALTH);
        this.setAttackDamage(HYDRA_ATTACK_DAMAGE);
    }

    public Hydra(Position position, Dungeon dungeon, int durability) {
        this(position, dungeon);
        giveArmour(durability);
    }

    /** 
     * updates health of hydra in 1 round of battle with input entity
     * @param other
     */
    @Override
    public void updateHealth(MovingEntity other) {
        if (attackSuccess()) {
            int newHealth = getHealth() - ((other.getHealth() * other.getAttackDamage()) / 5 );
            this.setHealth(newHealth);
        } else {
            int newHealth = getHealth() + ((other.getHealth() * other.getAttackDamage()) / 5 );
            this.setHealth(newHealth);
        }
    }

    /** 
     * generates a true or false with equal chance
     * @param percentage
     * @return boolean
     */
    public boolean attackSuccess() {
        Random rand = new Random();
        int randN = rand.nextInt(2);
        if (randN == 1) {
            return true;
        }
        return false;
    }


    /** 
     * updates hydra's position for 1 tick
     * moves hydra in random valid direction
     */
    @Override
    public void updatePosition() {
        Entity player = getDungeon().getEntities().stream().filter(n -> n.getType().equals("player")).findFirst().orElse(null);
        if (((Player)player).getCharacterState().getType().equals("Invincible")) {
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

    /** 
     * @return String
     */
    @Override
    public String setType() {
        return "hydra";
    }

    
}

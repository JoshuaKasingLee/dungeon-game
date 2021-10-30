package dungeonmania;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

import dungeonmania.util.Position;

public class Mercenary extends Enemy {
    public static final int ORIGINAL_HEALTH = 10;
    public static final int MERCENARY_ATTACK_DAMAGE = 4;

    private int x = getPosition().getX();
    private int y = getPosition().getY();
    // distance list
    // position list
    // private List<Position> possiblePositions = getPossiblePositions();
    // private List<Double> distanceOfPositions = getDistanceOfPositions();

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
        setDurability(durability);
        setAlly(isAlly);
    }

    @Override
    public void updatePosition() {
        // // create an array of 4 possible positions between character and mercenary (up, down, left, right)
        // // go in the shortest direction between the two // if blocked, don't move
        // int direction = distanceOfPositions.indexOf(Collections.min(distanceOfPositions));

        // switch(direction) {
        //     case 0:
        //         moveUp();
        //         break;
        //     case 1:
        //         moveDown();
        //         break;
        //     case 2:
        //         moveLeft();
        //         break;
        //     case 3:
        //         moveRight();
        //         break;
        // }
    }

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
    
    public List<Double> getDistanceOfPositions() {
        // List<Double> distanceOfPositions = new ArrayList<Double>();
        // for (Position possiblePosition : possiblePositions) {
        //     Position tempPosition = Position.calculatePositionBetween(possiblePosition, playerPosition);
        //     distanceOfPositions.add(Math.sqrt(tempPosition.getX()*tempPosition.getX() + 
        //                                       tempPosition.getY()*tempPosition.getY()));
        // }
        // return distanceOfPositions;
        return new ArrayList<Double>();
    }

    @Override
    public String setType() {
        return "Mercenary";
    }
}

package dungeonmania;

import java.util.List;
import java.util.ArrayList;

import dungeonmania.util.Position;

public class Spider extends Enemy {
    public static final int ORIGINAL_HEALTH = 5;
    public static final int SPIDER_ATTACK_DAMAGE = 2;
    private static final int CLOCKWISE = 1;

    // The position number of a spider (0-7)
    // 0 1 2
    // 7 p 3
    // 6 5 4
    private int positionNumber;
    // 1 is clockwise, -1 is anticlockwise
    private int direction = CLOCKWISE;

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

    public ArrayList<Position> setPositions(Position position) {
        return new ArrayList<Position>(position.getAdjacentPositions());
    }

    public List<Position> getAdjacentPositions() {
        return adjacentPositions;
    }

    @Override
    public void updatePosition() {
        // Check if spider is in its starting position
        if (getPosition().equals(startingPosition)) {
            if (checkForBoulder(adjacentPositions.get(1))) {
                return;
            } else {
                setPosition(adjacentPositions.get(1));
                setPositionNumber(1);
            }
        } else {
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

    // Set the spider's position number
    public void setPositionNumber(int number) {
        this.positionNumber = number;
    }

    // Get the spider's position number
    public int getPositionNumber() {
        if (this.positionNumber < 0) {
            return (this.positionNumber % 8 + 8);
        }
        return (this.positionNumber % 8);
    }

    // Get the spider's next position number (depending on the direction)
    public int getNextPositionNumber() {
        if ((this.positionNumber + direction) < 0) {
            return ((this.positionNumber + direction) % 8 + 8);
        }
        return ((this.positionNumber + direction) % 8);
    }

    // Check if there are any boulders in a given position
    public boolean checkForBoulder(Position position) {
        for (Entity entity : getDungeon().getEntities(position)) {
            if (entity instanceof Boulder) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String setType() {
        return "Spider";
    }
}

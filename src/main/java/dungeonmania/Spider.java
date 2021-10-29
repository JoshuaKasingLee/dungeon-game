package dungeonmania;

import java.util.List;
import java.util.ArrayList;

import dungeonmania.util.Position;
import dungeonmania.util.Direction;

public class Spider extends Enemy {
    public static final int ORIGINAL_HEALTH = 5;
    public static final int SPIDER_ATTACK_DAMAGE = 2;
    private static final int CLOCKWISE = 1;
    private static final int ANTICLOCKWISE = -1;

    private int positionNumber;
    // 1 is clockwise, -1 is anticlockwise
    private int direction = CLOCKWISE;

    // Positions
    private Position startingPosition;
    private ArrayList<Position> adjacentPositions;

    public Spider(Position position, String id, Dungeon dungeon) {
        super(position, id, "Spider", dungeon);
        this.setHealth(ORIGINAL_HEALTH);
        this.setAttackDamage(SPIDER_ATTACK_DAMAGE);
        this.startingPosition = position;
        this.adjacentPositions = setPositions(position);
        this.positionNumber = 0;
        // need to consider edge case where spider movement goes off the map - what happens?
    }

    public ArrayList<Position> setPositions(Position position) {
        //GET ADJACENT POSITIONS
        return new ArrayList<Position>(position.getAdjacentPositions());
    }

    public List<Position> getAdjacentPositions() {
        return adjacentPositions;
    }

    @Override
    public void updatePosition() {
        if (getPosition().equals(startingPosition)) {
            if (checkForBoulder(adjacentPositions.get(1))) {
                return;
            } else {
                setPosition(adjacentPositions.get(1));
                setPositionNumber(1);
            }
        } else {
            if (!checkForBoulder(adjacentPositions.get(getNextPositionNumber()))) {
                setPosition(adjacentPositions.get(getNextPositionNumber()));
                setPositionNumber(getNextPositionNumber());
            } else {
                direction *= -1;
            }
        }
    }

    public void setPositionNumber(int number) {
        this.positionNumber = number;
    }

    public int getPositionNumber() {
        if (this.positionNumber < 0) {
            return (this.positionNumber % 8 + 8);
        }
        return (this.positionNumber % 8);
    }

    public int getNextPositionNumber() {
        if ((this.positionNumber + direction) < 0) {
            return ((this.positionNumber + direction) % 8 + 8);
        }
        return ((this.positionNumber + direction) % 8);
    }

    public boolean checkForBoulder(Position position) {
        for (Entity entity : getDungeon().getEntities(position)) {
            if (entity instanceof Boulder) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        Dungeon dungeon = new Dungeon("Dungeon", "Standard", "1");
        Spider thisSpider = new Spider(new Position(2, 2), "Kelly", dungeon);
        System.out.println(thisSpider.getAdjacentPositions());
        // Boulder newBoulder = new Boulder(new Position(0, -1), "test", dungeon);
        System.out.println(thisSpider.getPosition());
        thisSpider.updatePosition();
        System.out.println(thisSpider.getPosition());
        thisSpider.updatePosition();
        System.out.println(thisSpider.getPosition());
        thisSpider.updatePosition();
        System.out.println(thisSpider.getPosition());
        thisSpider.updatePosition();
        System.out.println(thisSpider.getPosition());
        thisSpider.updatePosition();
    }
}

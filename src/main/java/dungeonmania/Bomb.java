package dungeonmania;

import java.util.ArrayList;
import java.util.List;
import dungeonmania.util.Position;

public class Bomb extends Item {
    public final static int BLAST_RADIUS = 3;

    public Bomb (Position position, Dungeon dungeon) {
        super(position, dungeon);
    }

    /**
     * drops bomb in Character's current position
     */
    @Override
    public void activate(Player character) {
        super.activate(character);
        this.setPosition(character.getPosition());
        getDungeon().addEntity(this);
    }

    /**
     * creations explosion - destroys all entities in the bomb's blast radius, except for the character
     * assume bomb does not explode if boulder is already on switch?
     */
    public void explode() {
        Position bombPos = getPosition();
        for (Position p : blastRadiusPositions(bombPos)) {
            getDungeon().removeFrom(p);
        }
        // remove bomb
        getDungeon().removeEntity(this);
    }

    private static List<Position> blastRadiusPositions(Position pos) {
        List<Position> blastRadiusPositions = new ArrayList<Position>();
        int currX = pos.getX();
        int currY = pos.getY();
        for (int i = currX - BLAST_RADIUS; i <= currX + BLAST_RADIUS; i++) {
            for (int j = currY - BLAST_RADIUS; j <= currY + BLAST_RADIUS; j++) {
                Position p = new Position(i, j);
                if (calculateDistance(p, pos) <= BLAST_RADIUS && !p.equals(pos)) {
                    // System.out.println(calculateDistance(p, pos));
                    blastRadiusPositions.add(p);
                }
            }
        }
        return blastRadiusPositions;
    }

    private static int calculateDistance(Position pos1, Position pos2) {
        Position dirVector = Position.calculatePositionBetween(pos1, pos2);
        int squaredDist = (dirVector.getX() * dirVector.getX()) + (dirVector.getY() * dirVector.getY());
        // always rounds up
        return (int) Math.ceil(Math.sqrt(squaredDist));
    }

    @Override
    public String setType() {
        return "Bomb";
    }
}

package dungeonmania.items;

import java.util.ArrayList;
import java.util.List;

import dungeonmania.Dungeon;
import dungeonmania.player.Player;
import dungeonmania.util.Position;

public class Bomb extends Item {
    public final static int BLAST_RADIUS = 3;

    public Bomb (Position position, Dungeon dungeon) {
        super(position, dungeon);
        dungeon.addEntity(this);
    }

    // bomb usage functions

    /**
     * drops bomb in dungeon in player's current position
     */
    @Override
    public void activate(Player player) {
        super.activate(player);
        this.setPosition(player.getPosition());
        getDungeon().addEntity(this);
    }

    /**
     * creates explosion - destroys all entities in the bomb's blast radius, except for the player
     */
    public void explode() {
        Position bombPos = getPosition();
        for (Position p : blastRadiusPositions(bombPos)) {
            getDungeon().explodePosition(p);
        }
        getDungeon().removeEntity(this);
    }

    // helper functions
    
    /** 
     * returns list of all positions within bomb's blast radius
     * @param pos
     * @return List<Position>
     */
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

    
    /** 
     * returns distance between two positions, rounded to nearest whole number
     * @param pos1
     * @param pos2
     * @return int
     */
    private static int calculateDistance(Position pos1, Position pos2) {
        Position dirVector = Position.calculatePositionBetween(pos1, pos2);
        int squaredDist = (dirVector.getX() * dirVector.getX()) + (dirVector.getY() * dirVector.getY());
        return (int) Math.ceil(Math.sqrt(squaredDist)); // always rounds up
    }
    
    // basic setters

    /** 
     * @return String
     */
    @Override
    public String setType() {
        return "bomb";
    }
}

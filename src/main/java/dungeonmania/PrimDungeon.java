package dungeonmania;

import dungeonmania.util.Position;

import java.util.List;
import java.util.ArrayList;
import java.lang.Math;



public class PrimDungeon {
    private Position start;
    private Position end;
    private boolean[][] mazeMap;

    public PrimDungeon(Position start, Position end, boolean[][] mazeMap) {
        this.start = start;
        this.end = end;
        this.mazeMap = mazeMap;
    }


    /** 
     * generates the mazemap from Prim's Algorithm
     */
    public void primGenerate() {
        int xStart = start.getX();
        int yStart = start.getY();
        mazeMap[yStart][xStart] = true;

        List<Position> options = new ArrayList<>();
        options = primAdjacentPositions(start, false, 2);

        while (!options.isEmpty()) {
            int randomInt = (int)(Math.random()*(options.size()));
            Position next = options.get(randomInt);
            options.remove(randomInt);
            List<Position> neighbours = primAdjacentPositions(next, true, 2);
            if (!neighbours.isEmpty()) {
                randomInt = (int)(Math.random()*(neighbours.size()));
                Position neighbour = neighbours.get(randomInt);
                mazeMap[next.getY()][next.getX()] = true;
                Position inBetween = getInBetween(next, neighbour);
                mazeMap[inBetween.getY()][inBetween.getX()] = true;
            }
            List<Position> newNeighbours = primAdjacentPositions(next, false, 2);
            for (Position n : newNeighbours) {
                if (!options.contains(n)) {
                    options.add(n);
                }
            }
        }
        
        int xEnd = end.getX();
        int yEnd = end.getY();
        if (mazeMap[yEnd][xEnd] == false) {
            mazeMap[yEnd][xEnd] = true;
            List<Position> endNeighboursEmpty = primAdjacentPositions(end, true, 1);
            List<Position> endNeighboursWalls = primAdjacentPositions(end, false, 1);
            if (endNeighboursEmpty.isEmpty()) {
                int randomInt = (int)(Math.random()*(endNeighboursWalls.size()));
                Position neighbour = endNeighboursWalls.get(randomInt);
                mazeMap[neighbour.getY()][neighbour.getX()] = true;
            }

        }
        
    }


    
    /**
     * gets the position between two positions
     * @param next
     * @param neighbour
     * @return Position
     */
    private Position getInBetween(Position next, Position neighbour) {
        int newX = (next.getX() + neighbour.getX())/2;
        int newY = (next.getY() + neighbour.getY())/2;
        return new Position(newX, newY);
    }

    
    /**
     * finds the up,left,right,down neighbours a certain distance away e.g. 1 or 2 tiles away
     * @param pos
     * @param empty
     * @param distance
     * @return List<Position>
     */
    private List<Position> primAdjacentPositions(Position pos, boolean empty, int distance) {
        List<Position> primAdjPositions = new ArrayList<Position>();
        int currX = pos.getX();
        int currY = pos.getY();



        int[] xChange = {-distance, distance, 0, 0};
        int[] yChange = {0, 0, -distance, distance};

        for (int i = 0; i < 4; i++) {
            int newX = currX + xChange[i];
            int newY = currY + yChange[i];
            if (!posPastMapBoundary(newY, newX) && mazeMap[newY][newX] == empty) {
                primAdjPositions.add(new Position(newX, newY));
            }
        }
        return primAdjPositions;
    }

    
    /** 
     * check if passed position is on or outside the map boundary
     * @param currY
     * @param currX
     * @return boolean
     */
    private boolean posPastMapBoundary(int currY, int currX) {
        return (currX <= 0 || currX >= mazeMap.length - 1 || currY <= 0 || currY >= mazeMap.length - 1);
    }


    /**
     * @return boolean[][] return the mazeMap
     */
    public boolean[][] getMazeMap() {
        return mazeMap;
    }

}

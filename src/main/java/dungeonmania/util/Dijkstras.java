// package dungeonmania.util;

// import java.util.ArrayList;
// import java.util.List;
// import java.util.Objects;
// import java.util.Map;
// import java.util.HashMap;
// import dungeonmania.util.Position;

// public final class Dijkstras {
    
    
//     public Map<Position, Position> dijkstras(List<Position> grid, Position source) throws Exception {
//         Map<Position, Double> dist = new HashMap<Position, Double>();
//         Map<Position, Position> prev = new HashMap<Position, Position>();

//         for (Position position : grid) {
//             dist.put(position, null);
//             prev.put(position, null);
//         }
//         dist.put(source, 0.0);

//         List<Position> queue = new ArrayList<Position>(grid);
        

//         while (!queue.isEmpty()) {
//             // Find the node with the smallest distance
//             Position lowestPosition = null;

//             // Traverse queue to find node with the lowest distance
//             for (Position position : queue) {
//                 if (lowestPosition.equals(null)) {
//                     lowestPosition = position;
//                 } else if (dist.get(position) != null && dist.get(position) < dist.get(lowestPosition)) {
//                     lowestPosition = position;
//                 }
//             }

//             // Throw error if the lowest position is null
//             if (lowestPosition.equals(null)) {
//                 throw new Exception("The lowestPosition is still null");
//             }

//             // Check each cardinal neighbour of the lowest position
//             for (Position neighbour : getCardinalNeighbours(lowestPosition.getX(), lowestPosition.getY())) {
//                 if (dist.containsKey(neighbour)) {
//                     double moveFactor = getDungeon.getmovementFactor(neighbour);
//                     if (dist.get(neighbour).equals(null) ||
//                         dist.get(lowestPosition) + dist.get(neighbour) < dist.get(neighbour)) {
//                         dist.put(neighbour, dist.get(lowestPosition) + dist.get(neighbour));
//                         prev.put(neighbour, lowestPosition);
//                     }
//                 }
//             }
//         }

//         return prev;
//     }

//     /** 
//      * returns list of positions zombie toast can move into
//      * @return List<Position>
//      */
//     public List<Position> getCardinalNeighbours(int x, int y) {
//         List<Position> cardinalNeighbours = new ArrayList<Position>();
//         // Up position
//         cardinalNeighbours.add(new Position(x, y-1));
//         // Down position
//         cardinalNeighbours.add(new Position(x, y+1));
//         // Left position
//         cardinalNeighbours.add(new Position(x-1, y));
//         // Right position
//         cardinalNeighbours.add(new Position(x+1, y));
//         return cardinalNeighbours;
//     }

// }

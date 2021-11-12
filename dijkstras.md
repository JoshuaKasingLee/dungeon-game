Dijkstra's Algorithm Planning
1. Find out the size of the grid (find the smallest x and y position in the grid, and find the largest x and y position in the grid)
2. Function that finds the smallest distance to each position in the grid

Function to be called from the updatePosition function in mercenary
1. Mercenary gets the distance to each square on the grid
2. Mercenary finds the position of the player
3. Mercenary takes the direction of least distance

Finer Details:
- Each square has a movement_factor
- needs a cost(curr, next) function to get distance between two objects
- needs a getGrid() in dungeon
- needs a movement_factor in Static Entity
- needs a getMovementFactor(Position) in dungeon

Pseudo Implementation:
- Initialise a grid/list of positions (for each position) in dungeon
- The map will be of List<Position>


* Called in update function
- dist will be a Map<Position, Double>
- prev will be a Map<Position, Position>

- Re-initialise each position in grid
    for each position in grid:
        dist[position] = infinity
        prev[position] = null
    dist[source] = 0

- Create a queue -> let queue (OR LIST) be a Queue<Position> of every position in grid
- while queue/list is not empty:
    - u: get node of position with the smallest distance
    - for each cardinal neighbour v of u:
        if dist[u] + cost(u, v) < dist[v]:
            dist[v] = dist[u] + cost(u, v)
            prev[v] = u

- Once the positions have been set, return the "previous" map

- To update the position of the entity, get the "previous" mapping and put in the Position of the destination
- While prev.prev[position] != Null:
    nextPosition = prev.prev[position]

- UpdatePosition():
    move, Position

public Map<Position, Position> dijkstras(List Positions, Position source) {

    return Map<Position, Position>
}

Assumptions:
- Boulders can pass through swamp tiles
- Dijkstra's algorithm does not account for the ability to go through portals
- Peaceful: Swamp Tile Movement = 1, Standard: Swamp Tile Movement = 2, Hard: Swamp Tile Movement = 3
- Enemies are not slowed by swamp tiles
- Door unlocked

to check in meeting:
- Can mercenaries go through doors?
- When traversing entities in the DungeonMania, update the dungeons of each entity
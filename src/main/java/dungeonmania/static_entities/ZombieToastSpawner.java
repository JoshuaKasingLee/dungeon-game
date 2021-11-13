package dungeonmania.static_entities;

import java.util.List;
import java.util.ArrayList;

import dungeonmania.util.Position;
import dungeonmania.Dungeon;
import dungeonmania.Entity;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.moving_entities.ZombieToast;
import dungeonmania.util.Direction;

public class ZombieToastSpawner extends StaticEntity {
    // private int counter = 0;

    public ZombieToastSpawner(Position position, Dungeon dungeon) {
        super(position, dungeon);
        setInteractable(true);
    }

    // public ZombieToastSpawner(Position position, Dungeon dungeon, int counter) {
    //     super(position, dungeon);
    //     this.counter = counter;
    //     setInteractable(true);
    // }

    
    /** 
     * @param direction
     */
    @Override
    public void update(Direction direction) {
        // counter++;
        spawnZombie();
    }

    public void spawnZombie() {
        if (getSpawnTimer() == 0) {
            return;
        } else if (getDungeon().getCounter() % getSpawnTimer() == 0) {
            // Get adjacent positions
            List<Position> adjacentPositions = new ArrayList<Position>(this.getPosition().getAdjacentPositions());
            // createZombieToast(new Position(0, 0));

            // Check whether adjacent positions are free and spawn a zombie
            boolean obstructed = false;
            for (Position position : adjacentPositions) {
                List<Entity> entitiesAtPos = getDungeon().getEntities(position);
                for (Entity e : entitiesAtPos) {
                    if ((e instanceof Wall) ||
                        (e instanceof Boulder) ||
                        (e instanceof Door) ||
                        (e instanceof ZombieToastSpawner)) {
                        obstructed = true;
                    }
                }
                if (!obstructed) {
                    new ZombieToast(position, getDungeon());
                    return;
                }                
            }
        }
    }

    /** 
     * @param position
     */
    // public void createZombieToast(Position position) {
    //     getDungeon().addEntity(new ZombieToast(position, getDungeon()));
    // }

    // basic getters and setters

    /** 
     * @return String
     */
    @Override
    public String setType() {
        return "zombie_toast_spawner";
    }
    
    /** 
     * @return int
     */
    public int getSpawnTimer() {
        return getDungeon().getSpawnTimer();
    }


    /** 
     * Sets the movement factor of the entity
     * @return double
     */
    @Override
    public double setMovementFactor() {
        return -1;
    }

}

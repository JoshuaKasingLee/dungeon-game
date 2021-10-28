package dungeonmania;

import java.util.List;

import dungeonmania.util.Position;
import dungeonmania.util.Direction;

public class ZombieToastSpawner extends StaticEntity {
    private int counter = 0;

    public ZombieToastSpawner(Position position, String id) {
        super(position, id);
    }

    @Override
    public String setType() {
        return "ZombieToastSpawner";
    }

    @Override
    public void update(Direction direction) {
        counter++;
        spawnZombie();
    }

    public void spawnZombie() {
        // dungeon.newEntity(super.position, zombie)
        if (getSpawnTimer() == 0) {
            return;
        } else if (counter % getSpawnTimer() == 0) {
            // id creator?
            // Get adjacent positions
            List<Position> adjacentPositions = this.getPosition().getAdjacentPositions();

            // Check whether adjacent positions are free and spawn a zombie
            for (Position position : adjacentPositions) {
                if (getDungeon().getEntity(position) /* IS NOT OBSTRUCTED*/) {
                    getDungeon().createZombieToast(position);
                    break;
                }
            }
        }
    }

    public int getSpawnTimer() {
        return getGamemode().getSpawnTimer();
    }

    public void createZombieToast(Position position) {
        // zombie creation
        getDungeon().addEntity(new ZombieToast(position, getGamemode()));
    }

    @Override
    public boolean setIsInteractable() {
        return true;
    }
}

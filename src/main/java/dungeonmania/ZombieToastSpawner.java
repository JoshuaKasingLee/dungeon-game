package dungeonmania;

import java.util.List;

import dungeonmania.util.Position;
import dungeonmania.util.Direction;

public class ZombieToastSpawner extends StaticEntity {
    private int counter = 0;

    public ZombieToastSpawner(Position position, Dungeon dungeon) {
        super(position, dungeon);
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
        if (getSpawnTimer() == 0) {
            return;
        } else if (counter % getSpawnTimer() == 0) {
            // Get adjacent positions
            List<Position> adjacentPositions = this.getPosition().getAdjacentPositions();

            // Check whether adjacent positions are free and spawn a zombie
            for (Position position : adjacentPositions) {
                if (!getDungeon().getEntity(position) instanceof Wall ||
                    !getDungeon().getEntity(position) instanceof Boulder ||
                    !getDungeon().getEntity(position) instanceof Door ||
                    !getDungeon().getEntity(position) instanceof ZombieToastSpawner) {
                    createZombieToast(position);
                    break;
                }
            }
        }
    }

    public int getSpawnTimer() {
        return getGamemode().getSpawnTimer();
    }

    public void createZombieToast(Position position) {
        getDungeon().addEntity(new ZombieToast(position, getDungeon()));
    }

    @Override
    public boolean setIsInteractable() {
        return true;
    }
}

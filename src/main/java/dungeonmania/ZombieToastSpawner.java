package dungeonmania;

import java.util.List;

import dungeonmania.util.Position;
import dungeonmania.util.Direction;

public class ZombieToastSpawner extends StaticEntity {
    private int counter = 0;

    public ZombieToastSpawner(Position position, Dungeon dungeon) {
        super(position, dungeon);
        setInteractable(true);
    }

    public ZombieToastSpawner(Position position, Dungeon dungeon, int counter) {
        super(position, dungeon);
        this.counter = counter;
        setInteractable(true);
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
                for (Entity e : getDungeon().getEntities(position)) {
                    if (!(e instanceof Wall) ||
                        !(e instanceof Boulder) ||
                        !(e instanceof Door) ||
                        !(e instanceof ZombieToastSpawner)) {
                        createZombieToast(position);
                        break;
                    }
                }
            }
        }
    }

    public int getSpawnTimer() {
        return getDungeon().getGamemode().getSpawnTimer();
    }

    public void createZombieToast(Position position) {
        getDungeon().addEntity(new ZombieToast(position, getDungeon()));
    }

}

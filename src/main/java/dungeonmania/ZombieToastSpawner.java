package dungeonmania;

import java.util.List;
import java.util.ArrayList;

import dungeonmania.util.Position;
import dungeonmania.util.Direction;

import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;

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
            List<Position> adjacentPositions = new ArrayList<Position>(this.getPosition().getAdjacentPositions());
            // createZombieToast(new Position(0, 0));
            
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
        // new ZombieToast(position, getDungeon());
    }


    /**
     * @return int return the counter
     */
    public int getCounter() {
        return counter;
    }

    /**
     * @param counter the counter to set
     */
    public void setCounter(int counter) {
        this.counter = counter;
    }

    public static void main(String[] args) {
        DungeonManiaController controller = new DungeonManiaController();

        // Create a new game
        DungeonResponse dungeonInfo = controller.newGame("spawner", "Standard");
        
        for (EntityResponse entity : dungeonInfo.getEntities()) {
            // System.out.println(entity.getType() + ": " + entity.getPosition());
            if (entity.getType().equals("ZombieToastSpawner") || entity.getType().equals("ZombieToast")) {
                System.out.println(entity.getType() + ": " + entity.getPosition());
            }
        }

        for (int i = 0; i < 19; i++) {
            dungeonInfo = controller.tick(null, Direction.NONE);
        }
        dungeonInfo = controller.tick(null, Direction.NONE);

        for (EntityResponse entity : dungeonInfo.getEntities()) {
            // System.out.println(entity.getType() + ": " + entity.getPosition());
            if (entity.getType().equals("ZombieToastSpawner") || entity.getType().equals("ZombieToast")) {
                System.out.println(entity.getType() + ": " + entity.getPosition());
            }
        }

        // Initialise dungeon
        // Dungeon dungeon = new Dungeon("spawner", "Standard", "1");

        // ZombieToastSpawner spawner = new ZombieToastSpawner(new Position(1, 1), dungeon);

        // System.out.println("Before");
        // for (Entity entity : dungeon.getEntities()) {
        //     System.out.println(entity.getType() + ": " + entity.getPosition());
        // }

        // // spawner.createZombieToast(new Position(1, 0));
        // // spawner.spawnZombie();
        // // dungeon.addEntity(new ZombieToast(new Position(1, 0), dungeon));
        // ZombieToast zombie = new ZombieToast(new Position(1, 0), dungeon);

        // System.out.println("After");
        // for (Entity entity : dungeon.getEntities()) {
        //     System.out.println(entity.getType() + ": " + entity.getPosition());
        // }
        // System.out.println(dungeon.getEntities());
    }

}

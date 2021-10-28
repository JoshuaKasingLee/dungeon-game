package dungeonmania;

import dungeonmania.util.Position;

public class ZombieToastSpawner extends Entity {

    public ZombieToastSpawner(Position position, String id, Dungeon dungeon) {
        super(position, id, "ZombieToastSpawner", dungeon);
        setIsInteractable(true);
    }
    
}

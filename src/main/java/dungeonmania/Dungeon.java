package dungeonmania;

import dungeonmania.util.Position;
import java.util.ArrayList;
import java.util.List;

public class Dungeon {
    private String dungeonName;
    private String gameMode;
    private String dungeonId;

    public Dungeon(String dungeonName, String gameMode, String dungeonId) {
        this.dungeonName = dungeonName;
        this.gameMode = gameMode;
        this.dungeonId = dungeonId;
    }

    public List<Entity> getEntities(Position position) {
        return new ArrayList<Entity>();
    }

    // assume only one door in each position
    public Door getDoor(Position position) {
        // return door if there is one in the position, null if else
        return null;
    }

    public List<Item> getItems(Position position) {
        return new ArrayList<Item>();
    }

    public List<Enemy> getEnemies(Position position) {
        return new ArrayList<Enemy>();
    }

    public void addTo(Entity entity, Position position) {
    }

    public void removeFrom(Entity entity) {
    }

    // for testing
    public List<Entity> getAllEntities() {
        return new ArrayList<Entity>();
    }
    
}

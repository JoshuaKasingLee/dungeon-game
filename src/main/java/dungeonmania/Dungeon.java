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

    public void addTo(Entity entity, Position position) {
    }

    public void removeFrom(Entity entity) {
    }

    public void removeFrom(Position position) {
        // remove all entities from the given position (including items)
    }

    // for testing
    public List<Entity> getAllEntities() {
        return new ArrayList<Entity>(); // on dungeon map
    }
    
    public Entity getPlayer() {
        return new Player(new Position(0, 0), this);
    }
}

package dungeonmania;

import dungeonmania.util.Position;
import java.util.ArrayList;
import java.util.List;

public class Dungeon {
    private String dungeonName;
    private Gamemode gamemode;
    private String dungeonId;
    private List<Entity> entityList = new ArrayList<Entity>();

    public Dungeon(String dungeonName, String gamemode, String dungeonId) {
        this.dungeonName = dungeonName;
        this.gamemode = new Gamemode(gamemode);
        this.dungeonId = dungeonId;
    }

    public List<Entity> getEntities(Position position) {
        return new ArrayList<Entity>();
    }

    public void addEntity(Entity entity) {
        entityList.add(entity);
    }

    public void removeEntity(Entity entity) {
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

    /**
     * @return Gamemode return the gameMode
     */
    public Gamemode getGamemode() {
        return gamemode;
    }
}
    

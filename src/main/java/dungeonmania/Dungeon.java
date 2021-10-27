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
    
}

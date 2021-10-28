package dungeonmania;

import java.util.List;
import java.util.ArrayList;

public class Dungeon {
    private List<Entity> entityList = new ArrayList<Entity>();

    public List<Entity> getEntityList() {
        return new ArrayList<>(entityList);
    }

    public void addEntity(Entity entity) {
        entityList.add(entity);
    }


    public void removeEntity(Entity entity) {
        entityList.remove(entity);
    }
}

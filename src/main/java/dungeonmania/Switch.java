package dungeonmania;

import dungeonmania.util.Position;
import dungeonmania.util.Direction;

public class Switch extends StaticEntity {    
    public Switch(Position position, Dungeon dungeon) {
        super(position, dungeon);
    }

    @Override
    public String setType() {
        return "switch";
    }

    @Override
    public void update(Direction direction) {
        return;
    }

    // Refactor into lambda function
    public boolean hasBoulder() {
        for (Entity entity : this.getEntities()) {
            if (entity instanceof Boulder) {
                if (this.getPosition().equals(entity.getPosition())) {
                    
                    return true;
                }
            }
        }
        
        return false;
    }
}

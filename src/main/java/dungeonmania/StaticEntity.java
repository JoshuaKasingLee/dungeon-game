package dungeonmania;

import dungeonmania.util.Position;
import dungeonmania.util.Direction;

public abstract class StaticEntity extends Entity {
    public StaticEntity(Position position, Dungeon dungeon) {
        super(position, dungeon);
    }

    /**
     * UPDATES to be overidden by some classes
     * @param direction
     */
    public abstract void update(Direction direction);

    // Change this to lambda function?
    public Position getPlayerPosition() {
        for (Entity entity : this.getEntities()) {
            if (entity.getType() == "player") {
                return entity.getPosition();
            }
        }
        // player does not exist
        return null;
    }
}

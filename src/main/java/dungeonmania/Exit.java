package dungeonmania;

import dungeonmania.util.Position;
import dungeonmania.util.Direction;

public class Exit extends StaticEntity {
    public Exit(Position position, Dungeon dungeon) {
        super(position, dungeon);
    }

    @Override
    public String setType() {
        return "Exit";
    }

    @Override
    // Updates state
    public void update(Direction direction) {
        return;
    }

    public boolean hasPlayer() {
        if (this.getPosition().equals(this.getPlayerPosition())) {
            return true;
        } else {
            return false;
        }
    }
}

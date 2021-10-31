package dungeonmania;

import dungeonmania.util.Position;
import dungeonmania.util.Direction;

public class Wall extends StaticEntity {
    public Wall(Position position, Dungeon dungeon) {
        super(position, dungeon);
    }

    @Override
    public String setType() {
        return "Wall";
    }

    @Override
    public void update(Direction direction) {
        return;
    }

}

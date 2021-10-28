package dungeonmania;

import java.util.List;
import java.util.ArrayList;

import dungeonmania.util.Position;
import dungeonmania.util.Direction;

public class Wall extends StaticEntity {
    public Wall(Position position, String id) {
        super(position, id);
    }

    @Override
    public String setType() {
        return "wall";
    }

    @Override
    public void update(Direction direction) {
        return;
    }
}

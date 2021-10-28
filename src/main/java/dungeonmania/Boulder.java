package dungeonmania;

import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class Boulder extends StaticEntity{
    public Boulder(Position position, String id) {
        super(position, id);
    }

    @Override
    public String setType() {
        return "boulder";
    }

    @Override
    // Updates state
    public void update(Direction direction) {
        if (this.getPosition().equals(this.getPlayerPosition())) {
            move(direction);
        }
    }

    // May have to override if use interface
    public void move(Direction direction) {
        Position currPos = this.getPosition();
        Position dir = direction.getOffset();
        Position newPos = new Position((currPos.getX() + dir.getX()), (currPos.getY() + dir.getY()));
        this.setPosition(newPos);
    }
}

package dungeonmania;

import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class Boulder extends StaticEntity{
    public Boulder(Position position, Dungeon dungeon) {
        super(position, dungeon);
    }

    @Override
    public String setType() {
        return "boulder";
    }

    @Override
    // Updates state
    public void update(Direction direction) {
        if (getPosition().equals(getPlayerPosition())) {
            move(direction);
            for (Entity entity : getEntities()) {
                if (entity instanceof Bomb && getPosition().equals(entity.getPosition)) {
                    ((Bomb)entity).explode();
                }
            }
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

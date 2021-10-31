package dungeonmania;

import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class Boulder extends StaticEntity{
    public Boulder(Position position, Dungeon dungeon) {
        super(position, dungeon);
    }

    /** 
     * @return String
     */
    @Override
    public String setType() {
        return "boulder";
    }
    
    /** 
     * updates boulder position and status for one tick
     * @param direction
     */
    @Override
    public void update(Direction direction) {
        if (getPosition().equals(getPlayerPosition())) {
            move(direction);
            for (Entity entity : getEntities()) {
                if (entity instanceof Bomb && getPosition().equals(entity.getPosition())) {
                    ((Bomb)entity).explode();
                }
            }
        }
    }

    /** 
     * moves boulder in given direction
     * @param direction
     */
    public void move(Direction direction) {
        Position currPos = this.getPosition();
        Position dir = direction.getOffset();
        Position newPos = new Position((currPos.getX() + dir.getX()), (currPos.getY() + dir.getY()));
        this.setPosition(newPos);
    }
}

package dungeonmania;

import dungeonmania.util.Position;

public class Anduril extends Sword {

    public Anduril(Position position, Dungeon dungeon) {
        super(position, dungeon);
    }

    /** 
     * @return String
     */
    @Override
    public String setType() {
        return "anduril";
    }
}

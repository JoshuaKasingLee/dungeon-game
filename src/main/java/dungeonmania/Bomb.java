package dungeonmania;

import dungeonmania.util.Position;

public class Bomb extends Item {

    public Bomb (Position position, String id, Dungeon dungeon) {
        super(position, id, "Bomb", dungeon);
    }

    /**
     * drops bomb in Character's current position
     */
    @Override
    public void activate(Character character) {
        super.activate(character);
        // Position currPos = character.getPosition();
        
    }

    /**
     * creations explosion - destroys all entities in the bomb's blast radius, except for the character
     */
    public void explode() {
    }

    // /**
    //  * @param position the position to set
    //  */
    // public void setPosition(Position position) {
    //     this.position = position;
    // }

}

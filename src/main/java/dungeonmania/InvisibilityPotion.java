package dungeonmania;

import dungeonmania.util.Position;

public class InvisibilityPotion extends Item {

    public InvisibilityPotion (Position position, Dungeon dungeon) {
        super(position, dungeon);
        dungeon.addEntity(this);
    }

    @Override
    public void activate(Player character) {
        super.activate(character);
        character.setCharacterState(new InvisibleState(character));
    }
    
    @Override
    public String setType() {
        return "InvisibilityPotion";
    }
}

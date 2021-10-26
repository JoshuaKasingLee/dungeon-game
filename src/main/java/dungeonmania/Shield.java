package dungeonmania;

public class Shield extends Item {

    // assume a shield completely deflects an enemy's attack

    public Shield (String id) {
        super(id, "Shield");
        this.setUsesLeft(3);
    }

    
}
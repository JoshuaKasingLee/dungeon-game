package dungeonmania;

public class Sword extends Item {
    
    // assume a sword can instantly destroy an enemy
    public Sword (String id) {
        super(id, "Sword");
        this.setUsesLeft(3);
    }
}

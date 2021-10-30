package dungeonmania;

public class Standard extends Gamemode {

    public Standard() {
        super(true, 20, Player.ORIGINAL_HEALTH_STANDARD, true);
    }

    @Override
    public String toString() {
        return "Standard";
    }

}
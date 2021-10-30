package dungeonmania;


public class Peaceful extends Gamemode {

    public Peaceful() {
        super(false, 0, Player.ORIGINAL_HEALTH_STANDARD, true);
    }


    @Override
    public String toString() {
        return "Peaceful";
    }

}
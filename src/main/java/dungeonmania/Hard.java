package dungeonmania;

public class Hard extends Gamemode {
    
    public Hard() {
        super(true, 15, Player.ORIGINAL_HEALTH_HARD, false);
    }

    
    /** 
     * @return String
     */
    @Override
    public String toString() {
        return "Hard";
    }
}

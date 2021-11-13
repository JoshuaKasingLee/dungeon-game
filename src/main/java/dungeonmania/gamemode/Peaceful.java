package dungeonmania.gamemode;

import dungeonmania.player.Player;

public class Peaceful extends Gamemode {

    public Peaceful() {
        super(false, 0, Player.ORIGINAL_HEALTH_STANDARD, true);
    }


    
    /** 
     * returns a string "Peaceful"
     * @return String
     */
    @Override
    public String toString() {
        return "Peaceful";
    }

}
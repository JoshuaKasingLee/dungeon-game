package dungeonmania.gamemode;

import dungeonmania.player.Player;

public class Standard extends Gamemode {

    public Standard() {
        super(true, 20, Player.ORIGINAL_HEALTH_STANDARD, true);
    }

    
    /** 
     * returns a string "Standard"
     * @return String
     */
    @Override
    public String toString() {
        return "Standard";
    }

}
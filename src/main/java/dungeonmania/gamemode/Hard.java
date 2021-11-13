package dungeonmania.gamemode;

import dungeonmania.player.Player;

public class Hard extends Gamemode {
    
    public Hard() {
        super(true, 15, Player.ORIGINAL_HEALTH_HARD, false, 50);
    }

    
    /** 
     * returns a string "Hard"
     * @return String
     */
    @Override
    public String toString() {
        return "Hard";
    }
}

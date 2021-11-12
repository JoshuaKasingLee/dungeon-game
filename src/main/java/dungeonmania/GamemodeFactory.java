package dungeonmania;

public class GamemodeFactory {
    public static Gamemode makeGamemode(String gameMode) {
        Gamemode mode = null;

        switch(gameMode) {

            case "peaceful": 
                mode = new Peaceful();
                break;
            case "standard":
                mode = new Standard();
                break;
            case "hard":
                mode = new Hard();
                break;
        }
        
        return mode;
    }
}

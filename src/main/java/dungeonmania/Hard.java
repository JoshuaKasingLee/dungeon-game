package dungeonmania;

public class Hard extends Gamemode{
    
    public Hard() {
        super();
    }
    public boolean findBattle() {
        return true;
    }
    public int findSpawnTimer() {
        return 15;
    }
    public int findStartingHealth() {
        return 0; //subject ot change
    }
}

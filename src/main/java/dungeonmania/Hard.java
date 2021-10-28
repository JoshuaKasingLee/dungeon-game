package dungeonmania;

public class Hard extends Gamemode{
    
    public Hard() {
        super();
    }
    public boolean checkBattle() {
        return true;
    }
    public int checkSpawnTimer() {
        return 15;
    }
    public int checkStartingHealth() {
        return 0; //subject ot change
    }
}

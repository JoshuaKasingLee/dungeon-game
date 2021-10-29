package dungeonmania;


public class Peaceful extends Gamemode {

    public Peaceful() {
        super();
    }

    public boolean checkBattle() {
        return false;
    }
    public int checkSpawnTimer() {
        return 0;
    }
    public int checkStartingHealth() {
        return 0; //subject ot change
    }

    @Override
    public String toString() {
        return "Peaceful";
    }

}
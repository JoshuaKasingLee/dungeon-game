package dungeonmania;

public class Peaceful extends Gamemode {

    public Peaceful() {
        super();
    }

    public boolean findBattle() {
        return false;
    }
    public int findSpawnTimer() {
        return 0;
    }
    public int findStartingHealth() {
        return 0; //subject ot change
    }

}
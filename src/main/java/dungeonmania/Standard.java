package dungeonmania;

public class Standard extends Gamemode {

    public Standard() {
        super();
    }

    public boolean findBattle() {
        return true;
    }
    public int findSpawnTimer() {
        return 20;
    }
    public int findStartingHealth() {
        return 0; //subject ot change
    }
}
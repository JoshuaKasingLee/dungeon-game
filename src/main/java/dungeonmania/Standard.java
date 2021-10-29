package dungeonmania;

public class Standard extends Gamemode {

    public Standard() {
        super();
    }

    public boolean checkBattle() {
        return true;
    }
    public int checkSpawnTimer() {
        return 20;
    }
    public int checkStartingHealth() {
        return 0; //subject ot change
    }

    @Override
    public String toString() {
        return "Standard";
    }

}
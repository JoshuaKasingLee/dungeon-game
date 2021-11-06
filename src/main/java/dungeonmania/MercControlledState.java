package dungeonmania;

public class MercControlledState implements MercenaryState {
    private Mercenary merc;
    private String type;
    private int timeLeft;
    private boolean wasAlly;
    public static final int SCEPTRE_TIME_LIMIT = 10; // 10 ticks

    public MercControlledState(Mercenary merc) {
        this.wasAlly = merc.isAlly();
        merc.setAlly(true);
        this.merc = merc;
        this.type = "MercControlled";
        this.timeLeft = SCEPTRE_TIME_LIMIT;
    }

    public void updateState() {
        timeLeft--;
        if (timeLeft <= 0) {
            merc.setMercenaryState(new MercStandardState(merc, wasAlly));
        }

    }

    public String getType() {
        return type;
    }
    
}

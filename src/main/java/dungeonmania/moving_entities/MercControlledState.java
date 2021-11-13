package dungeonmania.moving_entities;

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

    public MercControlledState(Mercenary merc, int timeLeft) {
        this(merc);
        this.timeLeft = timeLeft;
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
    

    /**
     * @return Mercenary return the merc
     */
    public Mercenary getMerc() {
        return merc;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return int return the timeLeft
     */
    public int getTimeLeft() {
        return timeLeft;
    }

    /**
     * @param timeLeft the timeLeft to set
     */
    public void setTimeLeft(int timeLeft) {
        this.timeLeft = timeLeft;
    }

    /**
     * @return boolean return the wasAlly
     */
    public boolean isWasAlly() {
        return wasAlly;
    }

    /**
     * @param wasAlly the wasAlly to set
     */
    public void setWasAlly(boolean wasAlly) {
        this.wasAlly = wasAlly;
    }

}

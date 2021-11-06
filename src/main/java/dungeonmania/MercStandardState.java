package dungeonmania;

public class MercStandardState implements MercenaryState {
    private Mercenary merc;
    private String type;

    public MercStandardState(Mercenary merc) {
        this.merc = merc;
        this.type = "MercStandard";
    }

    // if mercenary already has allies
    public MercStandardState(Mercenary merc, boolean isAlly) {
        merc.setAlly(isAlly);
        this.merc = merc;
        this.type = "MercStandard";
    }

    public void updateState() {
    }

    public String getType() {
        return type;
    }
    
}

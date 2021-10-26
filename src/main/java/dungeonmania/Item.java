package dungeonmania;

public class Item {
    private String id;
    private String type;

    public Item (String id, String type) {
        this.id = id;
        this.type = type;
    }

    public void activate() {
    
    }

    // basic getters and setters
    
    /**
     * @return String return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @return String return the type
     */
    public String getType() {
        return type;
    }

}

package dungeonmania;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import dungeonmania.exceptions.InvalidActionException;

public class Inventory {
    private List<Item> inventory = new ArrayList<Item>();

    public Inventory() {
    }
    
    /** 
     * adds given item to inventory list
     * @param item
     */
    public void add(Item item) {
        inventory.add(item);
    }
    
    /** 
     * activates and removes last item of given type from inventory
     * @param type
     */
    public void use(String type, Character character) {
        Item item = this.getItem(type);
        if (item != null) {
            item.activate(character);
            if (item.getUsesLeft() == 0) {
                inventory.remove(item);
            }
            
        } else {
            throw new InvalidActionException(type + "does not exist in inventory");
        }
    }

    public boolean useKey(Door door, Character character) {
        if (door != null) {
            for (Item i : inventory) {
                if (i instanceof Key && i.correctKey(door)) {
                    i.activate(character);
                    inventory.remove(i);
                    return true;
                }
            }
        }
        // no error throwing?
        return false;
    }
    
    /** 
     * returns how many of the input item type is in the inventory
     * @param type
     * @return int
     */
    public int count(String type) {
        int count = 0;
        for (Item i : inventory) {
            if (i.getType().equals(type)) {
                count++;
            }
        }
        return count;
    }

    /** 
     * creates a bow and adds to inventory
     * adjusts stock of crafting materials
     */
    public void craftBow(Character character) {
        if (this.count("Wood") >= 1 && this.count("Arrow") >= 3) {
            this.use("Wood", character);
            this.use("Arrow", character);
            this.use("Arrow", character);
            this.use("Arrow", character); // NEED TO FIX
            // bow id is given by count of bows in inventory (ok since last item is always used, so won't have double ups)
            this.add(new Bow(Integer.toString(this.count("Bow")), character.getDungeon())); 
        } else {
            throw new InvalidActionException("Insufficient crafting material for Bow");
        }
    }

    /** 
     * creates a shield and adds to inventory
     * adjusts stock of crafting materials
     */
    public void craftShield(Character character) {
        if (this.count("Wood") >= 2) {
            // assume we prioritise using treasure over keys for crafting
            if (this.count("Treasure") >= 1) {
                this.use("Wood", character);
                this.use("Wood", character);
                this.use("Treasure", character);
                this.add(new Shield(Integer.toString(this.count("Shield")), character.getDungeon()));  // shield id is given same as shields - see above
            } else if (this.count("Key") >= 1) {
                this.use("Wood", character);
                this.use("Wood", character);
                this.use("Key", character);
                this.add(new Shield(Integer.toString(this.count("Shield")), character.getDungeon())); 
            }
        } else {
            throw new InvalidActionException("Insufficient crafting material for Shield");
        }
    }

    // for testing

    /** 
     * returns list of item types in inventory, in order
     * @return List<String>
     */
    public List<String> listInventory() {
        List<String> invList = new ArrayList<String>();
        for (Item i : inventory) {
            invList.add(i.getType());
        }
        return invList;
    }
    
    /** 
     * returns the last item of the given type from inventory
     * if item type does not exist in inventory, return null
     * @param type
     * @return Item
     */
    public Item getItem(String type) {
        Collections.reverse(inventory);
        for (Item i : inventory) {
            if (i.getType().equals(type)) {
                Collections.reverse(inventory);
                return i;
            }
        }
        Collections.reverse(inventory);
        return null;
    }


    public static void main(String[] args) {
    }

}

package dungeonmania;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.util.Position;

public class Inventory {
    private List<Item> inventory = new ArrayList<Item>();

    public Inventory() {
    }

    // general inventory functions
    
    /** 
     * adds given item to end of inventory list
     * @param item
     */
    public void add(Item item) {
        inventory.add(item);
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
     * activates and removes last item of given type from inventory
     * if item does not exist, throws InvalidActionException
     * @param type
     */
    public void use(String type, Player player) {
        Item item = this.getItem(type);
        if (item != null) {
            item.activate(player);
            if (item.getUsesLeft() == 0) {
                inventory.remove(item);
            }
        } else {
            throw new InvalidActionException(type + " does not exist in inventory");
        }
    }

    /** 
     * given a door, return true if player possesses correct key and unlocks door
     * return false if else
     * @param door
     * @param player
     * @return boolean
     */
    public boolean useKey(Door door, Player player) {
        if (door != null) {
            for (Item i : inventory) {
                if (i instanceof Key) {
                    Key k = (Key) i;
                    if (k.correctKey(door)) {
                        i.activate(player);
                        inventory.remove(i);
                        return true;
                    } 
                }
            }
        }
        return false;
    }

    // crafting functions

    /** 
     * creates a bow and adds to inventory, adjusts stock of crafting materials
     * returns InvalidActionException if insufficient crafting material
     */
    public void craftBow(Player player) {
        if (this.count("wood") >= 1 && this.count("arrow") >= 3) {
            this.use("wood", player);
            this.use("arrow", player);
            this.use("arrow", player);
            this.use("arrow", player);
            this.add(new Bow(player.getDungeon()));
        } else {
            throw new InvalidActionException("Insufficient crafting material for Bow");
        }
    }

    /** 
     * creates a shield and adds to inventory, adjusts stock of crafting materials
     * returns InvalidActionException if insufficient crafting material
     */
    public void craftShield(Player player) {
        if (this.count("wood") >= 2) {
            // assume we prioritise using treasure over keys for crafting
            if (this.count("treasure") >= 1) {
                this.use("wood", player);
                this.use("wood", player);
                this.use("treasure", player);
                this.add(new Shield(player.getDungeon())); 
            } else if (this.count("key") >= 1) {
                this.use("wood", player);
                this.use("wood", player);
                this.use("key", player);
                this.add(new Shield(player.getDungeon())); 
            } else if (this.count("sun_stone") >= 1) {
                this.use("wood", player);
                this.use("wood", player);
                this.use("sun_stone", player);
                this.add(new Shield(player.getDungeon())); 
            }
        } else {
            throw new InvalidActionException("Insufficient crafting material for Shield");
        }
    }

    /** 
     * creates midnight armour and adds to inventory, adjusts stock of crafting materials
     * returns InvalidActionException if insufficient crafting material
     * returns InvalidActionException if zombie is in dungeon
     */
    public void craftMidnightArmour(Player player) {
        Dungeon d = player.getDungeon();
        // first check for zombies
        for (Entity e : d.getEntities()) {
            if (e instanceof ZombieToast) {
                throw new InvalidActionException("Insufficient crafting material for Shield");
            }
        }
        // then check sufficient crafting materials
        if (this.count("armour") >= 1 && this.count("sun_stone") >= 1) {
            Item armour = getItem("armour");
            inventory.remove(armour);
            this.use("sun_stone", player);
            this.add(new MidnightArmour(player.getDungeon()));
        } else {
            throw new InvalidActionException("Insufficient crafting material for Midnight Armour");
        }
    }

    /** 
     * creates sceptre and adds to inventory, adjusts stock of crafting materials
     * returns InvalidActionException if insufficient crafting material
     * returns InvalidActionException if zombie is in dungeon
     */
    public void craftSceptre(Player player) {
        //  check sufficient crafting materials
        if (this.count("sun_stone") >= 1) {
            Boolean bool1 = this.count("wood") >= 1 || this.count("arrow") >= 2;
            Boolean bool2 = this.count("treasure") >= 1 || this.count("key") >= 1 || this.count("sun_stone") >= 2;
            if (bool1 && bool2) {
                this.use("sun_stone", player);
                System.out.print("hello");
                // use bool1 items
                if (this.count("wood") >= 1) {
                    this.use("wood", player);
                } else {
                    this.use("arrow", player);
                    this.use("arrow", player);
                }
                // use bool2 items
                if (this.count("treasure") >= 1) {
                    this.use("treasure", player);
                } else if (this.count("key") >= 1) {
                    this.use("key", player);
                } else {
                    this.use("sun_stone", player);
                    System.out.print("hel");
                }
                this.add(new Sceptre(player.getDungeon()));
            } else {
                throw new InvalidActionException("Insufficient crafting material for Sceptre");
            }
        } else {
            throw new InvalidActionException("Insufficient crafting material for Sceptre");
        }
    }

    /** 
     * @return List<String>
     */
    public List<String> getBuildables() {
        List<String> buildables = new ArrayList<>();
        if (count("wood") >= 1 && count("arrow") >= 3) {
            buildables.add("bow");
        }
        if (count("wood") >= 2) {
            if (count("treasure") >= 1) {
                buildables.add("shield");
            } else if (this.count("key") >= 1) {
                buildables.add("shield");
            }
        }
        return buildables;
    }

    // getters

    /**
     * @return List<Item> return the inventory
     */
    public List<Item> getInventoryList() {
        return inventory;
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

}

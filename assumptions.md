/// ITEMS ///
- Armour can only be attained by battling an armoured enemy
- Bows and shields can only be attained via crafting
- Durabilities:
    Armour - 2
    Sword - 3
    Shield - 3
    Bow - 2
- Bomb radius is less than or equal to 3 positions away
- Shield completely deflects an enemy's attack
- Sword instantly destroys an enemy
- Only one invicibility/invisibility potion can be taken at a time - player is always in state of most recently consumed potion
- Armours and shields will not be used in invincible state, since enemies cannot damage their health already
- Invincibility/invisibility time limit = 10 ticks

/// ENEMIES ///
- Zombie Toast has 25% chance spawning with armour
- Mercenary has 50% chance spawning with armour

/// MERCENARIES ///
- Can be bribed using 1 treasure
- Bribing only occurs if within 2 cardinally adjacent tiles of the original player position (e.g. cannot bribe 1 square diagonally)
- Battle radius is 3 positions
- When within radius of a fighting player, position is updated once per battle, not per round (i.e. to simulate moving twice)

/// PLAYER ///
- Original starting health is 10, unless hard mode - then is 7
- Weapons get "used" when used to destroy zombie toast spawner

/// MOVING ///
- Player cannot move on top of zombie toast spawner
- Zombies & mercenaries are also able to push boulders, etc. -> can do everything a player can do, except open a door
- Items are picked after a player finishes a fight with enemies in the current position

/// BATTLES ///
- Player and enemy health point deductions occur simultaneously (i.e. doesn't matter if player or enemy strikes first, result will be the same)
- Only one weapon and one protection item can be used per round -> no point using bow if sword already instantly kills, and no point using armour if shield already deflects all attack
- And for above reasons:
    - Sword will be prioritised over bow
    - Shield will be prioritised over armour
- Using sword takes precedence over enemy's armour - amoured enemy will still instantly be destroyed
- An enemy's hits will also deduct health points from player, and not allied mercenaries

// STATIC ENTITIES ///
- Boulders can be pushed onto other items/moving entites
- Boulders never exist on the edge of the dungeon (i.e. there is always a wall border)


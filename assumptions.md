/// ASSUMPTIONS ///
Kelly, Cyrus, Josh & Sami
Team Avocado

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

/// PLAYER ///
- Original starting health is 10, unless hard mode - then is 7
- Weapons get "used" when used to destroy zombie toast spawner
- Only the most recently used potion state is active if one potion is used before another potion is finished

/// MOVING ///
- Player cannot move on top of zombie toast spawner
- Zombies & mercenaries are also able to push boulders, etc. -> can do everything a player can do, except open a door
- Items are picked after a player finishes a fight with enemies in the current position
- If there is a boulder above the spider's spawning position, the spider will not move up until it is clear

/// BATTLES ///
- Player and enemy health point deductions occur simultaneously (i.e. doesn't matter if player or enemy strikes first, result will be the same)
- Only one weapon and one protection item can be used per round -> no point using bow if sword already instantly kills, and no point using armour if shield already deflects all attack
- And for above reasons:
    - Sword will be prioritised over bow
    - Shield will be prioritised over armour
- Using sword takes precedence over enemy's armour - amoured enemy will still instantly be destroyed
- An enemy's hits will also deduct health points from player, and not allied mercenaries

/// ENEMIES ///
- Zombie Toast has 25% chance spawning with armour
- Mercenary has 50% chance spawning with armour

/// MERCENARIES ///
- Can be bribed using 1 treasure
- Bribing only occurs if within 2 cardinally adjacent tiles of the original player position (e.g. cannot bribe 1 square diagonally)
- Battle radius is 3 positions
- When within radius of a fighting player, position is updated once per battle, not per round (i.e. to simulate moving twice)

// STATIC ENTITIES ///
- Boulders can be pushed onto other items/moving entites
- Boulders never exist on the edge of the dungeon (i.e. there is always a wall border)
- Only two portals can exist for the same colour 
- Only player can move through doors/portals etc. 
- If portal doesn't work, player will just step onto the portal 
- Only 1 player exists 
- Constants:
    - Standard zombieSpawnerTimer = 20 ticks 
    - Hard zombieSpawnerTimer = 15 ticks 
    - Peaceful zombieSpawnerTimer = 0 
- If there are no cardinally adjacent squares next to a zombie spawner, it won't spawn any zombies 
- The bomb does not explode if the boulder is already on a switch adjacent to it 
- Doors are created in conjunction to a key 
- When a zombie spawner is destroyed, it is removed off the map 
- Key must be an integer 
- There is only 1 exit


//////// MILESTONE 3 ASSUMPTIONS /////////
- Assume if sunstone can be used in place of a key/bribe, it will get used first (i.e. key or treasure will remain in inventory if exists)
- Where there is an option, sunstone is used last in crafting - we want to "save" it since it is the most valuable
- Sceptre only has 1 use
- Assume you need to decide to "use" the sceptre (like a potion)
<!-- - Assume sceptre effect only works on entities that are within 2 cardinally adjacent tiles (i.e. same distance as a bribe) -->
- Assume buildables can only be built (i.e. can't be picked up from the ground)
- Midnight Armour has same durability as armour (2 uses)
- Midnight Armour adds 2 attack damage points points to player if used as a weapon
- Midnight Armour has same functionaltiy as Armour if used as protection
- Use midnight shield last when player has multiple weapons/protection choices
- If we try to create midnight armour while there is a zombie in the dungeon, returns InvalidActionException
- Sword changed to take off 10 damage points
/// ITEMS ///

- Armour can only be attained by battling an armoured enemy
- Bows and shields can only be attained via crafting
- Durabilities:
Armour - 2
Sword - 3
Shield - 3
Bow - 2
- Bomb radius is less than or equal to 3 positions away
// assume a shield completely deflects an enemy's attack
// assume a sword can instantly destroy an enemy -> not specified in specc

/// ENEMIES ///
- Zombie Toast has 25% chance spawning with armour
- Mercenary has 50% chance spawning with armour

// assume position is updated once per battle, not per round - for mercenary move twice


/// PLAYER ///
- Original starting health is 10, unless hard mode - then is 7

/// BATTLES
// assume original health points at start of round are used in battle 
// prioritise using sword over bow
            // assume can only use one weapon per round - to save weapons e.g. no point using bow if sword already instantly kills
(assume sword > armour) -> health will still be set to 0
// assume mercenary fights even if enemies regardless of its current health -> see spec
 // prioritise using shield over armour
            // assume can only use one protection per round - to save protection e.g. no point using armour if shield already deflects all attack
            // assume enemy only hits the player, not mercernaries -> see spec

INVINCIBILITY
// assume that since they are already invincible, no need to use armour/shields in this state
    // assume character's health cannot decrease (spec doesn't explicitly say this)

/// ASSUMED CONSTANTS ///
- Invincibility/invisibility time limit = 10 ticks

// assume player can't walk on top of spawner
// assumes zombies + mercenaries can also push boulders, etc. -> everything a player can do except key/door (i.e. checkc valid move
// assume boulders never exist on the edge of the dungeon (i.e. there is always a wall border)
            // assume boulder can be pushed onto items/other moving entities)


// assume we pick items after we fight enemies

// assume only need to give 1 gold to be bribed
    // assume 2 cardinally adjacent tiles does NOT mean diagonally adjacent

// assume weapons get "used" when used to destroy zombie toast spawner

// assume sword is used before bow
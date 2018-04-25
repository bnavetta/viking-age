# The Viking Age

This is a game created for HIST 1210A: The Viking Age. In it, players
control a Viking chieftain and their network of settlements and ships.

You start at Oseberg, in Norway, with a single ship to your name. From
there, careful resource management and planning is the only way to survive,
let alone expand your wealth and power.

[Download here](https://github.com/bnavetta/viking-age/releases/download/v1.1/desktop-1.1.jar)!

## Gameplay

The map on the screen shows all units in the game, both yours and
those you can interact with. Below are information and controls
specific to whatever unit you have selected. To select a unit,
click on the corresponding dot on the map.

Units are color-coded:

* Yellow: one of your central places or settlements
* Blue: one of your ships. When a ship is selected, right-click
  on the map to navigate the ship to that location
* Red: a ship that is not yours, generally hostile
* Orange: an emporium, which you can trade with
* Purple: a town you can raid
* Black: a monastery or other religious site, which you can also raid

Selecting an unit that isn't yours shows a popup with its stats.

The leftmost column of the control panel contains the unit's name and health bar.
Next to that is a list of all resources it posesses. The rest of the controls
are different for ships and settlements:

### Ship Controls

The `Settle` button creates a new settlement near the site of the
ship. This requires the ship to have 1000 wood, 400 meat, 200 iron, 400 wool,
300 mead, and 800 bread.

The `Trade Valuation` sliders determine what the ship will buy or sell
during trade. The ship is more eager to obtain high-valued items and more willing
to trade away low-value items. In general, set the value high for things you are
trying to get and low for things you want to sell. Trade happens
automatically when you are in range of another trading unit, after
accumulating sufficient goodwill. 

### Central Place Controls

Next to the resources is a worker breakdown. The number of workers
devoted to a particular task is set with the two buttons next to the
current number. Idle workers are not assigned to a task - unless some resource
is exhausted, population growth adds to idle workers. If production of enough
resources is too low, idle workers will start to die.

The `Port` lists all of your ships that are close enough to be considered
docked. Clicking on a ship opens a menu where you can transfer resources
between the ship and the central place. There is also a `Repair` button,
to heal damage done to the ship. This uses wood, wool, and iron from
the settlement.

Finally, there is the `Build Ship` button. This creates a new ship
near the settlement. Doing so uses 400 wood, 200 iron, and 500 wool.

### Raiding and Combat

When one of your ships is close enough to a non-friendly unit, it will
start to attack and/or be attacked. Both will take damage according to their
relative health while in proximity, until one dies. Towns are not removed when
dead - instead, their health is set to a low amount. This means that they can
be attacked again, but they will also continue to defend against your attacks.
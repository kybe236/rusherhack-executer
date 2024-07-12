# Command Structure
### -***executer:**
**delay:** delay between commands
**command:** executes the given string but does it for every player and replaces \<player> with the coresponding player. Also see below for more \<> Operator
**includeSelf:** Should it also run for yourself?

### -***executerMessage:** Same as above, but sends a chat message instead of a command.
SAME AS ABOVE


### -***executeronce:** Executes the give command once and does not fill \<player>
**command:** the command for full list of \<> option see below

### -***executeroncemsg:** The same as above, but sends a chat message instead of a command.
SAME AS ABOVE

### -***executercancel:** Cancels all execution


# <> Operator
## Only Executer
### - \<player> gets the name of the player
### -\<uuid> gets the uuid of the player

## Everywhere
### - \<location> gets the player's position in the format "X Y Z"
### - \<x> \<y> \<z> gets the corresponding coordinates of yourself
### - \<yaw> <pitch> gets the pitch or yaw of yourself
### - \<health> gets the health of yourself
### - \<food> gets the food level of yourself.
### - \<saturation> gets the sturation level of yourself
### - \<xp> gets the current decimal number of how many xp you have until level up.
### - \<player_s> gets the name of yourself
### - \<uuid_s> gets the uuid of yourself
### - \<server_ip> gets the uuid of the server

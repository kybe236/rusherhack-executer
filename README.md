### [CHANGELOG](CHANGELOG.md)
# Command Structure
### -***executer:**
**delay:** delay between commands
**command:** executes the given string but does it for every player and replaces \<player> with the coresponding player. Also see below for more \<> Operator
**includeSelf:** Should it also run for yourself?
**useIgnoreList:** Should it use the IgnoreList
**msg** Should it send a chat message instead of a command

### -***executeronce:** Executes the give command once and does not fill \<player>
**command:** the command for full list of \<> option see below
**msg** Should it send a chat message instead of a command

### -***executercancel:** Cancels all execution

### -***executerignorelist:**
- ***executerignorelist** add player: adds the player
- ***executerignorelist** remove player: removes the player
- ***executerignorelist** clear: clears the list
- ***executerignorelist** list: list the ignore list

### -***executerloop:** Loops the given command
**delay:** delay between commands
**command:** executes the given string but does it for every player and replaces \<player> with the coresponding player. Also see below for more \<> Operator
**includeSelf:** Should it also run for yourself?

# <> Operator
## Only Executer
### - \<player> gets the name of the player
### -\<uuid> gets the uuid of the player
### -\<ping> gets the ping of the player
### -\<gamemode> gets the gamemode of the player
### -\<skin> gets the skin of the player

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
### - \<nbt> gets the nbt of your held item
### - \<nbt_off> gets the nbt of your offhand item
### - \<ping_s> gets the ping of yourself
### - \<gamemode_s> gets the gamemode of yourself
### - \<skin_s> gets the skin of yourself
### - \<tablistname_s> gets the tablist name of the player
### - \<ping> gets the ping of the player
### - \<gamemode> gets the gamemode of the player
### - \<skin> gets the skin of the player
### - \<tps> gets the tps of the server
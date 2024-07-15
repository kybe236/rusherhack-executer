# 5.0.0:
### Commands
- #### Added ***executerloop:** Loops the given command
  - **delay:** delay between commands
  - **command:** executes the given string but does it for every player and replaces \<player> with the coresponding player. Also see below for more \<> Operator
  - **includeSelf:** Should it also run for yourself?
- #### Removed ***executermsg:**
  - Replaced with an msg flag on ***executer:**
- #### Removed ***executeroncemsg:**
  - Replaced with an msg flag on ***executeronce:**
### <> Operator
- #### Added \<tps> gets the tps of the server
- #### Added \<skin_s> gets the skin of yourself
- #### Added \<gamemode_s> gets the gamemode of yourself
- #### Added \<ping_s> gets the ping of yourself
- #### Added \<tablistname> gets the tablist name of the player
- #### Added \<ping> gets the ping of the player
- #### Added \<gamemode> gets the gamemode of the player
- #### Added \<skin> gets the skin of the player
- #### Added \<tablistname_s> gets the tablist name of yourself
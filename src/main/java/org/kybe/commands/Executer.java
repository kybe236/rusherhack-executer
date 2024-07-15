package org.kybe.commands;

import net.minecraft.ChatFormatting;
import net.minecraft.SharedConstants;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.world.entity.player.Player;
import org.kybe.Main;
import org.rusherhack.client.api.RusherHackAPI;
import org.rusherhack.client.api.feature.command.Command;
import org.rusherhack.client.api.utils.ChatUtils;
import org.rusherhack.core.command.AbstractCommand;
import org.rusherhack.core.command.annotations.CommandExecutor;

import java.nio.charset.IllegalCharsetNameException;
import java.util.*;

public class Executer extends Command {

    public boolean cancel = false;

    public Executer() {
        super("executer", "executes a command <player> is replaced with all players");
    }

    @CommandExecutor
    @CommandExecutor.Argument({"delay", "command", "includeSelf", "useExcludeList", "msg" })
    private void executer(int delay, String command, Optional<Boolean> includeSelf, Optional<Boolean> useExcludeList, Optional<Boolean> msg) {
        cancel = false;
        ChatUtils.print("Executing command: " + command + " with a delay of " + delay + " ticks" + (includeSelf.isPresent() && includeSelf.get() ? " including self" : "")); // Print the command and delay

        ClientPacketListener connection = mc.getConnection(); // Get the connection
        if (connection == null) {
            ChatUtils.print("You are not on a server");
            return;
        }

        Collection<PlayerInfo> playerList = connection.getOnlinePlayers(); // Get all online players
        /*
            * Check if there are any players online
         */
        if (playerList.isEmpty()) {
            ChatUtils.print("No players online");
            return;
        }

        delay = delay * 50;         // Convert delay ticks to ms
        int delayAccumulator = 0;   // Accumulate the delay for each player

        Timer timer = new Timer();  // Create a timer to schedule the command

        /*
         * Iterate through all players and schedule the command to be executed after the delay
         */
        for (PlayerInfo playerInfo : playerList) {

            /*
             * Skip the player if the player is the same as the player executing the command or null
             */
            String playerName = playerInfo.getProfile().getName();
            delayAccumulator += delay;  // Increment the delay for each player
            /*
             * Skip self if includeSelf is false
             */
            if (includeSelf.isPresent() && !includeSelf.get()) {
                Player player = mc.player;
                if (player != null && player.getUUID().equals(playerInfo.getProfile().getId())) {
                    continue;
                }
            }
            /*
             * skip player if player is in the ignore list if useExcludeList is true
             */
            if (useExcludeList.isPresent() && useExcludeList.get()) {
                AbstractCommand executer = RusherHackAPI.getCommandManager().getFeature("executerignorelist").orElse(null);
                if (executer == null) {
                    ChatUtils.print("ExecuterIgnoreList not found");
                    return;
                }
                ArrayList<String> ignoredPlayers = (ArrayList<String>) ((ExecuterIgnoreList) executer).ignoredPlayers;
                if (ignoredPlayers.contains(playerName)) {
                    continue;
                }
            }

            /*
             * Schedule the command to be executed after the delay
             */
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    // checks if the command should be cancelled
                    if (cancel) {
                        cancel = false;
                        timer.cancel();
                        timer.purge();
                        return;
                    }

                    /*
                     * Do formatting that requires the player object
                     */
                    String replacedCommand = command.replace("<player>", playerName);
                    replacedCommand = replacedCommand.replace("<uuid>",playerInfo.getProfile().getId().toString());
                    replacedCommand = replacedCommand.replace("<ping>", String.valueOf(playerInfo.getLatency()));
                    replacedCommand = replacedCommand.replace("<gamemode>" , playerInfo.getGameMode().getName());
                    replacedCommand = replacedCommand.replace("<skin>", Objects.requireNonNull(playerInfo.getSkin().textureUrl()));
                    String TabListName = Objects.requireNonNull(playerInfo.getTabListDisplayName()).getString();
                    System.out.println(TabListName);
                    replacedCommand = replacedCommand.replace("<tablistname>", SharedConstants.filterText(TabListName, false));
                    /*
                     * Do formatting that doesn't require the player object
                     */
                    Main.format(replacedCommand);


                    String finalReplacedCommand = replacedCommand; // Final replaced command
                    /*
                     * Check if the command is too long
                     */
                    if (finalReplacedCommand.length() > 256) {
                        ChatUtils.print("Command too long for player " + playerName);
                        return;
                    }

                    /*
                     * Send the command to the server
                     */
                    mc.execute(() -> {
                        try {
                            assert mc.player != null;
                            if (msg.isPresent() && msg.get()) {
                                mc.player.connection.sendChat(finalReplacedCommand);
                            } else {
                                ChatUtils.print("Sending command: " + finalReplacedCommand);
                                mc.player.connection.sendCommand(finalReplacedCommand);
                            }
                        } catch (Exception e) {
                            ChatUtils.print("Error executing command for player " + playerName + ": " + e.getMessage());
                        }
                    });
                }
            }, delayAccumulator);
            delayAccumulator += delay; // Increment the delay for each player
        }
    }
}

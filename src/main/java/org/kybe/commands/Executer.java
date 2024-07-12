package org.kybe.commands;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.world.entity.player.Player;
import org.rusherhack.client.api.feature.command.Command;
import org.rusherhack.client.api.utils.ChatUtils;
import org.rusherhack.core.command.annotations.CommandExecutor;

import java.sql.Time;
import java.util.Collection;
import java.util.Timer;
import java.util.TimerTask;

public class Executer extends Command {

    public boolean cancel = false;

    public Executer() {
        super("executer", "executes a command <player> is replaced with all players");
    }

    @CommandExecutor
    @CommandExecutor.Argument({"delay", "command", "includeSelf"})
    private void executer(int delay, String command, boolean includeSelf) {
        cancel = false;
        ChatUtils.print("Executing command: " + command + " with a delay of " + delay + " ms" + (includeSelf ? " including self" : "")); // Print the command and delay

        ClientPacketListener connection = Minecraft.getInstance().getConnection(); // Get the connection
        if (connection == null) {
            ChatUtils.print("You are not on a server");
            return;
        }

        Collection<PlayerInfo> playerList = connection.getOnlinePlayers(); // Get all online players
        if (playerList.isEmpty()) {
            ChatUtils.print("No players online");
            return;
        } // Check if there are any players online

        delay = delay * 50;         // Convert delay ticks to ms
        int delayAccumulator = 0;   // Accumulate the delay for each player

        Timer timer = new Timer();

        /*
         * Iterate through all players and schedule the command to be executed after the delay
         */
        for (PlayerInfo playerInfo : playerList) {

            /*
             * Skip the player if the player is the same as the player executing the command or null
             */
            String playerName = playerInfo.getProfile().getName();
            delayAccumulator += delay;  // Increment the delay for each player
            if (!includeSelf) {
                Player player = Minecraft.getInstance().player;
                if (player != null && player.getUUID().equals(playerInfo.getProfile().getId())) {
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
                    String replacedCommand = command.replace("<player>", playerName);
                    replacedCommand = replacedCommand.replace("<location>", Minecraft.getInstance().player.getX() + " " + Minecraft.getInstance().player.getY() + " " + Minecraft.getInstance().player.getZ());
                    replacedCommand = replacedCommand.replace("<x>", String.valueOf(Minecraft.getInstance().player.getX()));
                    replacedCommand = replacedCommand.replace("<y>", String.valueOf(Minecraft.getInstance().player.getY()));
                    replacedCommand = replacedCommand.replace("<z>", String.valueOf(Minecraft.getInstance().player.getZ()));
                    replacedCommand = replacedCommand.replace("<yaw>", String.valueOf(Minecraft.getInstance().player.getYRot()));
                    replacedCommand = replacedCommand.replace("<pitch>", String.valueOf(Minecraft.getInstance().player.getXRot()));
                    replacedCommand = replacedCommand.replace("<health>", String.valueOf(Minecraft.getInstance().player.getHealth()));
                    replacedCommand = replacedCommand.replace("<food>", String.valueOf(Minecraft.getInstance().player.getFoodData().getFoodLevel()));
                    replacedCommand = replacedCommand.replace("<saturation>", String.valueOf(Minecraft.getInstance().player.getFoodData().getSaturationLevel()));
                    replacedCommand = replacedCommand.replace("<xp>", String.valueOf(Minecraft.getInstance().player.experienceProgress));
                    replacedCommand = replacedCommand.replace("<player_s>", Minecraft.getInstance().player.getGameProfile().getName());
                    replacedCommand = replacedCommand.replace("<uuid>",playerInfo.getProfile().getId().toString());
                    replacedCommand = replacedCommand.replace("<uuid_s>", Minecraft.getInstance().player.getGameProfile().getId().toString());
                    replacedCommand = replacedCommand.replace("<server_ip>", Minecraft.getInstance().getCurrentServer() != null ? Minecraft.getInstance().getCurrentServer().ip : "null");
                    //! only 1.20.4 or below
                    replacedCommand = replacedCommand.replace("<nbt>", Minecraft.getInstance().player.getInventory().getSelected().getTag().toString());
                    replacedCommand = replacedCommand.replace("<nbt_off>", Minecraft.getInstance().player.getOffhandItem().getTag().toString());

                    String finalReplacedCommand = replacedCommand;
                    if (finalReplacedCommand.length() > 256) {
                        ChatUtils.print("Command too long for player " + playerName);
                        return;
                    }
                    Minecraft.getInstance().execute(() -> {
                        try {
                            ChatUtils.print("Sending command: " + finalReplacedCommand);
                            Minecraft.getInstance().player.connection.sendCommand(finalReplacedCommand);
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

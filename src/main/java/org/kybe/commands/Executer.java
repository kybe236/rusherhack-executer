package org.kybe.commands;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.world.entity.player.Player;
import org.rusherhack.client.api.feature.command.Command;
import org.rusherhack.client.api.utils.ChatUtils;
import org.rusherhack.core.command.annotations.CommandExecutor;

import java.util.Collection;
import java.util.Timer;
import java.util.TimerTask;

public class Executer extends Command {
    public Executer() {
        super("executer", "executes a command <player> is replaced with all players");
    }

    @CommandExecutor
    @CommandExecutor.Argument({"delay", "command", "includeSelf"})
    private void executer(int delay, String command, boolean includeSelf) {
        ChatUtils.print("Executing command: " + command + " with a delay of " + delay + " ms" + (includeSelf ? " including self" : ""));
        ClientPacketListener connection = Minecraft.getInstance().getConnection();
        if (connection == null) {
            ChatUtils.print("You are not on a server");
            return;
        }

        Collection<PlayerInfo> playerList = connection.getOnlinePlayers();
        if (playerList.isEmpty()) {
            ChatUtils.print("No players online");
            return;
        }

        Timer timer = new Timer();
        delay = delay * 50;  // Convert delay ticks to ms
        int delayAccumulator = 0;

        for (PlayerInfo playerInfo : playerList) {
            String playerName = playerInfo.getProfile().getName();
            delayAccumulator += delay;  // Increment the delay for each player
            if (!includeSelf) {
                Player player = Minecraft.getInstance().player;
                if (player != null && player.getUUID().equals(playerInfo.getProfile().getId())) {
                    continue;
                }
            }
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    /*
                        * Replace the <player> placeholder with the player's name and execute the command
                     */
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
                    replacedCommand = replacedCommand.replace("<stats>", Minecraft.getInstance().player.getStats().toString());
                    replacedCommand = replacedCommand.replace("<xp>", String.valueOf(Minecraft.getInstance().player.experienceProgress));
                    replacedCommand = replacedCommand.replace("<player_s>", Minecraft.getInstance().player.getGameProfile().getName());
                    replacedCommand = replacedCommand.replace("<uuid>", Minecraft.getInstance().player.getGameProfile().getId().toString());
                    replacedCommand = replacedCommand.replace("<server_ip>", Minecraft.getInstance().getCurrentServer() != null ? Minecraft.getInstance().getCurrentServer().ip : "null");


                    String finalReplacedCommand = replacedCommand;
                    Minecraft.getInstance().execute(() -> {
                        try {
                            ChatUtils.print("Sending command: " + finalReplacedCommand);
                            assert Minecraft.getInstance().player != null;
                            Minecraft.getInstance().player.connection.sendCommand(finalReplacedCommand);
                        } catch (Exception e) {
                            ChatUtils.print("Error executing command for player " + playerName + ": " + e.getMessage());
                        }
                    });
                }
            }, delayAccumulator);
        }
    }
}

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
        if (playerList == null || playerList.isEmpty()) {
            ChatUtils.print("No players online");
            return;
        }
        if (includeSelf) {
            Player player = Minecraft.getInstance().player;
            playerList.removeIf(playerInfo -> playerInfo.getProfile().getId().equals(player.getUUID()));
        }

        Timer timer = new Timer();
        delay = delay * 50;  // Convert delay ticks to ms
        int delayAccumulator = 0;

        for (PlayerInfo playerInfo : playerList) {
            String playerName = playerInfo.getProfile().getName();
            delayAccumulator += delay;  // Increment the delay for each player

            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    String replacedCommand = command.replace("<player>", playerName);
                    Minecraft.getInstance().execute(() -> {
                        try {
                            ChatUtils.print("Sending command: " + replacedCommand);
                            assert Minecraft.getInstance().player != null;
                            Minecraft.getInstance().player.connection.sendCommand(replacedCommand);
                        } catch (Exception e) {
                            ChatUtils.print("Error executing command for player " + playerName + ": " + e.getMessage());
                        }
                    });
                }
            }, delayAccumulator);
        }
    }
}

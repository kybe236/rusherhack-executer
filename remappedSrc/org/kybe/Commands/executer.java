package org.kybe.Commands;

import org.rusherhack.client.api.feature.command.Command;
import org.rusherhack.client.api.utils.ChatUtils;
import org.rusherhack.core.command.annotations.CommandExecutor;

import javax.swing.text.html.parser.Entity;
import java.util.TimerTask;
import java.util.logging.Level;

public class executer extends Command {
    public executer() {
        super("execter", "executes a command <player> is replaced with all players");
    }

    @CommandExecutor
    @CommandExecutor.Argument({"delay", "command"})
    private void excuter(int delay, String command) {

        String[] players = mc.world.getServer().getPlayerNames();
        if (players.length == 0) {
            return;
        }

        for (String player : players) {
            new java.util.Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                }
            }, delay);
        }
    }
}

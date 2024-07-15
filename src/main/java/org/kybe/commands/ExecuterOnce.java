package org.kybe.commands;

import org.kybe.Main;
import org.rusherhack.client.api.feature.command.Command;
import org.rusherhack.client.api.utils.ChatUtils;
import org.rusherhack.core.command.annotations.CommandExecutor;

import java.util.Optional;


public class ExecuterOnce extends Command {
    public ExecuterOnce() {
        super("executeronce", "sends an command with some completion");
    }

    @CommandExecutor
    @CommandExecutor.Argument({"command", "msg"})
    private void executer(String command, Optional<Boolean> msg) {
        ChatUtils.print("Executing command: " + command); // Print the command


        String replacedCommand = Main.format(command);

        /*
         * Check if the command is too long
         */
        if (replacedCommand.length() > 256) {
            ChatUtils.print("Command too long");
            return;
        }

        try {
            assert mc.player != null;
            /*
             * Send the msg or command to the server
             */
            if (msg.isPresent() && msg.get()) {
                mc.player.connection.sendChat(replacedCommand);
            } else {
                mc.player.connection.sendCommand(replacedCommand);
            }
        } catch (Exception e) {
            ChatUtils.print("Error executing command " + command + ": " + e.getMessage());
        }
    }
}

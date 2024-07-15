package org.kybe.commands;

import org.kybe.Main;
import org.rusherhack.client.api.feature.command.Command;
import org.rusherhack.client.api.utils.ChatUtils;
import org.rusherhack.core.command.annotations.CommandExecutor;

import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;

public class ExecuterLoop extends Command {
    boolean cancel = false;

    public ExecuterLoop() {
        super("executerloop", "sends an command with some completion over and over again");
    }

    @CommandExecutor
    @CommandExecutor.Argument({"command", "delay", "msg"})
    private void executer(String command, int delay, Optional<Boolean> msg) {
        cancel = false;
        ChatUtils.print("Executing command: " + command + " with a delay of " + delay + " ticks");

        delay = delay * 50;     // ticks to ms

        /*
         * This is a timer that will execute the command every delay ticks
         */
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (cancel) {
                    timer.cancel();
                    return;
                }

                ChatUtils.print("Executing command: " + command);
                String replacedCommand = Main.format(command);


                if (replacedCommand.length() > 256) {       // check if the msg/command is greater as 256 (max length)
                    if (msg.isPresent() && msg.get()) {
                        ChatUtils.print("Message too long");
                        return;
                    }
                    ChatUtils.print("Command too long");
                    return;
                }

                /*
                 * Send the msg or command to the server
                 */
                try {
                    if (msg.isPresent() && msg.get()){
                        assert mc.player != null;
                        mc.player.connection.sendChat(replacedCommand);
                    } else {
                        assert mc.player != null;
                        mc.player.connection.sendCommand(replacedCommand);
                    }
                } catch (Exception e) {
                    ChatUtils.print("Error executing command " + command + ": " + e.getMessage());
                }
            }
        }, 0, delay);
    }
}

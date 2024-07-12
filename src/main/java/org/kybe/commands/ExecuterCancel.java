package org.kybe.commands;

import org.rusherhack.client.api.RusherHackAPI;
import org.rusherhack.client.api.feature.command.Command;
import org.rusherhack.client.api.utils.ChatUtils;
import org.rusherhack.core.command.AbstractCommand;
import org.rusherhack.core.command.annotations.CommandExecutor;

public class ExecuterCancel extends Command {
    public ExecuterCancel() {
        super("executercancel", "Cancels the execution of a command");
    }

    @CommandExecutor
    private void executerCancel() {
        AbstractCommand executer = RusherHackAPI.getCommandManager().getFeature("executer").orElse(null);
        if (executer == null) {
            ChatUtils.print("Executer not found");
            return;
        }
        ((Executer) executer).cancel = true;

        AbstractCommand command = RusherHackAPI.getCommandManager().getFeature("executermsg").orElse(null);
        if (command == null) {
            ChatUtils.print("ExecuterMsg not found");
            return;
        }
        ((ExecuterMessage) command).cancel = true;
    }
}

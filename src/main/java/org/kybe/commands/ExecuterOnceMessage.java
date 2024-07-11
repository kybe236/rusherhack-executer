package org.kybe.commands;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import org.rusherhack.client.api.feature.command.Command;
import org.rusherhack.client.api.utils.ChatUtils;
import org.rusherhack.core.command.annotations.CommandExecutor;


public class ExecuterOnceMessage extends Command {
    public ExecuterOnceMessage() {
        super("executeroncemsg", "executes a command with some completion");
    }

    @CommandExecutor
    @CommandExecutor.Argument("command")
    private void executer(String command) {
        ChatUtils.print("Executing command: " + command);
        ClientPacketListener connection = Minecraft.getInstance().getConnection();
        if (connection == null) {
            ChatUtils.print("You are not on a server");
            return;
        }
        String replacedCommand = command.replace("<location>", Minecraft.getInstance().player.getX() + " " + Minecraft.getInstance().player.getY() + " " + Minecraft.getInstance().player.getZ());
        replacedCommand = replacedCommand.replace("<x>", String.valueOf(Minecraft.getInstance().player.getX()));
        replacedCommand = replacedCommand.replace("<y>", String.valueOf(Minecraft.getInstance().player.getY()));
        replacedCommand = replacedCommand.replace("<z>", String.valueOf(Minecraft.getInstance().player.getZ()));
        replacedCommand = replacedCommand.replace("<yaw>", String.valueOf(Minecraft.getInstance().player.getYRot()));
        replacedCommand = replacedCommand.replace("<pitch>", String.valueOf(Minecraft.getInstance().player.getXRot()));
        replacedCommand = replacedCommand.replace("<health>", String.valueOf(Minecraft.getInstance().player.getHealth()));
        replacedCommand = replacedCommand.replace("<food>", String.valueOf(Minecraft.getInstance().player.getFoodData().getFoodLevel()));
        replacedCommand = replacedCommand.replace("<xp>", String.valueOf(Minecraft.getInstance().player.experienceProgress));

        try {
            assert Minecraft.getInstance().player != null;
            Minecraft.getInstance().player.connection.sendChat(replacedCommand);
        } catch (Exception e) {
            ChatUtils.print("Error executing command " + command + ": " + e.getMessage());
        }
    }
}

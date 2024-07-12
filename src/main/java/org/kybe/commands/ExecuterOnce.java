package org.kybe.commands;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import org.rusherhack.client.api.RusherHackAPI;
import org.rusherhack.client.api.feature.command.Command;
import org.rusherhack.client.api.utils.ChatUtils;
import org.rusherhack.core.command.annotations.CommandExecutor;


public class ExecuterOnce extends Command {
    public ExecuterOnce() {
        super("executeronce", "sends an command with some completion");
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
        replacedCommand = replacedCommand.replace("<saturation>", String.valueOf(Minecraft.getInstance().player.getFoodData().getSaturationLevel()));
        replacedCommand = replacedCommand.replace("<xp>", String.valueOf(Minecraft.getInstance().player.experienceProgress));
        replacedCommand = replacedCommand.replace("<player_s>", Minecraft.getInstance().player.getGameProfile().getName());
        replacedCommand = replacedCommand.replace("<uuid_s>", Minecraft.getInstance().player.getGameProfile().getId().toString());
        replacedCommand = replacedCommand.replace("<server_ip>", Minecraft.getInstance().getCurrentServer() != null ? Minecraft.getInstance().getCurrentServer().ip : "null");
        //! only 1.20.4 or below
        replacedCommand = replacedCommand.replace("<nbt>", Minecraft.getInstance().player.getInventory().getSelected().getTag().toString());
        replacedCommand = replacedCommand.replace("<nbt_off>", Minecraft.getInstance().player.getOffhandItem().getTag().toString());

        if (replacedCommand.length() > 256) {
            ChatUtils.print("Command too long");
            return;
        }
        try {
            assert Minecraft.getInstance().player != null;
            Minecraft.getInstance().player.connection.sendCommand(replacedCommand);
        } catch (Exception e) {
            ChatUtils.print("Error executing command " + command + ": " + e.getMessage());
        }
    }
}

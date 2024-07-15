package org.kybe;

import net.minecraft.SharedConstants;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.nbt.CompoundTag;
import org.kybe.commands.*;
import org.kybe.modules.ExecuterGatherData;
import org.rusherhack.client.api.RusherHackAPI;
import org.rusherhack.client.api.feature.module.IModule;
import org.rusherhack.client.api.plugin.Plugin;

import java.nio.charset.IllegalCharsetNameException;
import java.util.Objects;

public class Main extends Plugin {

    @Override
    public void onLoad() {
        System.out.println("[EXECUTER] Plugin loaded!");

        RusherHackAPI.getCommandManager().registerFeature(new Executer());
        RusherHackAPI.getCommandManager().registerFeature(new ExecuterOnce());
        RusherHackAPI.getCommandManager().registerFeature(new ExecuterCancel());
        RusherHackAPI.getCommandManager().registerFeature(new ExecuterIgnoreList());
        RusherHackAPI.getCommandManager().registerFeature(new ExecuterLoop());
        RusherHackAPI.getModuleManager().registerFeature(new ExecuterGatherData());
    }

    @Override
    public void onUnload() {
        System.out.println("[EXECUTER] Plugin unloaded!");
    }

    public static String format(String replacedCommand) {
        assert Minecraft.getInstance().player != null;
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
        replacedCommand = replacedCommand.replace("<uuid_s>", Minecraft.getInstance().player.getGameProfile().getId().toString());
        replacedCommand = replacedCommand.replace("<server_ip>", Minecraft.getInstance().getCurrentServer() != null ? Minecraft.getInstance().getCurrentServer().ip : "null");
        /*
         * Get the TPS from the ExecuterGatherData module
         */
        IModule abstractCommand = RusherHackAPI.getModuleManager().getFeature("ExecuterGatherData").isPresent() ? RusherHackAPI.getModuleManager().getFeature("ExecuterGatherData").get() : null;
        if (abstractCommand != null) {
            replacedCommand = replacedCommand.replace("<tps>", String.valueOf(ExecuterGatherData.tps));
        }
        /*
         * Get the player info from PlayerInfo
         */
        PlayerInfo playerInfo = Objects.requireNonNull(Minecraft.getInstance().getConnection()).getPlayerInfo(Minecraft.getInstance().player.getUUID());
        if (playerInfo != null) {
            replacedCommand = replacedCommand.replace("<ping_s>", String.valueOf(playerInfo.getLatency()));
            replacedCommand = replacedCommand.replace("<gamemode_s>" , playerInfo.getGameMode().getName());
            replacedCommand = replacedCommand.replace("<skin_s>", Objects.requireNonNull(playerInfo.getSkin().textureUrl()));
            String TabListName = Objects.requireNonNull(playerInfo.getTabListDisplayName()).getString();
            System.out.println(TabListName);
            replacedCommand = replacedCommand.replace("<tablistname_s>", SharedConstants.filterText(TabListName, false));
        }
        //! only 1.20.4 or below
        CompoundTag nbt = Minecraft.getInstance().player.getInventory().getSelected().getTag();
        String nbtString;
        if (nbt == null) {
            nbtString = "{NULL}";
        } else {
            nbtString = nbt.toString();
        }
        replacedCommand = replacedCommand.replace("<nbt>", nbtString);
        CompoundTag nbtOff = Minecraft.getInstance().player.getOffhandItem().getTag();
        if (nbtOff == null) {
            nbtString = "{NULL}";
        } else {
            nbtString = nbtOff.toString();
        }
        replacedCommand = replacedCommand.replace("<nbt_off>", nbtString);
        return replacedCommand;
    }
}

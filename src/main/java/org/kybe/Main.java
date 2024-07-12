package org.kybe;

import com.mojang.realmsclient.dto.PlayerInfo;
import org.kybe.commands.Executer;
import org.kybe.commands.ExecuterMessage;
import org.kybe.commands.ExecuterOnce;
import org.kybe.commands.ExecuterOnceMessage;
import org.rusherhack.client.api.RusherHackAPI;
import org.rusherhack.client.api.plugin.Plugin;

public class Main extends Plugin {

    @Override
    public void onLoad() {
        System.out.println("[EXECUTER] Plugin loaded!");

        RusherHackAPI.getCommandManager().registerFeature(new Executer());
        RusherHackAPI.getCommandManager().registerFeature(new ExecuterMessage());
        RusherHackAPI.getCommandManager().registerFeature(new ExecuterOnce());
        RusherHackAPI.getCommandManager().registerFeature(new ExecuterOnceMessage());
    }

    @Override
    public void onUnload() {
        System.out.println("[EXECUTER] Plugin unloaded!");
    }

    public void onTick() {
        // This method is called every tick
    }
}

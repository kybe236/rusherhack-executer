package org.kybe;

import org.kybe.commands.*;
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
        RusherHackAPI.getCommandManager().registerFeature(new ExecuterCancel());
    }

    @Override
    public void onUnload() {
        System.out.println("[EXECUTER] Plugin unloaded!");
    }
}

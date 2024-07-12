package org.kybe;

import org.kybe.commands.executer;
import org.rusherhack.client.api.RusherHackAPI;
import org.rusherhack.client.api.plugin.Plugin;

public class Main extends Plugin {

    @Override
    public void onLoad() {
        System.out.println("[EXECUTER] Plugin loaded!");

        RusherHackAPI.getCommandManager().registerFeature(new executer());
    }

    @Override
    public void onUnload() {
        System.out.println("[EXECUTER] Plugin unloaded!");
    }

    public void onTick() {
        // This method is called every tick
    }
}

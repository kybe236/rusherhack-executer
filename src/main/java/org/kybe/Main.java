package org.kybe;

import org.kybe.Commands.executer;
import org.rusherhack.client.api.RusherHackAPI;
import org.rusherhack.client.api.plugin.Plugin;

public class Main extends Plugin {

    @Override
    public void onLoad() {
        System.out.println("[ANTI-BOOK-BAN] Plugin loaded!");

        RusherHackAPI.getCommandManager().registerFeature(new executer());
    }

    @Override
    public void onUnload() {
        System.out.println("[ANTI-BOOK-BAN] Plugin unloaded!");
    }

    public void onTick() {
        // This method is called every tick
    }
}

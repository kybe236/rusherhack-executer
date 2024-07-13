package org.kybe.commands;

import org.rusherhack.client.api.feature.command.Command;
import org.rusherhack.client.api.utils.ChatUtils;
import org.rusherhack.core.command.annotations.CommandExecutor;

import java.util.ArrayList;
import java.util.List;

public class ExecuterIgnoreList extends Command {
    public List<String> ignoredPlayers = new ArrayList<>();

    public ExecuterIgnoreList() {
        super("executerignorelist", "Ignores some users");
    }


    @CommandExecutor(subCommand = "add")
    @CommandExecutor.Argument("player")
    private void executerIgnore(String player) {
        ChatUtils.print("Ignoring player: " + player);
        ignoredPlayers.add(player);
    }

    @CommandExecutor(subCommand = "remove")
    @CommandExecutor.Argument("player")
    private void executerUnignore(String player) {
        ChatUtils.print("Unignoring player: " + player);
        ignoredPlayers.remove(player);
    }

    @CommandExecutor(subCommand = "list")
    private void executerList() {
        ChatUtils.print("Ignored players: " + ignoredPlayers);
    }

    @CommandExecutor(subCommand = "clear")
    private void executerClear() {
        ChatUtils.print("Cleared ignored players");
        ignoredPlayers.clear();
    }

    @CommandExecutor(subCommand = "help")
    private void executerHelp() {
        ChatUtils.print("Commands: ");
        ChatUtils.print("add <player> - Adds a player to the ignore list");
        ChatUtils.print("remove <player> - Removes a player from the ignore list");
        ChatUtils.print("list - Lists all ignored players");
        ChatUtils.print("clear - Clears the ignore list");
    }
}

package org.kybe.commands;

import org.rusherhack.client.api.feature.command.Command;
import org.rusherhack.client.api.feature.command.arg.PlayerReference;
import org.rusherhack.client.api.utils.ChatUtils;
import org.rusherhack.core.command.annotations.CommandExecutor;

import java.util.ArrayList;
import java.util.List;

public class ExecuterIgnoreList extends Command {
    public List<String> ignoredPlayers = new ArrayList<>();

    public ExecuterIgnoreList() {
        super("executerignorelist", "Ignores some users");
    }

    /*
     * This is the command to add a player to the ignore list
     */
    @CommandExecutor(subCommand = "add")
    @CommandExecutor.Argument("player")
    private void executerIgnore(PlayerReference player) {
        ChatUtils.print("Ignoring player: " + player);
        ignoredPlayers.add(player.name());
    }

    /*
     * This is the command to remove a player from the ignore list
     */
    @CommandExecutor(subCommand = "remove")
    @CommandExecutor.Argument("player")
    private void executerUnignore(PlayerReference player) {
        ChatUtils.print("Unignoring player: " + player);
        ignoredPlayers.remove(player.name());
    }

    /*
     * This is the command to list all ignored players
     */
    @CommandExecutor(subCommand = "list")
    private void executerList() {
        ChatUtils.print("Ignored players: " + ignoredPlayers);
    }

    /*
     * This is the command to clear the ignore list
     */
    @CommandExecutor(subCommand = "clear")
    private void executerClear() {
        ChatUtils.print("Cleared ignored players");
        ignoredPlayers.clear();
    }

    /*
     * This is the help command
     */
    @CommandExecutor(subCommand = "help")
    private void executerHelp() {
        ChatUtils.print("§4§l[HELP] §r");
        ChatUtils.print("§4§l[HELP] §r/executerignorelist add <player> - Adds a player to the ignore list");
        ChatUtils.print("§4§l[HELP] §r/executerignorelist remove <player> - Removes a player from the ignore list");
        ChatUtils.print("§4§l[HELP] §r/executerignorelist list - Lists all ignored players");
        ChatUtils.print("§4§l[HELP] §r/executerignorelist clear - Clears the ignore list");
        ChatUtils.print("§4§l[HELP] §r");
    }
}

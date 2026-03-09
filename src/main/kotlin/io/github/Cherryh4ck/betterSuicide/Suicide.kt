package io.github.Cherryh4ck.betterSuicide

import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.entity.Player

class Suicide(private val plugin: BetterSuicide)  : CommandExecutor, TabCompleter {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender is Player) {
            if (!sender.isOp || label.lowercase() == "suicide"){
                sender.health = 0.0
            }
            else{
                if (!plugin.killDisabledToOps){
                    if (!args.isEmpty()){
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "minecraft:kill ${args[0]}")
                    }
                    else {
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "minecraft:kill ${sender.name}")
                    }
                }
                else{
                    sender.health = 0.0
                }
            }
        }
        return true
    }

    override fun onTabComplete(sender: CommandSender, command: Command, alias: String, args: Array<out String>): List<String>? {
        if (args.size == 1 && sender.isOp && alias.equals("kill", ignoreCase = true) && !plugin.killDisabledToOps) {
            return Bukkit.getOnlinePlayers()
                .map { it.name }
                .filter { it.startsWith(args[0], ignoreCase = true) }
        }

        return emptyList()
    }
}
package io.github.Cherryh4ck.betterSuicide

import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.entity.Player
import java.util.UUID

class Suicide(private val plugin: BetterSuicide)  : CommandExecutor, TabCompleter {
    private val cooldowns = HashSet<UUID>()
    private val ticksTimer = 20L * plugin.cooldown

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender is Player) {
            if (plugin.usePermissions && !sender.hasPermission("bettersuicide.suicide")) {
                sender.sendMessage(plugin.minimessage.deserialize(plugin.noPermissions))
                return true
            }

            if (plugin.enableCooldown && cooldowns.contains(sender.uniqueId) && !sender.hasPermission("bettersuicide.bypass")) {
                if (plugin.cooldownMessage) {
                    sender.sendMessage(plugin.minimessage.deserialize(plugin.onCooldown))
                }
                return true
            }
            sender.health = 0.0
            cooldowns.add(sender.uniqueId)
            Bukkit.getScheduler().runTaskLater(plugin, Runnable {
                cooldowns.remove(sender.uniqueId)
            }, ticksTimer)
        }
        return true
    }

    override fun onTabComplete(sender: CommandSender, command: Command, alias: String, args: Array<out String>): List<String>? {
        if (args.size == 1 && sender.isOp && alias.equals("kill", ignoreCase = true) && plugin.redirectKill) {
            return Bukkit.getOnlinePlayers()
                .map { it.name }
                .filter { it.startsWith(args[0], ignoreCase = true) }
        }

        return emptyList()
    }
}
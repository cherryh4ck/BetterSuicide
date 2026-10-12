package io.github.Cherryh4ck.betterSuicide

import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.entity.Player
import java.util.UUID

class Suicide(private val plugin: BetterSuicide)  : CommandExecutor, TabCompleter {
    private val cooldowns = HashMap<UUID, Long>()

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender is Player) {
            if (plugin.usePermissions && !sender.hasPermission("bettersuicide.suicide")) {
                sender.sendMessage(plugin.minimessage.deserialize(plugin.noPermissions))
                return true
            }
            if (plugin.enableCooldown && !sender.hasPermission("bettersuicide.bypass")) {
                val now = System.currentTimeMillis()
                if (now < (cooldowns[sender.uniqueId] ?: 0L)) {
                    if (plugin.cooldownMessage) {
                        sender.sendMessage(plugin.minimessage.deserialize(plugin.onCooldown))
                    }
                    return true
                }
                cooldowns[sender.uniqueId] = now + plugin.cooldown * 1000L
            }
            if (plugin.enableGlobalDeathMessage) {
                Bukkit.getServer().sendMessage(plugin.minimessage.deserialize(plugin.globalDeathMessage.replace("{player}", sender.name)))
            }

            sender.health = 0.0
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
package io.github.Cherryh4ck.betterSuicide

import org.bukkit.command.Command
import org.bukkit.command.CommandSender

class AliasCommand(name: String, private val handler: Suicide) : Command(name) {
    override fun execute(sender: CommandSender, commandLabel: String, args: Array<out String>): Boolean {
        return handler.onCommand(sender, this, commandLabel, args)
    }

    override fun tabComplete(sender: CommandSender, alias: String, args: Array<out String>): List<String> {
        return handler.onTabComplete(sender, this, alias, args) ?: emptyList()
    }
}
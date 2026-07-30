package io.github.Cherryh4ck.betterSuicide

import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerCommandPreprocessEvent
import org.bukkit.plugin.java.JavaPlugin

class BetterSuicide : JavaPlugin(), Listener {
    val minimessage = MiniMessage.miniMessage()
    var redirectKill : Boolean = false
    var usePermissions : Boolean = false
    var enableCooldown : Boolean = false
    var cooldown : Int = 0
    var cooldownMessage : Boolean = false
    var noPermissions : String = ""
    var onCooldown : String = ""
    var pluginPrefix : String = ""

    override fun onEnable() {
        saveDefaultConfig()
        loadConfig()

        logger.info("------------------------------")
        logger.info("Plugin activated successfully.")
        logger.info("-------------------------------")

        val suicide = getCommand("suicide")
        val kill = getCommand("kill")
        val bettersuicide = getCommand("bettersuicide")

        suicide?.setExecutor(Suicide(this))
        kill?.setExecutor(Suicide(this))
        bettersuicide?.setExecutor(this)

        suicide?.tabCompleter = Suicide(this)
        kill?.tabCompleter = Suicide(this)
        bettersuicide?.tabCompleter = this

        server.pluginManager.registerEvents(this, this)
    }

    override fun onDisable() {
        // Plugin shutdown logic
    }

    fun loadConfig(){
        pluginPrefix = config.getString("plugin-prefix") ?: ""
        redirectKill = config.getBoolean("redirect-kill-for-ops")
        usePermissions = config.getBoolean("use-permission")
        enableCooldown = config.getBoolean("enable-cooldown")
        cooldown = config.getInt("cooldown-timer")
        cooldownMessage = config.getBoolean("enable-cooldown-message")
        noPermissions = config.getString("no-permissions") ?: ""
        onCooldown = config.getString("cooldown-active") ?: ""
    }

    fun reloadPluginConfig(){
        reloadConfig()
        loadConfig()
    }

    @EventHandler
    fun onPlayerCommand(event: PlayerCommandPreprocessEvent) {
        val message = event.message.lowercase()
        val player = event.player

        if (message.startsWith("/kill") && player.isOp && redirectKill) {
            event.message = message.replaceFirst("/kill", "/minecraft:kill")
        }
    }

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (args.isEmpty()) {
            sender.sendMessage(minimessage.deserialize("$pluginPrefix <gold><b>${this.pluginMeta.version}"))
            return true
        }

        when (args[0].lowercase()) {
            "reload" -> {
                reloadPluginConfig()
                sender.sendMessage(minimessage.deserialize("$pluginPrefix <gold>Reloaded!"))
                return true
            }
            else -> {
                sender.sendMessage(minimessage.deserialize("$pluginPrefix <red>Not a valid command."))
                return true
            }
        }
    }

    override fun onTabComplete(sender: CommandSender, command: Command, alias: String, args: Array<out String>): List<String>? {
        val completions = mutableListOf<String>()
        if (args.size == 1) {
            val subs = listOf("reload")
            for (s in subs) {
                if (s.startsWith(args[0].lowercase())) {
                    completions.add(s)
                }
            }
        }
        return completions
    }
}
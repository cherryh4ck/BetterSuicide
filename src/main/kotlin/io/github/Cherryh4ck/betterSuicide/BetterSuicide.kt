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
    var enableGlobalDeathMessage : Boolean = false
    var globalDeathMessage : String = ""
    var enableCooldown : Boolean = false
    var cooldown : Int = 0
    var cooldownMessage : Boolean = false
    var noPermissions : String = ""
    var onCooldown : String = ""
    var pluginPrefix : String = ""
    var aliases : List<String> = emptyList()

    override fun onEnable() {
        saveDefaultConfig()
        loadConfig()

        logger.info("------------------------------")
        logger.info("Plugin activated successfully.")
        logger.info("-------------------------------")

        val suicide = getCommand("suicide")
        val kill = getCommand("kill")
        val bettersuicide = getCommand("bettersuicide")

        val suicideHandler = Suicide(this)

        suicide?.setExecutor(suicideHandler)
        kill?.setExecutor(suicideHandler)
        bettersuicide?.setExecutor(this)

        suicide?.tabCompleter = suicideHandler
        kill?.tabCompleter = suicideHandler
        bettersuicide?.tabCompleter = this

        registerAliases(suicideHandler)

        server.pluginManager.registerEvents(this, this)
    }

    override fun onDisable() {
        // Plugin shutdown logic
    }

    fun loadConfig(){
        pluginPrefix = config.getString("plugin-prefix") ?: ""
        redirectKill = config.getBoolean("redirect-kill-for-ops")
        usePermissions = config.getBoolean("use-permission")
        enableGlobalDeathMessage = config.getBoolean("enable-global-death-message")
        globalDeathMessage = config.getString("global-death-message") ?: ""
        enableCooldown = config.getBoolean("enable-cooldown")
        cooldown = config.getInt("cooldown-timer")
        cooldownMessage = config.getBoolean("enable-cooldown-message")
        noPermissions = config.getString("no-permissions") ?: ""
        onCooldown = config.getString("cooldown-active") ?: ""
        aliases = config.getStringList("aliases")
    }

    private fun registerAliases(handler: Suicide) {
        for (alias in aliases) {
            val name = alias.trim().removePrefix("/").lowercase()
            if (name.isEmpty() || name == "suicide" || name == "kill" || name == "bettersuicide") {
                continue
            }
            val registered = server.commandMap.register(name, "bettersuicide", AliasCommand(name, handler))
            if (!registered) {
                logger.warning("The alias /$name is already used by another command. Use /bettersuicide:$name instead.")
            }
        }
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
                val previousAliases = aliases
                reloadPluginConfig()
                sender.sendMessage(minimessage.deserialize("$pluginPrefix <gold>Reloaded!"))
                if (aliases != previousAliases) {
                    sender.sendMessage(minimessage.deserialize("$pluginPrefix <yellow>Alias changes require a server restart."))
                }
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
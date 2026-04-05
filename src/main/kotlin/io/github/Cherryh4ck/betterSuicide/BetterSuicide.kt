package io.github.Cherryh4ck.betterSuicide

import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerCommandPreprocessEvent
import org.bukkit.plugin.java.JavaPlugin

class BetterSuicide : JavaPlugin(), Listener {
    val minimessage = MiniMessage.miniMessage()
    var redirectKill : Boolean = false
    var usePermissions : Boolean = false
    var noPermissions : String = ""

    override fun onEnable() {
        saveDefaultConfig()
        loadConfig()

        logger.info("------------------------------")
        logger.info("Plugin activated successfully.")
        logger.info("-------------------------------")

        val suicide = getCommand("suicide")
        val kill = getCommand("kill")

        suicide?.setExecutor(Suicide(this))
        kill?.setExecutor(Suicide(this))

        suicide?.tabCompleter = Suicide(this)
        kill?.tabCompleter = Suicide(this)

        server.pluginManager.registerEvents(this, this)
    }

    override fun onDisable() {
        // Plugin shutdown logic
    }

    fun loadConfig(){
        redirectKill = config.getBoolean("redirect-kill-for-ops") ?: false
        usePermissions = config.getBoolean("use-permission") ?: false
        noPermissions = config.getString("no-permissions") ?: ""
    }

    @EventHandler
    fun onPlayerCommand(event: PlayerCommandPreprocessEvent) {
        val message = event.message.lowercase()
        val player = event.player

        if (message.startsWith("/kill") && player.isOp && redirectKill) {
            event.message = message.replaceFirst("/kill", "/minecraft:kill")
        }
    }
}
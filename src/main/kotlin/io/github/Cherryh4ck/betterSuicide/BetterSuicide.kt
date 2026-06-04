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
    var enableCooldown : Boolean = false
    var cooldown : Int = 0
    var cooldownMessage : Boolean = false
    var noPermissions : String = ""
    var onCooldown : String = ""

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
        redirectKill = config.getBoolean("redirect-kill-for-ops")
        usePermissions = config.getBoolean("use-permission")
        enableCooldown = config.getBoolean("enable-cooldown")
        cooldown = config.getInt("cooldown-timer")
        cooldownMessage = config.getBoolean("enable-cooldown-message")
        noPermissions = config.getString("no-permissions") ?: ""
        onCooldown = config.getString("cooldown-active") ?: ""
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
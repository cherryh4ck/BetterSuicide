package io.github.Cherryh4ck.betterSuicide

import org.bukkit.plugin.java.JavaPlugin

class BetterSuicide : JavaPlugin() {
    var killDisabledToOps : Boolean = false

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
    }

    override fun onDisable() {
        // Plugin shutdown logic
    }

    fun loadConfig(){
        killDisabledToOps = config.getBoolean("disable-kill-for-ops") ?: false
    }
}
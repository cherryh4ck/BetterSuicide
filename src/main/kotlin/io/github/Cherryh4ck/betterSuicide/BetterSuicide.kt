package io.github.Cherryh4ck.betterSuicide

import org.bukkit.plugin.java.JavaPlugin

class BetterSuicide : JavaPlugin() {
    override fun onEnable() {
        logger.info("---------------------")
        logger.info("Plugin activated successfully.")
        logger.info("---------------------")

        val suicide = getCommand("suicide")
        val kill = getCommand("kill")

        suicide?.setExecutor(Suicide())
        kill?.setExecutor(Suicide())

        suicide?.tabCompleter = Suicide()
        kill?.tabCompleter = Suicide()
    }

    override fun onDisable() {
        // Plugin shutdown logic
    }
}
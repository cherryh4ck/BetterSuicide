package io.github.Cherryh4ck.betterSuicide

import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.plugin.java.JavaPlugin

class BetterSuicide : JavaPlugin() {
    override fun onEnable() {
        logger.info("Plugin activated.")

        getCommand("suicide")?.setExecutor(Suicide())
        getCommand("kill")?.setExecutor(Suicide())
    }

    override fun onDisable() {
        // Plugin shutdown logic
    }
}

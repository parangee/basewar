package tk.pshub.parangee.basewar.events

import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import tk.pshub.parangee.basewar.plugin.BaseWar

class OnJoin:Listener {
    @EventHandler
    fun onJoin(e:PlayerJoinEvent) {
        (Bukkit.getPluginManager().getPlugin("basewar") as BaseWar).blueBossBar.addPlayer(e.player)
        (Bukkit.getPluginManager().getPlugin("basewar") as BaseWar).redBossBar.addPlayer(e.player)
    }
}
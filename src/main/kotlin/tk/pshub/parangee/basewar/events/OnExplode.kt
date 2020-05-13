package tk.pshub.parangee.basewar.events

import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityExplodeEvent
import tk.pshub.parangee.basewar.plugin.BaseWar

class OnExplode : Listener {
    private val pl = Bukkit.getPluginManager().getPlugin("basewar") as BaseWar

    @EventHandler
    fun onExplode(e: EntityExplodeEvent) {
        e.blockList().forEach {
            if (it.hasMetadata("coreType") && it.getMetadata("coreType")[0].asString() == "BLUE") {
                if (pl.blueCoreHealth > 0) {
                    Bukkit.getOnlinePlayers().forEach {
                        it.sendTitle("${ChatColor.BLUE}블루팀 코어 체력 -1", "", 10, 20, 10)
                    }
                    pl.blueCoreHealth = pl.blueCoreHealth - 1
                }
            } else if (it.hasMetadata("coreType") && it.getMetadata("coreType")[0].asString() == "RED") {
                if (pl.redCoreHealth > 0) {
                    Bukkit.getOnlinePlayers().forEach {
                        it.sendTitle("${ChatColor.RED}레드팀 코어 체력 -1", "", 10, 20, 10)
                    }
                    pl.redCoreHealth = pl.redCoreHealth - 1
                }
            }
        }
        e.blockList().removeIf { it.type != Material.BEACON }
        e.blockList().removeIf { !it.hasMetadata("coreType") }
        e.blockList().removeIf { it.getMetadata("coreType")[0].asString() == "RED" && pl.redCoreHealth > 0 }
        e.blockList().removeIf { it.getMetadata("coreType")[0].asString() == "BLUE" && pl.blueCoreHealth > 0 }
        if (pl.blueCoreHealth == 0 && pl.redCoreHealth != 0 && e.blockList().find { it.type == Material.BEACON } !== null) {
            Bukkit.broadcastMessage("레드팀 승!")
        } else
        if (pl.redCoreHealth == 0 && pl.blueCoreHealth != 0 && e.blockList().find { it.type == Material.BEACON } !== null) {
            Bukkit.broadcastMessage("블루팀 승!")
        }
    }
}
package tk.pshub.parangee.basewar.events

import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockPlaceEvent
import org.bukkit.metadata.FixedMetadataValue
import tk.pshub.parangee.basewar.items.Core
import tk.pshub.parangee.basewar.plugin.BaseWar

class OnBlockPlace:Listener {


    private val pl:BaseWar = Bukkit.getPluginManager().getPlugin("basewar") as BaseWar

    @EventHandler
    fun handleCorePlace(e:BlockPlaceEvent) {
        if (e.block.type == Material.BEACON) {
            if (e.itemInHand.itemMeta == Core().itemMeta) {
                if (pl.team.get(e.player) == "BLUE") {
                    e.block.setMetadata("coreType", FixedMetadataValue(pl, "BLUE"))
                    Bukkit.broadcastMessage("블루팀 코어가 설치되었습니다.")
                } else if (pl.team.get(e.player) == "RED") {
                    e.block.setMetadata("coreType", FixedMetadataValue(pl, "RED"))
                    Bukkit.broadcastMessage("레드팀 코어가 설치되었습니다.")
                } else {
                    e.isCancelled = true
                    e.player.sendMessage("팀이 설정되지 않았습니다.")
                }
            } else {
                e.isCancelled = true
                e.player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&f[&ci&f] &c기지전쟁에서는 코어 신호기만 설치할 수 있습니다."))
            }
        }
    }
}
package tk.pshub.parangee.basewar.items

import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

class Core:ItemStack(Material.BEACON) {
    init {
        var meta = this.itemMeta
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&a코어"))
        this.itemMeta = meta
    }
}
package tk.pshub.parangee.basewar.plugin

import com.github.noonmaru.kommand.argument.player
import com.github.noonmaru.kommand.kommand
import org.bukkit.Bukkit
import org.bukkit.boss.BarColor
import org.bukkit.boss.BarFlag
import org.bukkit.boss.BarStyle
import org.bukkit.boss.BossBar
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin
import tk.pshub.parangee.basewar.events.*
import tk.pshub.parangee.basewar.items.Core

class BaseWar:JavaPlugin() {
    lateinit var blueBossBar:BossBar
    lateinit var redBossBar:BossBar
    var blueCoreHealth = 10
    var redCoreHealth = 10
    var team = HashMap<Player, String>()
    override fun onEnable() {
        logger.info("기지전쟁 플러그인이 활성화되었습니다.")
        registerCommands()
        registerEvents()
        createBossBar()
    }

    override fun onDisable() {
        Bukkit.getOnlinePlayers().forEach{
            blueBossBar.removeAll()
            redBossBar.removeAll()
        }
    }
    private fun registerCommands() {
        kommand {
            register("기지전쟁") {
                 then("시작") {
                     executes {
                         it.sender.sendMessage("기지전쟁 시작")
                     }
                 }
                then("아이템") {
                    then("상점") {
                    }
                    then("아이템") {
                        then("코어") {
                            executes {
                                it.sender.sendMessage("코어가 지급되었습니다.")
                                (it.sender as Player).inventory.addItem(Core())
                            }
                        }
                    }
                }
                then("리셋") {
                    executes {
                        redCoreHealth = 10
                        blueCoreHealth = 10
                        it.sender.sendMessage("초기화 완료되었습니다.")
                    }
                }
                then("팀") {
                    then("player" to player()) {
                        then("BLUE") {
                            executes {
                                team.set(Bukkit.getServer().getPlayer(it.getArgument("player")) as Player, "BLUE")
                                it.sender.sendMessage("${it.getArgument("player")}님의 팀이 BLUE로 설정되었습니다.")
                            }
                        }
                        then("RED") {
                            executes {
                                team.set(Bukkit.getServer().getPlayer(it.getArgument("player")) as Player, "RED")
                                it.sender.sendMessage("${it.getArgument("player")}님의 팀이 RED로 설정되었습니다.")
                            }
                        }
                    }
                }
            }
        }
    }
    private fun registerEvents() {
        server.pluginManager.registerEvents(OnExplode(), this)
        server.pluginManager.registerEvents(OnJoin(), this)
        server.pluginManager.registerEvents(OnBlockPlace(), this)
    }
    private fun createBossBar() {
        blueBossBar = Bukkit.getServer().createBossBar("블루 팀 코어 체력", BarColor.BLUE, BarStyle.SOLID, BarFlag.CREATE_FOG)
        redBossBar = Bukkit.getServer().createBossBar("레드 팀 코어 체력", BarColor.RED, BarStyle.SOLID, BarFlag.CREATE_FOG)

        blueBossBar.isVisible = true
        redBossBar.isVisible = true
        Bukkit.getOnlinePlayers().forEach{
            blueBossBar.addPlayer(it)
            redBossBar.addPlayer(it)
        }
        server.scheduler.scheduleSyncRepeatingTask(this, {
            blueBossBar.progress = blueCoreHealth.toDouble() / 10
            redBossBar.progress = redCoreHealth.toDouble() / 10
            blueBossBar.setTitle("블루 팀 코어 체력: ${blueCoreHealth}")
            redBossBar.setTitle("레드 팀 코어 체력: ${redCoreHealth}")
        }, 0, 1)
    }
}
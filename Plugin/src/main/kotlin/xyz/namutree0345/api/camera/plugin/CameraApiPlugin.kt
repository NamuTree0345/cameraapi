package xyz.namutree0345.api.camera.plugin

import org.bukkit.GameMode
import org.bukkit.Location
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin
import xyz.namutree0345.api.camera.api.CameraAPI
import xyz.namutree0345.api.camera.nms.FakePlayer
import xyz.namutree0345.api.camera.nms.NMS
import xyz.namutree0345.api.camera.plugin.util.getMinecraftVersion
import java.util.*
import java.util.logging.Level
import kotlin.collections.HashMap

class CameraApiPlugin : JavaPlugin(), CameraAPI {

    lateinit var nms: NMS

    val map: HashMap<UUID, FakePlayer> = HashMap()
    val beforeGameMode: HashMap<UUID, GameMode> = HashMap()

    override fun onEnable() {
        this.logger.info("Loading version...")

        val version = getMinecraftVersion()

        this.logger.info("Your server is running version: $version")

        if(version == "v1_17_R1") {
            nms = xyz.namutree0345.api.camera.`nms.1_17_R1`.NMS_1_17_R1()
        }

        if(nms == null) {
            this.logger.log(Level.SEVERE, "Can not find NMS Candidate!")
            this.pluginLoader.disablePlugin(this)
        }

    }

    override fun camera(location: Location, vararg players: Player) {
        for (player in players) {
            if(!map.containsKey(player.uniqueId)) {
                map[player.uniqueId] = nms.spawnFakeEntityWithSkin(
                    player,
                    player.location,
                    "camera-fake-${player.uniqueId}",
                    player.uniqueId
                )
                beforeGameMode[player.uniqueId] = player.gameMode
                player.gameMode = GameMode.SPECTATOR
            } else {
                nms.teleportFakeEntityTo(player, map[player.uniqueId]!!, location)
            }
        }
    }

    override fun reset(vararg players: Player) {
        for (player in players) {
            if(map.containsKey(player.uniqueId)) {
                nms.removeFakeEntity(player, map[player.uniqueId]!!)
                map.remove(player.uniqueId)
                player.gameMode = beforeGameMode[player.uniqueId]!!
            }
        }
    }

}
package xyz.namutree0345.api.camera

import io.papermc.paper.event.player.AsyncChatEvent
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.plugin.java.JavaPlugin
import xyz.namutree0345.api.camera.api.getInstance

class Test : JavaPlugin(), Listener {

    var cameraOn = false

    override fun onEnable() {
        server.pluginManager.registerEvents(this, this)
    }

    @EventHandler
    fun chat(event: AsyncChatEvent) {
        cameraOn = !cameraOn

        val cameraAPI = getInstance()

        if(cameraOn) {
            cameraAPI.camera(event.player.location.add(0.0, 5.0, 0.0), event.player)
        } else {
            cameraAPI.reset(event.player)
        }
    }

}
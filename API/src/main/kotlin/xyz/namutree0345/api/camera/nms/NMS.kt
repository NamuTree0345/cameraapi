package xyz.namutree0345.api.camera.nms

import org.bukkit.Location
import org.bukkit.entity.Player
import java.util.*

interface NMS {

    fun spawnFakeEntity(player: Player, location: Location, name: String) : FakePlayer

    fun spawnFakeEntityWithSkin(player: Player, location: Location, name: String, uuid: UUID) : FakePlayer

    fun removeFakeEntity(player: Player, npc: FakePlayer)

    fun teleportFakeEntityTo(player: Player, npc: FakePlayer, location: Location)

}
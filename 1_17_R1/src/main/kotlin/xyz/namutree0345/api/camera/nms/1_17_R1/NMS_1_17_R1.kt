package xyz.namutree0345.api.camera.nms.`1_17_R1`

import com.mojang.authlib.GameProfile
import com.mojang.authlib.properties.Property
import com.mojang.util.UUIDTypeAdapter
import net.minecraft.network.protocol.game.PacketPlayOutEntityDestroy
import net.minecraft.network.protocol.game.PacketPlayOutEntityTeleport
import net.minecraft.network.protocol.game.PacketPlayOutNamedEntitySpawn
import net.minecraft.network.protocol.game.PacketPlayOutPlayerInfo
import net.minecraft.server.MinecraftServer
import net.minecraft.server.level.EntityPlayer
import net.minecraft.server.level.WorldServer
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.craftbukkit.v1_17_R1.CraftServer
import org.bukkit.craftbukkit.v1_17_R1.CraftWorld
import org.bukkit.craftbukkit.v1_17_R1.entity.CraftPlayer
import org.bukkit.entity.Player
import xyz.namutree0345.api.camera.nms.FakePlayer
import xyz.namutree0345.api.camera.nms.NMS
import xyz.namutree0345.api.camera.nms.Skin
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.util.*
import java.util.stream.Collectors
import javax.net.ssl.HttpsURLConnection


class NMS_1_17_R1 : NMS {

    val skinCache = hashMapOf<UUID, Skin>()

    override fun spawnFakeEntity(player: Player, location: Location, name: String): Custom {

        val world = (location.world as CraftWorld).handle
        val c = Custom((Bukkit.getServer() as CraftServer).handle.server, world, GameProfile(UUID.randomUUID(), name))
        val connection = (player as CraftPlayer).handle.b

        connection.sendPacket(PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.a, c))
        connection.sendPacket(PacketPlayOutNamedEntitySpawn(c))

        c.setPositionRotation(location.x, location.y, location.z, location.yaw, location.pitch)
        c.setLocation(location.x, location.y, location.z, location.yaw, location.pitch)
        c.isInvisible = false;
        //c.teleportTo(world, BlockPosition(location.block.x, location.block.y, location.getBlock().z))
        c.customName = net.minecraft.network.chat.ChatComponentText(name)

        return c

    }

    override fun spawnFakeEntityWithSkin(player: Player, location: Location, name: String, uuid: UUID): Custom {
        val world = (location.world as CraftWorld).handle
        val profile = GameProfile(UUID.randomUUID(), name)

        if(skinCache.containsKey(uuid)) {
            profile.properties.put("textures", Property("textures", skinCache[uuid]?.skin, skinCache[uuid]?.signature))
        } else {
            val connection = URL(
                String.format(
                    "https://sessionserver.mojang.com/session/minecraft/profile/%s?unsigned=false",
                    UUIDTypeAdapter.fromUUID(player.uniqueId)
                )
            ).openConnection() as HttpsURLConnection
            connection.requestMethod = "GET"
            connection.connect()
            if (connection.responseCode == HttpsURLConnection.HTTP_OK) {
                val reply = BufferedReader(InputStreamReader(connection.inputStream)).lines().collect(Collectors.joining())
                val skin = reply.split("\"value\":\"").toTypedArray()[1].split("\"").toTypedArray()[0]
                val signature = reply.split("\"signature\":\"").toTypedArray()[1].split("\"").toTypedArray()[0]
                skinCache[uuid] = Skin(skin, signature)
                profile.properties.put("textures", Property("textures", skin, signature))
            } else {
                println("Connection could not be opened (Response code " + connection.responseCode + ", " + connection.responseMessage + ")")
            }
        }

        val c = Custom((Bukkit.getServer() as CraftServer).handle.server, world, profile)
        val connection = (player as CraftPlayer).handle.b

        connection.sendPacket(PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.a, c))
        connection.sendPacket(PacketPlayOutNamedEntitySpawn(c))

        c.setPositionRotation(location.x, location.y, location.z, location.yaw, location.pitch)
        c.setLocation(location.x, location.y, location.z, location.yaw, location.pitch)
        c.isInvisible = false;
        //c.teleportTo(world, BlockPosition(location.block.x, location.block.y, location.getBlock().z))
        c.customName = net.minecraft.network.chat.ChatComponentText(name)

        return c
    }

    override fun removeFakeEntity(player: Player, npc: FakePlayer) {
        val c = npc as Custom
        val connection = (player as CraftPlayer).handle.b

        connection.sendPacket(PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.e, c))
        connection.sendPacket(PacketPlayOutEntityDestroy(c.id))
    }

    override fun teleportFakeEntityTo(player: Player, npc: FakePlayer, location: Location) {
        val c = npc as Custom
        val connection = (player as CraftPlayer).handle.b

        c.setPositionRotation(location.x, location.y, location.z, location.yaw, location.pitch)
        c.setLocation(location.x, location.y, location.z, location.yaw, location.pitch)

        connection.sendPacket(PacketPlayOutEntityTeleport(c))
    }

    class Custom(srv: MinecraftServer?, world: WorldServer?, val game: GameProfile?) : EntityPlayer(srv, world, game), FakePlayer {
        override fun getUuid(): UUID {
            return game?.id!!
        }

        override fun getX(): Double {
            return this.positionVector.x
        }

        override fun getY(): Double {
            return this.positionVector.y
        }

        override fun getZ(): Double {
            return this.positionVector.z
        }
    }

}

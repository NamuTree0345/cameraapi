package xyz.namutree0345.api.camera.plugin.util

import org.bukkit.Bukkit
import java.lang.IllegalStateException

fun getMinecraftVersion() : String {
    try {
        return Bukkit.getServer().javaClass.`package`.name.split("\\.")[3]
    } catch (exception: ArrayIndexOutOfBoundsException) {
        throw IllegalStateException("Can't find minecraft version!")
    }
}
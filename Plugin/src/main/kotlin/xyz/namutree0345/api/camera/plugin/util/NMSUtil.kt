package xyz.namutree0345.api.camera.plugin.util

import org.bukkit.Bukkit
import java.lang.IllegalStateException

fun getMinecraftVersion() : String {
    try {
        val v = Bukkit.getServer().javaClass.`package`.name
        return v.substring(v.lastIndexOf('.') + 1)
    } catch (exception: ArrayIndexOutOfBoundsException) {
        throw IllegalStateException("Can't find minecraft version!")
    }
}
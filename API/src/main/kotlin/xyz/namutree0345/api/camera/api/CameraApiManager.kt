@file:JvmName("CameraApiManager")

package xyz.namutree0345.api.camera.api

import org.bukkit.Bukkit

fun getInstance() : CameraAPI {
    return Bukkit.getPluginManager().getPlugin("CameraAPI") as CameraAPI
}
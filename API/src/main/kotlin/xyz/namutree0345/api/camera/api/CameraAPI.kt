package xyz.namutree0345.api.camera.api

import org.bukkit.Location
import org.bukkit.entity.Player
import org.jetbrains.annotations.NotNull

interface CameraAPI {

    fun camera(location: Location, vararg players: Player)

    fun reset(vararg players: Player)

}
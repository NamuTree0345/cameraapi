package xyz.namutree0345.api.camera.nms

import java.util.*

interface FakePlayer {
    fun getUuid() : UUID

    fun getX() : Double
    fun getY() : Double
    fun getZ() : Double
}

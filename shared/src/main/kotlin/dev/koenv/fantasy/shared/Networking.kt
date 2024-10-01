package dev.koenv.fantasy.shared

import com.esotericsoftware.kryo.Kryo

object Networking {
    fun registerClasses(kryo: Kryo) {
        kryo.apply {
            register(Message::class.java)
        }
    }
}

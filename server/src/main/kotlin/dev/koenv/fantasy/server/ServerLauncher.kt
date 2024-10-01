@file:JvmName("ServerLauncher")

package dev.koenv.fantasy.server

import com.esotericsoftware.kryonet.Server
import com.esotericsoftware.kryonet.Listener
import com.esotericsoftware.kryonet.Connection
import dev.koenv.fantasy.shared.Message
import dev.koenv.fantasy.shared.Networking

/** Launches the server application. */
fun main() {
    val server = Server()
    server.start()
    server.bind(54555, 54777)

    Networking.registerClasses(server.kryo)

    server.addListener(object : Listener() {
        override fun received(connection: Connection?, obj: Any?) {
            when (obj) {
                is Message -> {
                    println("Server received: ${obj.text}")
                    connection?.sendTCP(Message("Hello, Client!"))
                }

                else -> {
                    println("Server received an unknown object: $obj")
                }
            }
        }
    })

    println("Server started!")
}

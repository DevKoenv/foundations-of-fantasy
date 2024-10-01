package dev.koenv.fantasy

import com.esotericsoftware.kryonet.Client
import com.esotericsoftware.kryonet.Listener
import com.esotericsoftware.kryonet.Connection
import dev.koenv.fantasy.shared.Message
import dev.koenv.fantasy.shared.Networking
import ktx.app.KtxGame
import ktx.app.KtxScreen
import ktx.async.KtxAsync

class Main : KtxGame<KtxScreen>() {
    private val client = Client()

    override fun create() {
        KtxAsync.initiate()
        setupClient()
    }

    private fun setupClient() {
        client.start()

        while (!client.isConnected) {
            try {
                client.connect(5000, "localhost", 54555, 54777)
            } catch (e: Exception) {
                println("Failed to connect to server, retrying in 5 seconds...")
                Thread.sleep(5000)
            }
        }

        Networking.registerClasses(client.kryo)

        client.addListener(object : Listener() {
            override fun received(connection: Connection?, obj: Any?) {
                if (obj is Message) {
                    println("Received: ${obj.text}")
                }
            }
        })

        // Example to send a message
        client.sendTCP(Message("Hello, Server!"))
    }
}

package dev.koenv.fantasy.screens

import com.artemis.Aspect
import com.artemis.World
import com.artemis.WorldConfigurationBuilder
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.viewport.ExtendViewport
import dev.koenv.fantasy.components.PlayerComponent
import dev.koenv.fantasy.components.PositionComponent
import dev.koenv.fantasy.components.VelocityComponent
import dev.koenv.fantasy.entities.Player
import dev.koenv.fantasy.systems.MovementSystem
import dev.koenv.fantasy.systems.PlayerControlSystem
import ktx.app.KtxScreen
import dev.koenv.fantasy.worlds.World as GameWorld

class GameScreen : KtxScreen {
    private val camera = OrthographicCamera()
    private val viewport = ExtendViewport(800f, 600f, camera)
    private val uiCamera = OrthographicCamera()
    private val uiViewport = ExtendViewport(800f, 600f, uiCamera)
    private val shapeRenderer = ShapeRenderer()
    private val world: World
    private val gameWorld = GameWorld(seed = 12345L) // Pass a seed here
    private var playerPosition: PositionComponent? = null
    private val font = BitmapFont()
    private val batch = SpriteBatch()
    private val player: Player

    init {
        viewport.apply()
        uiViewport.apply()
        camera.position.set(0f, 0f, 0f)
        uiCamera.position.set(uiViewport.worldWidth / 2, uiViewport.worldHeight / 2, 0f)

        val config = WorldConfigurationBuilder()
            .with(PlayerControlSystem())
            .with(MovementSystem())
            .build()
        world = World(config)

        player = createPlayer()
    }

    private fun createPlayer(): Player {
        val playerEntity = world.create()
        playerPosition = world.edit(playerEntity).create(PositionComponent::class.java).apply {
            x = 100f
            y = 100f
        }
        world.edit(playerEntity).create(VelocityComponent::class.java)
        world.edit(playerEntity).create(PlayerComponent::class.java)
        return Player(Vector2(playerPosition!!.x, playerPosition!!.y))
    }

    override fun render(delta: Float) {
        world.setDelta(delta)
        world.process()

        playerPosition?.let {
            camera.position.set(it.x, it.y, 0f)
            player.position.set(it.x, it.y)
        }
        camera.update()
        shapeRenderer.projectionMatrix = camera.combined

        Gdx.gl.glClearColor(0f, 0f, 0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled)
        renderEntities()
        gameWorld.render(shapeRenderer, camera)
        player.render(shapeRenderer)
        shapeRenderer.end()

        uiCamera.update()
        batch.projectionMatrix = uiCamera.combined
        batch.begin()
        player.render(batch)
        playerPosition?.let {
            font.draw(batch, "X: ${it.x}, Y: ${it.y}", 10f, uiViewport.worldHeight - 10f)
        }
        batch.end()
    }

    private fun renderEntities() {
        val entities = world.aspectSubscriptionManager.get(Aspect.all(PositionComponent::class.java)).entities
        val entityIds = entities.data
        for (i in 0 until entities.size()) {
            val entityId = entityIds[i]
            val position = world.getMapper(PositionComponent::class.java).get(entityId)
            shapeRenderer.circle(position.x, position.y, 25f)
        }
    }

    override fun resize(width: Int, height: Int) {
        viewport.update(width, height)
        uiViewport.update(width, height)
        uiCamera.position.set(uiViewport.worldWidth / 2, uiViewport.worldHeight / 2, 0f)
    }

    override fun dispose() {
        shapeRenderer.dispose()
        font.dispose()
        batch.dispose()
    }
}

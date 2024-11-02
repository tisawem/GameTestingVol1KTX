package Tisawem.GameTesting.Vol1KTXVersion


import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.viewport.FitViewport
import ktx.app.KtxScreen
import ktx.assets.disposeSafely
import ktx.graphics.center


abstract class CommonScreen : KtxScreen {

    open val viewportWidth = 800f
    open val viewportHeight = 600f

    open val camera: OrthographicCamera by lazy {
        OrthographicCamera(viewportWidth, viewportHeight).apply {
            center()
        }

    }
    open val viewport by lazy {
        FitViewport(viewportWidth, viewportHeight, camera)

    }

    open val batch by lazy {
        SpriteBatch().apply {
            projectionMatrix = camera.combined
        }
    }

    open val stage by lazy {
        Stage(viewport, batch).also {
            Gdx.input.inputProcessor = it
        }
    }

    override fun resize(width: Int, height: Int) {
        viewport.update(width, height)
    }

    override fun dispose() {
        batch.disposeSafely()
        stage.disposeSafely()
    }

    inline fun <reified T : KtxScreen, reified U : KtxScreen> replaceScreen(currentScreen: T, nextScreen: U) {
        if (Main.containsScreen<T>()) {
            Main.removeScreen<T>()
        }
        if (!Main.containsScreen<U>()) {
            Main.addScreen(nextScreen)
        }
        Main.setScreen<U>()
    }
}

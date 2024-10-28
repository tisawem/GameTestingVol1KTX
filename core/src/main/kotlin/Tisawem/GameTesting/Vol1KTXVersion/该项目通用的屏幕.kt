package Tisawem.GameTesting.Vol1KTXVersion


import com.badlogic.gdx.Gdx
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.viewport.FitViewport
import ktx.app.KtxGame
import ktx.app.KtxScreen
import ktx.graphics.center


abstract class 该项目通用的屏幕 : KtxScreen {
    abstract val game: KtxGame<KtxScreen>
    abstract val 全局素材管理器: AssetManager

    open val 屏幕分辨率_X = 800f
    open val 屏幕分辨率_Y = 600f

    open val 视口宽 = 800f
    open val 视口高 = 600f

    open val 正交相机: OrthographicCamera = OrthographicCamera().apply { center() }

    open val 视口 by lazy {
        FitViewport(视口宽, 视口高, 正交相机)
    }


    open val batch by lazy {
        SpriteBatch().apply {
            projectionMatrix = 正交相机.combined
        }
    }


    open val stage by lazy {
        Stage(视口, batch)
            .let {
                Gdx.input.inputProcessor = it
                it
            }

    }

    override fun resize(width: Int, height: Int) {
        视口.update(width, height)
    }

    override fun dispose() {
        batch.dispose()
        stage.dispose()
    }

    inline fun <reified T : KtxScreen, reified U : KtxScreen> replaceScreen(本屏幕: T, 下一个屏幕: U) {

        game.removeScreen<T>()
        game.addScreen(下一个屏幕)
        game.setScreen<U>()


    }
}


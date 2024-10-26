package Tisawem.GameTesting.Vol1KTXVersion


import com.badlogic.gdx.Gdx
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.viewport.FitViewport
import ktx.app.KtxGame
import ktx.app.KtxScreen
import ktx.graphics.center


abstract class 该项目通用的屏幕 : KtxScreen {
    abstract val game: KtxGame<KtxScreen>
    abstract val 全局素材管理器: AssetManager

    open val 屏幕分辨率 = Vector2(800f, 600f)
    abstract val 视口宽度高度: Vector2

    open val 正交相机: OrthographicCamera by lazy { OrthographicCamera().apply { center() } }

    open val 视口 by lazy {
        FitViewport(视口宽度高度.x, 视口宽度高度.y, 正交相机)
    }
    open val batch by lazy {
        SpriteBatch().apply {
            projectionMatrix = 正交相机.combined
        }
    }


    override fun dispose() {
        batch.dispose()
    }

    inline fun <reified T : KtxScreen, reified U : KtxScreen> 更换屏幕并删除上一个屏幕(本屏幕: T, 下一个屏幕: U) {

        game.removeScreen<T>()
        game.addScreen(下一个屏幕)
        game.setScreen<U>()


    }
}

/**
 * 程序抛出错误的时候，显示的屏幕
 * 使用思源宋体
 * @param 报错内容 报错内容，每32个字符自动换行
 */
class errorScreen(val 报错内容: String) : KtxScreen {
    val backgroundImage = Texture("背景/stage1Background.png")
    val errorMessage: String
        get() = 报错内容.chunked(32).joinToString("\n")

    private val generator = FreeTypeFontGenerator(Gdx.files.internal("SourceHanSerifSC-Light.otf"))
    var parameter = FreeTypeFontParameter().apply {
        characters = 报错内容 + "Sorry, this game cannot continue running.\nyou can close this application."
        size = 24
        borderColor = Color.TAN;
        borderWidth = 1f
    }

    private val font = generator.generateFont(parameter)

    private val batch = SpriteBatch()

    init {
        Gdx.graphics.setWindowedMode(800, 600)

    }

    override fun render(delta: Float) {
        batch.begin()
        batch.draw(backgroundImage, 0f, 150f)
        font.draw(batch, errorMessage, 1f, 590f)
        font.draw(batch, "Sorry, this game cannot continue running.\nyou can close this application.", 5f, 75f)
        batch.end()

    }

    override fun dispose() {
        batch.dispose()
        font.dispose()
        generator.dispose()

    }
}

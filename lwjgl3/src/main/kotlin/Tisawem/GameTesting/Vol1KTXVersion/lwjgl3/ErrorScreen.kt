package Tisawem.GameTesting.Vol1KTXVersion.lwjgl3

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.audio.Music
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.badlogic.gdx.utils.Align
import com.badlogic.gdx.utils.ScreenUtils
import com.badlogic.gdx.utils.viewport.ScreenViewport

/**
 * 程序抛出错误的时候，显示的屏幕
 *
 * @param 报错内容 报错内容
 */
class ErrorScreen(val 报错内容: String) : ApplicationAdapter() {

    private lateinit var backgroundImage: Texture
    private lateinit var generator: FreeTypeFontGenerator
    private lateinit var font: BitmapFont
    private lateinit var batch: SpriteBatch
    private lateinit var music: Music

    private lateinit var label: Label
    private lateinit var viewport: ScreenViewport

    private lateinit var stage: Stage

    override fun create() {
        viewport = ScreenViewport()
        backgroundImage = Texture("背景/stage1Background.png")
        batch = SpriteBatch()

        generator = FreeTypeFontGenerator(Gdx.files.internal("SourceHanSansSC-Regular.otf"))
        val parameter = FreeTypeFontParameter().apply {
            characters = "程序出错，现在可以关闭该程序了。\n$报错内容"
            size = 24
            borderColor = Color.BROWN
            borderWidth = 2f
        }

        font = generator.generateFont(parameter)

        label = Label(
            "程序出错，现在可以关闭该程序了。\n\n$报错内容",
            Label.LabelStyle(font, Color.WHITE)
        ).apply {
            setAlignment(Align.topLeft)
            wrap = true
        }

        stage = Stage(viewport, batch).apply {
            addActor(Image(TextureRegionDrawable(backgroundImage)))

            addActor(label)
        }

        music = Gdx.audio.newMusic(Gdx.files.internal("音乐/error.mp3")).apply {
            play()
        }
    }

    override fun render() {
        ScreenUtils.clear(Color.BLACK)
        stage.draw()

    }

    override fun resize(width: Int, height: Int) {

        label.width = width.toFloat()

        label.setPosition(0f, height - label.height)

        viewport.update(width, height, true)
    }

    override fun dispose() {
        stage.dispose()
        batch.dispose()
        font.dispose()
        generator.dispose()
        music.dispose()
    }
}

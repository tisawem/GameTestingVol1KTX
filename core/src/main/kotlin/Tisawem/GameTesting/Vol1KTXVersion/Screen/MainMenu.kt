package Tisawem.GameTesting.Vol1KTXVersion.Screen

import Tisawem.GameTesting.Vol1KTXVersion.CommonScreen

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.audio.Music
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.badlogic.gdx.utils.ScreenUtils
import ktx.app.KtxGame
import ktx.app.KtxScreen


class MainMenu(override val game: KtxGame<KtxScreen>, override val assetManager: AssetManager) : CommonScreen() {
    private val music_th01_01 = assetManager["音乐/th01_01.mp3", Music::class.java].apply {
        isLooping = true
        play()
    }

    private val backgroundImage = Image(assetManager["背景/mainmenuTouhou.png", Texture::class.java]).apply {
        setSize(viewportWidth, viewportHeight)
    }

    private val startButton =
        ImageButton(TextureRegionDrawable(assetManager["button/startbutton.png", Texture::class.java])).apply {
            addListener(object : ClickListener() {
                override fun clicked(event: InputEvent?, x: Float, y: Float) {

                    // 释放当前屏幕资源
                    dispose()

                    replaceScreen(this@MainMenu, Stage1(game, assetManager))
                }
            })
        }

    override fun render(delta: Float) {
        ScreenUtils.clear(Color.WHITE)
        stage.act()
        stage.draw()

    }

    init {
        stage.addActor(backgroundImage)
        stage.addActor(startButton)

    }

    override fun dispose() {
        super.dispose()
        music_th01_01.stop()
    }


}

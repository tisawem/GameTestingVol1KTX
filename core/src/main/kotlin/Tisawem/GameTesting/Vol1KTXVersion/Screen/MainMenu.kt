package Tisawem.GameTesting.Vol1KTX.Screen

import Tisawem.GameTesting.Vol1KTXVersion.Screen.Stage1
import Tisawem.GameTesting.Vol1KTXVersion.该项目通用的屏幕
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


class MainMenu(override val game: KtxGame<KtxScreen>, override val 全局素材管理器: AssetManager) : 该项目通用的屏幕() {
    private val music_th01_01 = 全局素材管理器["音乐/th01_01.mp3", Music::class.java].apply {
        isLooping = true
        play()
    }

    private val backgroundImage = Image(全局素材管理器["背景/mainmenuTouhou.png", Texture::class.java]).apply {
        setSize(屏幕分辨率_X, 屏幕分辨率_Y)
    }

    private val startButton =
        ImageButton(TextureRegionDrawable(全局素材管理器["button/startbutton.png", Texture::class.java])).apply {
            addListener(object : ClickListener() {
                override fun clicked(event: InputEvent?, x: Float, y: Float) {

                    // 释放当前屏幕资源
                    dispose()

                    replaceScreen(this@MainMenu, Stage1(game, 全局素材管理器))
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

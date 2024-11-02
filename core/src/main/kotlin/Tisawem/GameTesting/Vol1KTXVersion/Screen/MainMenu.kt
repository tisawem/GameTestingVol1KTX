package Tisawem.GameTesting.Vol1KTXVersion.Screen


import Tisawem.GameTesting.Vol1KTXVersion.CommonScreen
import Tisawem.GameTesting.Vol1KTXVersion.Main.全局资源管理器
import Tisawem.GameTesting.Vol1KTXVersion.实用工具.getBitmapFontSuitForYourContent
import Tisawem.GameTesting.Vol1KTXVersion.实用工具.loadMusic
import Tisawem.GameTesting.Vol1KTXVersion.实用工具.loadTexture
import com.badlogic.gdx.audio.Music
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.badlogic.gdx.utils.ScreenUtils

class MainMenu : CommonScreen() {
    init {
        全局资源管理器.apply {
            loadTexture("背景/mainmenuTouhou.png")
            loadTexture("button/startbutton.png")
            loadMusic("音乐/th01_01.mp3")
            finishLoading()
        }
        全局资源管理器["音乐/th01_01.mp3", Music::class.java].apply {
            isLooping = true
            play()
        }

    }


    private val backgroundImage = Image(全局资源管理器["背景/mainmenuTouhou.png", Texture::class.java]).apply {
        setSize(viewportWidth, viewportHeight)
    }

    private val startButton =
        ImageButton(TextureRegionDrawable(全局资源管理器["button/startbutton.png", Texture::class.java])).apply {
            addListener(object : ClickListener() {
                override fun clicked(event: InputEvent?, x: Float, y: Float) {
                    stage.addActor(nowLoadingFont)

                    // 释放当前屏幕资源
                    dispose()
                    replaceScreen(this@MainMenu, Stage1())

                }
            })
        }

    private val nowLoadingFont =
        Label("正在加载。。。。。。", Label.LabelStyle(getBitmapFontSuitForYourContent("正在加载。", 80), Color.WHITE)).apply {
            y = 200f
        }


    init {
        stage.addActor(backgroundImage)
        stage.addActor(startButton)

    }

    override fun render(delta: Float) {
        ScreenUtils.clear(Color.WHITE)
        stage.act()
        stage.draw()

    }


    override fun dispose() {
        全局资源管理器.apply {
            unload("背景/mainmenuTouhou.png")
            unload("button/startbutton.png")
            unload("音乐/th01_01.mp3")
        }
        super.dispose()
    }


}

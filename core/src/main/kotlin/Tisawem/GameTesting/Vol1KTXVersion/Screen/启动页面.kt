package Tisawem.GameTesting.Vol1KTXVersion.Screen

import Tisawem.GameTesting.Vol1KTX.Screen.MainMenu
import Tisawem.GameTesting.Vol1KTXVersion.该项目通用的屏幕

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.InputAdapter
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.audio.Music
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.utils.ScreenUtils
import ktx.app.KtxGame
import ktx.app.KtxScreen

class 启动页面(override val game: KtxGame<KtxScreen>) : 该项目通用的屏幕() {

    override val 视口高: Float
        get() = 600f
    private val ktxLogo = Sprite(Texture("logo.png")).apply {
        setScale(0.75f)
        setPosition((视口宽 - width) / 2, (视口高 - height) / 2)

    }
    private val 启动界面 = Sprite(Texture("背景/启动界面.png")).apply {
        setSize(视口宽, 视口高)


    }


    private val font = BitmapFont().apply {
        data.setScale(3f)
    }

    override val 全局素材管理器 = AssetManager().apply {
        load("背景/mainmenuTouhou.png", Texture::class.java)
        load("button/startbutton.png", Texture::class.java)


        load("音乐/th01_01.mp3", Music::class.java)
        load("音乐/th01_02.mp3", Music::class.java)
        load("音乐/th01_11.mp3", Music::class.java)
        load("音乐/th01_14.mp3", Music::class.java)
        load("音乐/lose.mp3", Music::class.java)
        load("音乐/win.mp3", Music::class.java)

        load("背景/作战背景1.png", Texture::class.java)

        load("人物/灵梦/右移/灵梦0000.png", Texture::class.java)
        load("人物/灵梦/右移/灵梦0001.png", Texture::class.java)
        load("人物/灵梦/右移/灵梦0002.png", Texture::class.java)
        load("人物/灵梦/右移/灵梦0003.png", Texture::class.java)
        load("人物/灵梦/右移/灵梦0004.png", Texture::class.java)
        load("人物/灵梦/右移/灵梦0005.png", Texture::class.java)

        load("人物/灵梦/左移/灵梦0000.png", Texture::class.java)
        load("人物/灵梦/左移/灵梦0001.png", Texture::class.java)
        load("人物/灵梦/左移/灵梦0002.png", Texture::class.java)
        load("人物/灵梦/左移/灵梦0003.png", Texture::class.java)
        load("人物/灵梦/左移/灵梦0004.png", Texture::class.java)
        load("人物/灵梦/左移/灵梦0005.png", Texture::class.java)

    }

    init {
        Gdx.input.inputProcessor = object : InputAdapter() {
            override fun keyDown(keycode: Int): Boolean {
                if (全局素材管理器.isFinished) {
                    Gdx.input.inputProcessor = null
                    dispose()

                    replaceScreen(this@启动页面, MainMenu(game, 全局素材管理器))
                }
                return true
            }

            override fun touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
                this.keyDown(0)
                return true
            }

            override fun mouseMoved(screenX: Int, screenY: Int): Boolean {
                this.keyDown(0)
                return true
            }


        }

    }

    override fun render(delta: Float) {
        // 清屏
        ScreenUtils.clear(0f, 0f, 0f, 1f)

        // 开始绘制
        batch.begin()

        // 绘制背景和 logo
        启动界面.draw(batch)
        ktxLogo.draw(batch)
        if (全局素材管理器.isFinished) {
            font.draw(batch, "100", 630f, 500f)
            font.draw(batch, "Hit Any Key", 500f, 400f)


        } else {
            font.draw(batch, (全局素材管理器.progress * 100).toString(), 630f, 500f)

        }

        // 结束绘制
        batch.end()

        //需要调用该指令，才会加载，否则一直不加载
        全局素材管理器.update()

    }

    override fun resize(width: Int, height: Int) {
        视口.update(width, height)
    }


    override fun dispose() {
        // 释放所有资源
        super.dispose()
        ktxLogo.texture.dispose()
        启动界面.texture.dispose()

        font.dispose() // 释放内置字体资源
    }


}

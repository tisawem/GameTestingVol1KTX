package Tisawem.GameTesting.Vol1KTXVersion.Screen

import Tisawem.GameTesting.Vol1KTXVersion.CommonScreen


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

class 启动页面(override val game: KtxGame<KtxScreen>) : CommonScreen() {


    private val ktxLogo = Sprite(Texture("logo.png")).apply {
        setScale(0.75f)
        setPosition((viewportWidth - width) / 2, (viewportHeight - height) / 2)

    }
    private val 启动界面 = Sprite(Texture("背景/启动界面.png")).apply {
        setSize(viewportWidth, viewportHeight)


    }


    private val font = BitmapFont().apply {
        data.setScale(3f)
    }

    override val assetManager = AssetManager().apply {
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

        load("其他/阴阳玉1.png", Texture::class.java)


    }

    init {
        Gdx.input.inputProcessor = object : InputAdapter() {
            override fun keyDown(keycode: Int): Boolean {
                if (assetManager.isFinished) {
                    Gdx.input.inputProcessor = null
                    dispose()

                    replaceScreen(this@启动页面, MainMenu(game, assetManager))
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
        if (assetManager.isFinished) {
            font.draw(batch, "100", 630f, 500f)
            font.draw(batch, "Hit Any Key", 500f, 400f)


        } else {
            font.draw(batch, (assetManager.progress * 100).toString(), 630f, 500f)

        }

        // 结束绘制
        batch.end()

        //需要调用该指令，才会加载，否则一直不加载
        assetManager.update()

    }

    override fun resize(width: Int, height: Int) {
        viewport.update(width, height)
    }


    override fun dispose() {
        // 释放所有资源
        super.dispose()
        ktxLogo.texture.dispose()
        启动界面.texture.dispose()

        font.dispose() // 释放内置字体资源
    }


}

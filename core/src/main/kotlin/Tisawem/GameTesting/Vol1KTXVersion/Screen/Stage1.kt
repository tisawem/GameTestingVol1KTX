package Tisawem.GameTesting.Vol1KTXVersion.Screen

import Tisawem.GameTesting.Vol1KTXVersion.Character.Hakurei_Reimu
import Tisawem.GameTesting.Vol1KTXVersion.该项目通用的屏幕
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.audio.Music
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.ui.Image
import ktx.app.KtxGame
import ktx.app.KtxScreen

class Stage1(override val game: KtxGame<KtxScreen>, override val 全局素材管理器: AssetManager) : 该项目通用的屏幕() {
    override val 视口宽: Float
        get() = 8f
    override val 视口高: Float
        get() = 6f
    private val worldWidth = 16f
    private val worldHeight = 6f

    private val music_th01_02 = 全局素材管理器["音乐/th01_02.mp3", Music::class.java].apply {
        isLooping = true
        play()
    }

    private val Hakurei_Reimu = Hakurei_Reimu(全局素材管理器, 视口, Vector2(worldWidth, worldHeight))

    init {
        stage.apply {
            addActor(Image(全局素材管理器["背景/作战背景1.png", Texture::class.java]).apply {
                setSize(worldWidth, worldHeight)
            })
            addActor(Hakurei_Reimu)
            keyboardFocus = Hakurei_Reimu
        }
    }

    override fun render(delta: Float) {
        stage.act()
        stage.draw()

    }

    override fun dispose() {
        super.dispose()
        music_th01_02.stop()
    }
}

package Tisawem.GameTesting.Vol1KTXVersion.Screen


import Tisawem.GameTesting.Vol1KTXVersion.Character.HakureiReimu
import Tisawem.GameTesting.Vol1KTXVersion.CommonScreen
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.audio.Music
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.ui.Image
import ktx.app.KtxGame
import ktx.app.KtxScreen

class Stage1(
    override val game: KtxGame<KtxScreen>,
    override val assetManager: AssetManager
) : CommonScreen() {

    override val viewportWidth: Float
        get() = 8f
    override val viewportHeight: Float
        get() = 6f

    private val worldWidth = 16f
    private val worldHeight = 6f

    private val musicTrack: Music = assetManager["音乐/th01_02.mp3", Music::class.java].apply {
        isLooping = true
        play()
    }


    private val reimu = HakureiReimu(assetManager, Vector2(worldWidth, worldHeight))

    init {
        // 设置输入处理器
        Gdx.input.inputProcessor = stage

        // 初始化舞台
        stage.apply {
            // 添加背景
            addActor(Image(assetManager.get("背景/作战背景1.png", Texture::class.java)).apply {
                setSize(worldWidth, worldHeight)
            })
            // 添加角色
            addActor(reimu)
            keyboardFocus = reimu

        }
    }

    override fun render(delta: Float) {
        super.render(delta)
        stage.act(delta)
        stage.draw()
        adjustCamera()
    }

    private fun adjustCamera() {
        val camera = viewport.camera
        val halfViewportWidth = viewport.worldWidth / 2

        // 获取角色的中心位置
        val characterX = reimu.x + reimu.width / 2

        // 定义相机边缘距离
        val cameraEdgeDistance = 2.25f

        // 根据角色位置调整相机
        if (characterX > camera.position.x + halfViewportWidth - cameraEdgeDistance) {
            camera.position.x =
                (characterX - (halfViewportWidth - cameraEdgeDistance)).coerceAtMost(worldWidth - halfViewportWidth)
        } else if (characterX < camera.position.x - halfViewportWidth + cameraEdgeDistance) {
            camera.position.x = (characterX + (halfViewportWidth - cameraEdgeDistance)).coerceAtLeast(halfViewportWidth)
        }

        // 确保相机不超出世界边界
        camera.position.x = camera.position.x.coerceIn(halfViewportWidth, worldWidth - halfViewportWidth)
        camera.update()
    }

    override fun dispose() {
        super.dispose()
        musicTrack.stop()
        assetManager.unload("音乐/th01_02.mp3")
    }
}

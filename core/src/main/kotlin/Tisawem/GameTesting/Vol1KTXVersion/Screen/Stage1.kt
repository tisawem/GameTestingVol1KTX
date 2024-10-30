package Tisawem.GameTesting.Vol1KTXVersion.Screen


import Tisawem.GameTesting.Vol1KTXVersion.Character.HakureiReimu
import Tisawem.GameTesting.Vol1KTXVersion.Character.阴阳玉
import Tisawem.GameTesting.Vol1KTXVersion.CommonScreen
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.audio.Music
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer
import com.badlogic.gdx.physics.box2d.World
import com.badlogic.gdx.scenes.scene2d.ui.Image
import ktx.app.KtxGame
import ktx.app.KtxScreen

class Stage1(
    override val game: KtxGame<KtxScreen>, override val assetManager: AssetManager
) : CommonScreen() {
    //世界和视口
    override val viewportWidth: Float
        get() = 8f
    override val viewportHeight: Float
        get() = 6f

    private val worldWidth = 16f
    private val worldHeight = 6f

    //box2D
    private val box2DWorld = World(Vector2(0f, 0f), true)
    private val box2DWorldTimeStep = 1 / 60f
    private val velocityIterations = 6
    private val positionIterations = 2

    // 调试渲染器
    private val debugRenderer = Box2DDebugRenderer()

    // Box2D 世界的缩放比例，用于渲染调试器
    private val cameraScale = 1f


    //Actor
    private val reimu = HakureiReimu(assetManager, Vector2(worldWidth, worldHeight))
    private val 阴阳玉 = 阴阳玉(assetManager, Vector2(worldWidth, worldHeight))

    //音乐
    private val musicTrack: Music = assetManager["音乐/th01_02.mp3", Music::class.java].apply {
        isLooping = true
        play()
    }

    init {

        // 初始化舞台
        stage.apply {
            // 添加背景
            addActor(Image(assetManager.get("背景/作战背景1.png", Texture::class.java)).apply {
                setSize(worldWidth, worldHeight)
            })
            // 添加角色
            addActor(reimu)
            addActor(阴阳玉)
            keyboardFocus = reimu

        }

        // 创建 Box2D 物体
        阴阳玉.createBody(box2DWorld)
        // reimu.createBody(box2DWorld)
    }

    override fun render(delta: Float) {
        // 更新物理世界
        box2DWorld.step(box2DWorldTimeStep, velocityIterations, positionIterations)

        // 同步 Box2D 物体的位置到 Actor
        阴阳玉.updateFromBody()
        reimu.updateFromBody()

        // 渲染 Box2D 调试信息
        debugRenderer.render(box2DWorld, camera.combined.scl(cameraScale))


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

        //需要手动操控相机进行调试时取消注释
//        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) camera.position.x+=0.1f
//        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) camera.position.x-=0.1f
//        if (Gdx.input.isKeyPressed(Input.Keys.UP)) camera.position.y+=0.1f
//        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) camera.position.y-=0.1f

        // 根据角色位置调整相机
        if (characterX > camera.position.x + halfViewportWidth - cameraEdgeDistance) {
            camera.position.x =
                (characterX - (halfViewportWidth - cameraEdgeDistance)).coerceAtMost(worldWidth - halfViewportWidth)
        } else if (characterX < camera.position.x - halfViewportWidth + cameraEdgeDistance) {
            camera.position.x = (characterX + (halfViewportWidth - cameraEdgeDistance)).coerceAtLeast(halfViewportWidth)
        }

//        // 确保相机不超出世界边界，但上面的代码已经能保证相机不越界
//        camera.position.x = camera.position.x.coerceIn(halfViewportWidth, worldWidth - halfViewportWidth)
        camera.update()
    }

    override fun dispose() {
        super.dispose()
        musicTrack.stop()
        assetManager.unload("音乐/th01_02.mp3")
        box2DWorld.dispose()
        debugRenderer.dispose()
    }
}

package Tisawem.GameTesting.Vol1KTXVersion.Character

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.badlogic.gdx.utils.Array
import com.badlogic.gdx.utils.viewport.FitViewport

class Hakurei_Reimu(
    private val assetManager: AssetManager,
    private val viewport: FitViewport,
    val 世界的宽高: Vector2
) :
    Image() {

    //属性
    companion object {
        private val NORMAL_MOVING_SPEED = 10f
        private val SLOWDOWN_MOVING_SPEED = 5f
        private val 博丽灵梦距离相机边缘多少像素时移动相机 = 2.25f
    }

    private val ANIMATION_PLAYING_SPEED
        get() = Gdx.graphics.deltaTime


    private var NOW_MOVING_SPEED = NORMAL_MOVING_SPEED
    private val 相机移动速度: Float
        get() = NOW_MOVING_SPEED
    private var Animation_State_Time = 0f
    private var deltaTime = 0f


    //博丽灵梦的动画帧
    private val Hakurei_Reimu_Right_Animation_Sprites = Array<TextureRegionDrawable>().apply {
        for (i in 0..5) {
            add(TextureRegionDrawable(assetManager["人物/灵梦/右移/灵梦000${i}.png", Texture::class.java]))
        }
        for (i in 4 downTo 1) {
            add(TextureRegionDrawable(assetManager["人物/灵梦/右移/灵梦000${i}.png", Texture::class.java]))
        }
    }
    private val Hakurei_Reimu_Left_Animation_Textures = Array<TextureRegionDrawable>().apply {
        for (i in 0..5) {
            add(TextureRegionDrawable(assetManager["人物/灵梦/左移/灵梦000${i}.png", Texture::class.java]))
        }
        for (i in 4 downTo 1) {
            add(TextureRegionDrawable(assetManager["人物/灵梦/左移/灵梦000${i}.png", Texture::class.java]))
        }
    }


    private val rightMovingAnimation =
        Animation(ANIMATION_PLAYING_SPEED, Hakurei_Reimu_Right_Animation_Sprites, Animation.PlayMode.LOOP_RANDOM)
    private val leftMovingAnimation =
        Animation(ANIMATION_PLAYING_SPEED, Hakurei_Reimu_Left_Animation_Textures, Animation.PlayMode.LOOP_RANDOM)

    //初始化块
    init {
        this.width = .9f
        this.height = 1.25f

        this.drawable = TextureRegionDrawable(Hakurei_Reimu_Right_Animation_Sprites[0])

        this.x = (viewport.worldWidth - this.width) / 2
        this.y = (viewport.worldHeight - this.height) / 2



        this.name = "Hakurei_Reimu ${hashCode()} in tisawem.GameTesting.Vol1.character"


        this.addListener(object : InputListener() {
            override fun keyDown(event: InputEvent?, keycode: Int): Boolean {
                if (keycode == Input.Keys.SHIFT_LEFT) {
                    NOW_MOVING_SPEED = SLOWDOWN_MOVING_SPEED
                }
                return true
            }

            override fun keyUp(event: InputEvent?, keycode: Int): Boolean {
                if (keycode == Input.Keys.SHIFT_LEFT) {
                    NOW_MOVING_SPEED = NORMAL_MOVING_SPEED
                }
                return true
            }
        })
    }

    //所有需要每帧调用的代码放在这里
    override fun act(delta: Float) {
        deltaTime = delta
        is_Key_Pressed()
        推回边界内()

    }


    //所有在render里面放一堆的代码放在这里
    fun is_Key_Pressed() {


        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            this.x -= NOW_MOVING_SPEED * deltaTime
            playing_Animation(deltaTime, leftMovingAnimation)
            左移相机()
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            this.x += NOW_MOVING_SPEED * deltaTime
            playing_Animation(deltaTime, rightMovingAnimation)

            右移相机()


        }

        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            this.y += NOW_MOVING_SPEED * deltaTime

        }

        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            this.y -= NOW_MOVING_SPEED * deltaTime

        }
    }


    fun playing_Animation(delta: Float, animation: Animation<TextureRegionDrawable>) {
        // 更新状态时间
        Animation_State_Time += delta  // delta是两帧之间的时间差

        // 获取当前应该显示的帧

        this.drawable = TextureRegionDrawable(animation.getKeyFrame(Animation_State_Time))

    }

    fun 推回边界内() {


        if (this.y >= 世界的宽高.y - this.height) this.y = 世界的宽高.y - this.height
        if (this.x >= 世界的宽高.x - this.width) this.x = 世界的宽高.x - this.width


        if (this.y <= 0f) this.y = 0f
        if (this.x <= 0f) this.x = 0f

    }

    fun 右移相机() {


        if (viewport.camera.position.x >= 世界的宽高.x - viewport.worldWidth / 2)
            viewport.camera.position.x = 世界的宽高.x - viewport.worldWidth / 2
        else if (this.x >= viewport.camera.position.x + viewport.worldWidth / 2 - 博丽灵梦距离相机边缘多少像素时移动相机)
            viewport.camera.position.x += 相机移动速度 * deltaTime

    }

    fun 左移相机() {
        if (viewport.camera.position.x <= viewport.worldWidth / 2)
            viewport.camera.position.x = viewport.worldWidth / 2
        else if (this.x <= viewport.camera.position.x - viewport.worldWidth / 2 + 博丽灵梦距离相机边缘多少像素时移动相机 / 2)
            viewport.camera.position.x -= 相机移动速度 * deltaTime
    }

}

class HakureiReimu(
    private val assetManager: AssetManager,
    private val worldSize: Vector2
) : Image() {

    companion object {
        private const val NORMAL_MOVING_SPEED = 10f
        private const val SLOWDOWN_MOVING_SPEED = 5f

    }

    private val ANIMATION_FRAME_DURATION = Gdx.graphics.deltaTime
    private var currentMovingSpeed = NORMAL_MOVING_SPEED
    private var animationStateTime = 0f

    //博丽灵梦的动画帧
    private val rightMoveFrames = Array<TextureRegionDrawable>().apply {
        for (i in 0..5) {
            add(TextureRegionDrawable(assetManager["人物/灵梦/右移/灵梦000${i}.png", Texture::class.java]))
        }
        for (i in 4 downTo 1) {
            add(TextureRegionDrawable(assetManager["人物/灵梦/右移/灵梦000${i}.png", Texture::class.java]))
        }
    }
    private val leftMoveFrames = Array<TextureRegionDrawable>().apply {
        for (i in 0..5) {
            add(TextureRegionDrawable(assetManager["人物/灵梦/左移/灵梦000${i}.png", Texture::class.java]))
        }
        for (i in 4 downTo 1) {
            add(TextureRegionDrawable(assetManager["人物/灵梦/左移/灵梦000${i}.png", Texture::class.java]))
        }
    }


    private val rightMovingAnimation =
        Animation(ANIMATION_FRAME_DURATION, rightMoveFrames, Animation.PlayMode.LOOP_RANDOM)
    private val leftMovingAnimation =
        Animation(ANIMATION_FRAME_DURATION, leftMoveFrames, Animation.PlayMode.LOOP_RANDOM)

    init {
        this.setSize(0.9f, 1.25f)
        this.drawable = rightMoveFrames.first()

        // 将角色放置在世界中心
        this.setPosition((worldSize.x - this.width) / 2f, (worldSize.y - this.height) / 2f)
        this.name = "HakureiReimu_${hashCode()}"

        addListener(object : InputListener() {
            override fun keyDown(event: InputEvent?, keycode: Int): Boolean {
                if (keycode == Input.Keys.SHIFT_LEFT) {
                    currentMovingSpeed = SLOWDOWN_MOVING_SPEED
                    return true
                }
                return false
            }

            override fun keyUp(event: InputEvent?, keycode: Int): Boolean {
                if (keycode == Input.Keys.SHIFT_LEFT) {
                    currentMovingSpeed = NORMAL_MOVING_SPEED
                    return true
                }
                return false
            }
        })
    }

    override fun act(delta: Float) {
        super.act(delta)
        animationStateTime += delta
        handleInput(delta)
        keepWithinBounds()
    }

    private fun handleInput(delta: Float) {
        var moving = false

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            this.x -= currentMovingSpeed * delta
            this.drawable = leftMovingAnimation.getKeyFrame(animationStateTime)
            moving = true
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            this.x += currentMovingSpeed * delta
            this.drawable = rightMovingAnimation.getKeyFrame(animationStateTime)
            moving = true
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            this.y += currentMovingSpeed * delta
            moving = true
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            this.y -= currentMovingSpeed * delta
            moving = true
        }

        if (!moving) {


        }
    }

    private fun keepWithinBounds() {
        this.x = this.x.coerceIn(0f, worldSize.x - this.width)
        this.y = this.y.coerceIn(0f, worldSize.y - this.height)
    }


}

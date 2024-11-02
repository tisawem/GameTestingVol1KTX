package Tisawem.GameTesting.Vol1KTXVersion.Character

import Tisawem.GameTesting.Vol1KTXVersion.Main.全局资源管理器
import Tisawem.GameTesting.Vol1KTXVersion.PhysicsActor
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.badlogic.gdx.utils.Array


class HakureiReimu(

    override val worldSize: Vector2
) : PhysicsActor(worldSize) {

    companion object {
        private const val NORMAL_MOVING_SPEED = 15f
        private const val SLOWDOWN_MOVING_SPEED = 10f

    }

    private val ANIMATION_FRAME_DURATION = Gdx.graphics.deltaTime
    private var currentMovingSpeed = NORMAL_MOVING_SPEED
    private var animationStateTime = 0f

    //博丽灵梦的动画帧
    private val rightMoveFrames = Array<TextureRegionDrawable>().apply {
        for (i in 0..5) {
            add(TextureRegionDrawable(全局资源管理器["人物/灵梦/右移/灵梦000${i}.png", Texture::class.java]))
        }
        for (i in 4 downTo 1) {
            add(TextureRegionDrawable(全局资源管理器["人物/灵梦/右移/灵梦000${i}.png", Texture::class.java]))
        }
    }
    private val leftMoveFrames = Array<TextureRegionDrawable>().apply {
        for (i in 0..5) {
            add(TextureRegionDrawable(全局资源管理器["人物/灵梦/左移/灵梦000${i}.png", Texture::class.java]))
        }
        for (i in 4 downTo 1) {
            add(TextureRegionDrawable(全局资源管理器["人物/灵梦/左移/灵梦000${i}.png", Texture::class.java]))
        }
    }


    private val rightMovingAnimation =
        Animation(ANIMATION_FRAME_DURATION, rightMoveFrames, Animation.PlayMode.LOOP_RANDOM)
    private val leftMovingAnimation =
        Animation(ANIMATION_FRAME_DURATION, leftMoveFrames, Animation.PlayMode.LOOP_RANDOM)

    init {
        this.setSize(1.5f, 2.25f)
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

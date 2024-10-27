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
    private val NORMAL_MOVING_SPEED = 10f
    private val SLOWDOWN_MOVING_SPEED = 5f

    private val ANIMATION_PLAYING_SPEED = Gdx.graphics.deltaTime
    private val 博丽灵梦距离相机边缘多少像素时移动相机 = 2.25f

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

/*
在libGDX中，Animation的播放需要配合stateTime(状态时间)来实现。这里是基本用法：

```kotlin
// 创建Animation例子
val animation: Animation<TextureRegion> = Animation<TextureRegion>(
    1/30f,  // 帧率：每帧持续时间(秒)
    textureRegions, // 包含所有帧的数组
    Animation.PlayMode.LOOP // 播放模式：循环播放
)

// 状态时间，用于追踪动画播放进度
var stateTime = 0f

// 在render方法中更新和绘制
override fun render(delta: Float) {
    // 更新状态时间
    stateTime += delta  // delta是两帧之间的时间差

    // 获取当前应该显示的帧
    val currentFrame = animation.getKeyFrame(stateTime)

    // 使用SpriteBatch绘制
    batch.begin()
    batch.draw(
        currentFrame,
        x, y,  // 位置
        width, height // 尺寸
    )
    batch.end()
}
```

重要的API说明：

1. 创建Animation：
```kotlin
Animation(frameDuration: Float, keyFrames: Array<T>, playMode: PlayMode)
```

2. PlayMode选项：
```kotlin
Animation.PlayMode.NORMAL      // 播放一次
Animation.PlayMode.REVERSED    // 反向播放一次
Animation.PlayMode.LOOP        // 循环播放
Animation.PlayMode.LOOP_REVERSED // 循环反向播放
Animation.PlayMode.LOOP_PINGPONG // 循环来回播放
```

3. 常用方法：
```kotlin
// 获取当前帧
animation.getKeyFrame(stateTime)

// 检查动画是否播放完成（仅对非循环模式有效）
animation.isAnimationFinished(stateTime)

// 获取动画总时长
animation.animationDuration

// 设置播放模式
animation.playMode = Animation.PlayMode.LOOP

// 获取指定索引的帧
animation.getKeyFrames()[index]
```

4. 控制播放：
```kotlin
// 暂停
// 只需停止更新stateTime即可
// stateTime += 0

// 重置
stateTime = 0f

// 跳转到特定时间
stateTime = desiredTime
```

5. 加载动画示例：
```kotlin
// 从精灵表加载
val texture = Texture("spritesheet.png")
val FRAME_COLS = 6
val FRAME_ROWS = 5

// 分割精灵表
val tmp = TextureRegion.split(
    texture,
    texture.width / FRAME_COLS,
    texture.height / FRAME_ROWS
)

// 转换为一维数组
val frames = Array<TextureRegion>(FRAME_COLS * FRAME_ROWS)
var index = 0
for (i in 0 until FRAME_ROWS) {
    for (j in 0 until FRAME_COLS) {
        frames[index++] = tmp[i][j]
    }
}

// 创建动画
val animation = Animation(0.025f, frames)
```

注意事项：
1. 要记得在不需要时释放资源（dispose）
2. stateTime通常在游戏状态重置时需要重置
3. 可以用deltaTime来控制动画速度
4. 对于多个动画，每个动画状态可能需要独立的stateTime

如果你有特定的使用场景，我可以提供更具体的示例代码。
 */


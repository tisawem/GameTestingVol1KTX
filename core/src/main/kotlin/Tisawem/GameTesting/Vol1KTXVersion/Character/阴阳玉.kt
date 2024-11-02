package Tisawem.GameTesting.Vol1KTXVersion.Character

import Tisawem.GameTesting.Vol1KTXVersion.Main.全局资源管理器
import Tisawem.GameTesting.Vol1KTXVersion.PhysicsActor
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.CircleShape
import com.badlogic.gdx.physics.box2d.World
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable


class 阴阳玉(worldSize: Vector2) : PhysicsActor(worldSize) {

    init {
        val texture: Texture = 全局资源管理器["其他/阴阳玉1.png", Texture::class.java]
        this.drawable = TextureRegionDrawable(TextureRegion(texture))

        // 设置尺寸
        this.setSize(1f, 1f)

        // 设置初始位置
        this.setPosition(worldSize.x / 2f, worldSize.y / 2f)
    }

    /**
     * 创建 Box2D 物体
     */
    fun createBody(world: World) {
        val shape = CircleShape().apply {
            radius = 0.5f
        }

        createBody(world, BodyDef.BodyType.DynamicBody, shape) {
            density = 1f
            friction = 0.5f
            restitution = 0.6f
        }
    }
}

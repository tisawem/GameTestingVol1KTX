package Tisawem.GameTesting.Vol1KTXVersion

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.*
import com.badlogic.gdx.scenes.scene2d.ui.Image

abstract class PhysicsActor(

    open val worldSize: Vector2
) : Image() {
    protected var body: Body? = null

    /**
     * 创建 Box2D 物体
     */
    fun createBody(world: World, bodyDefType: BodyDef.BodyType, shape: Shape, fixtureDef: FixtureDef.() -> Unit) {
        val bodyDef = BodyDef().apply {
            type = bodyDefType
            position.set(this@PhysicsActor.x, this@PhysicsActor.y)
        }

        body = world.createBody(bodyDef)

        val fixture = FixtureDef().apply {
            this.shape = shape
            fixtureDef()
        }

        body?.createFixture(fixture)

        shape.dispose()
    }

    /**
     * 从 Box2D 物体更新 Actor 的位置
     */
    fun updateFromBody() {
        body?.let {
            this.setPosition(it.position.x - this.width / 2, it.position.y - this.height / 2)
        }
    }

    /**
     * 在需要时应用力或设置速度
     */
    fun applyForce(force: Vector2) {
        body?.applyForceToCenter(force, true)
    }

    /**
     * 销毁 Box2D 物体
     */
    fun destroyBody(world: World) {
        world.destroyBody(body)
        body = null
    }
}

package Tisawem.GameTesting.Vol1KTXVersion.Character

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable


class 阴阳玉(val assetManager: AssetManager, val 世界的宽高: Vector2) : Image() {
    init {
        this.drawable = TextureRegionDrawable(assetManager["其他/阴阳玉1.png", Texture::class.java])

    }

}
/*

 private val world=World(Vector2(0f,0f),true)
    private val bodyDef=BodyDef().apply {
        type=BodyDef.BodyType.DynamicBody
    }

    private val body = world.createBody(bodyDef)



 */

package Tisawem.GameTesting.Vol1KTXVersion

import Tisawem.GameTesting.Vol1KTXVersion.Main.freeTypeFontGenerator
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.audio.Music
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator
import ktx.freetype.generateFont

object 实用工具 {
    //加载读取图片和音乐的操作很频繁，使用load方法时，还要指定它的类型
    //对于特别频繁使用的类型（比如音乐和图片类型），创建了这些扩展函数
    fun AssetManager.loadMusic(内部路径: String) = load(内部路径, Music::class.java)
    fun AssetManager.loadTexture(内部路径: String) = load(内部路径, Texture::class.java)
    fun AssetManager.getTexture(内部路径: String) = get(内部路径, Texture::class.java)
    fun AssetManager.getMusic(内部路径: String) = get(内部路径, Music::class.java)

    //简化需要渲染字体的场景，不想每次使用字体，都去写代码创建FreeTypeFontParameter
    fun getBitmapFontSuitForYourContent(内容: String = FreeTypeFontGenerator.DEFAULT_CHARS, 字号: Int = 24) =
        freeTypeFontGenerator.generateFont {
            size = 字号
            color = Color.WHITE
            borderColor = Color.BROWN
            borderWidth = 2f
            characters = 内容
        }

}


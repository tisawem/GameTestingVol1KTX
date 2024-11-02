package Tisawem.GameTesting.Vol1KTXVersion


import Tisawem.GameTesting.Vol1KTXVersion.Screen.启动页面
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator
import ktx.app.KtxGame
import ktx.app.KtxScreen


/**
 * 几乎所有的类都需要该类的同一个实例，所以它是单例类
 */
object Main : KtxGame<KtxScreen>() {

    //所有屏幕都使用该资源管理器存放，加载，删除资源
    lateinit var 全局资源管理器: AssetManager

    //目前仅实用工具类里面的getBitmapFontSuitForYourContent方法在使用
    //如果需要自定义字体样式，可以使用该Generator
    lateinit var freeTypeFontGenerator: FreeTypeFontGenerator


    override fun create() {

        super.create()
        全局资源管理器 = AssetManager()
        freeTypeFontGenerator = FreeTypeFontGenerator(Gdx.files.internal("SourceHanSansSC-Regular.otf"))
        //不建议一次性添加所有屏幕
        //首先，再次更换到该屏幕，并不会重新初始化它
        //其次，每次添加屏幕，屏幕都会初始化，且大多数屏幕初始化的时候更改了Gdx.input.inputProcessor
        //即使启动页面正在执行，也不耽误初始化其他屏幕，导致其他屏幕初始化了Gdx.input.inputProcessor

        addScreen(启动页面())


        setScreen<启动页面>()
    }


}


package Tisawem.GameTesting.Vol1KTXVersion.Screen


import Tisawem.GameTesting.Vol1KTXVersion.CommonScreen
import Tisawem.GameTesting.Vol1KTXVersion.Main.全局资源管理器
import Tisawem.GameTesting.Vol1KTXVersion.实用工具
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.InputAdapter
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.utils.ScreenUtils
import ktx.assets.disposeSafely

class 启动页面 : CommonScreen() {

    init {
        Gdx.input.inputProcessor = object : InputAdapter() {
            override fun keyDown(keycode: Int): Boolean {

                Gdx.input.inputProcessor = null
                dispose()

                replaceScreen(this@启动页面, MainMenu())


                return super.keyDown(keycode)
            }

            override fun touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
                this.keyDown(0)
                return super.touchDown(screenX, screenY, pointer, button)
            }

            override fun mouseMoved(screenX: Int, screenY: Int): Boolean {
                this.keyDown(0)
                return super.mouseMoved(screenX, screenY)
            }


        }
    }

    private val font = 实用工具.getBitmapFontSuitForYourContent("按任意键，或移动鼠标", 60)

    private val ktxLogo = Sprite(Texture("logo.png")).apply {
        setScale(0.75f)
        setPosition((viewportWidth - width) / 2, (viewportHeight - height) / 2)

    }
    private val 启动界面 = Sprite(Texture("背景/启动界面.png")).apply {
        setSize(viewportWidth, viewportHeight)


    }




    override fun render(delta: Float) {

        // 清屏
        ScreenUtils.clear(0f, 0f, 0f, 1f)

        // 开始绘制
        batch.begin()

        // 绘制背景和 logo
        启动界面.draw(batch)
        ktxLogo.draw(batch)
        if (全局资源管理器.isFinished) {
            font.draw(batch, "按任意键，或移动鼠标", 0f, 450f)
        }

        // 结束绘制
        batch.end()


    }

    override fun resize(width: Int, height: Int) {
        viewport.update(width, height)
    }


    override fun dispose() {

        ktxLogo.texture.disposeSafely()
        启动界面.texture.disposeSafely()
        font.disposeSafely()

        super.dispose()
    }


}



package Tisawem.GameTesting.Vol1KTXVersion

import Tisawem.GameTesting.Vol1KTXVersion.Screen.启动页面
import ktx.app.KtxGame
import ktx.app.KtxScreen


class Main : KtxGame<KtxScreen>() {
    override fun create() {



            addScreen(启动页面(this))
            setScreen<启动页面>()


    }
}


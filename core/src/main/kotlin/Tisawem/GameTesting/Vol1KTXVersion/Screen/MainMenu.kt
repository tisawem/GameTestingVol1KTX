package Tisawem.GameTesting.Vol1KTX.Screen

import Tisawem.GameTesting.Vol1KTXVersion.该项目通用的屏幕
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.math.Vector2
import ktx.app.KtxGame
import ktx.app.KtxScreen


class MainMenu(override val game: KtxGame<KtxScreen>, override val 全局素材管理器: AssetManager) : 该项目通用的屏幕() {
    override val 视口宽度高度 = Vector2(800f, 600f)
}

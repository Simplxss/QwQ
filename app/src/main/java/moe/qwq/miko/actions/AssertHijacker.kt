package moe.qwq.miko.actions

import android.content.Context
import android.content.res.AssetManager
import android.content.res.XModuleResources
import moe.qwq.miko.ext.hookMethod
import moe.qwq.miko.MODULE_PATH

class AssertHijacker : IAction {
    override fun invoke(ctx: Context) {

        AssetManager::class.java.hookMethod("open").after {
            val path = it.args[0] as String
            if (path.startsWith("qwq/")) {
                try {
                    it.result = XModuleResources.createInstance(MODULE_PATH, null).assets.open(path.replace("qwq/", ""))
                } catch (_: Throwable) {
                }
            }
        }
        AssetManager::class.java.hookMethod("openFd").after {
            val path = it.args[0] as String
            if (path.startsWith("qwq/")) {
                try {
                    it.result = XModuleResources.createInstance(MODULE_PATH, null).assets.openFd(path.replace("qwq/", ""))
                } catch (_: Throwable) {
                }
            }
        }
    }

}


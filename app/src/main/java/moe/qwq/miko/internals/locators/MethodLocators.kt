@file:Suppress("LocalVariableName")
package moe.qwq.miko.internals.locators

import android.widget.ImageView
import com.tencent.mobileqq.activity.qqsettingme.config.QQSettingMeBizBean
import com.tencent.mobileqq.data.CardProfile
import com.tencent.mobileqq.profile.vote.VoteHelper
import de.robv.android.xposed.XposedBridge
import de.robv.android.xposed.XposedBridge.log
import moe.fuqiuluo.entries.ClassEnum
import moe.qwq.miko.internals.helper.DvmLocator
import java.lang.reflect.Method
import java.lang.reflect.Modifier

fun interface MethodLocator {
    operator fun invoke(): Pair<Class<*>, Method>?
}

/*
object QQSettingMeConfigGetItemsLocator: MethodLocator {
    override fun invoke(): Pair<Class<*>, Method>? {
        val QQSettingMeConfig = DvmLocator.findClass(ClassEnum.QQSettingMeConfig)
            ?: return null
        val method = QQSettingMeConfig.methods.firstOrNull {
            !Modifier.isStatic(it.modifiers) && it.returnType.isArray && it.returnType.componentType == QQSettingMeBizBean::class.java
        } ?: return null
        return QQSettingMeConfig to method
    }
}
*/

object VoteHelperVoteLocator: MethodLocator {
    override fun invoke(): Pair<Class<*>, Method>? {
        val method = VoteHelper::class.java.declaredMethods.firstOrNull {
            it.parameterCount == 2 && it.parameterTypes[0] == CardProfile::class.java && it.parameterTypes[1] == ImageView::class.java
        } ?: return null
        return VoteHelper::class.java to method
    }
}
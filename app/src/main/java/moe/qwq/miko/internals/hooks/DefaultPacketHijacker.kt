package moe.qwq.miko.internals.hooks

import android.content.Context
import com.tencent.common.app.AppInterface
import com.tencent.qphone.base.remote.ToServiceMsg
import moe.qwq.miko.actions.IAction
import moe.qwq.miko.ext.hookMethod
import moe.qwq.miko.internals.helper.AppRuntimeFetcher
import moe.qwq.miko.internals.setting.QwQSetting
import kotlin.random.Random
import kotlin.random.nextInt

/**
 * 拦截无用发包 + 修复主题验证 + 禁用更新检查
 */
class DefaultPacketHijacker: IAction {
    override fun invoke(ctx: Context) {
        val app = AppRuntimeFetcher.appRuntime
        if (app !is AppInterface) return

        AppInterface::class.java.hookMethod("sendToService").before {
            val to = it.args[0] as ToServiceMsg
            if (QwQSetting.disableUselessPacket && to.serviceCmd in TRASH_PACKET) {
                it.result = Unit
            } else if (QwQSetting.disableUpdateCheck && to.serviceCmd == "ProfileService.CheckUpdateReq") {
                it.result = Unit
            } else if (QwQSetting.oneClickLike && to.serviceCmd == "VisitorSvc.ReqFavorite") {
                var total = 0
                val toServiceMsg = it.args[0] as ToServiceMsg
                while (total != 20) {
                    val random = Random.nextInt(1 .. 10)
                    toServiceMsg.extraData.putInt("iCount", random)
                    app.sendToService(toServiceMsg)
                    total += random
                }
                it.result = Unit
            }
        }
    }

    companion object {
        private val TRASH_PACKET = arrayOf(
            "AuthSvr.ThemeAuth", // 主题验证
            "FeedCloudSvr.trpc.feedcloud.eeveeundealmsg.EeveeMsgChannel.FcUndealMsgs",
            "trpc.qqva.uni_log_server.uni_log_server.Report",
            "OidbSvc.0x5eb_ForTheme",
        )
    }
}
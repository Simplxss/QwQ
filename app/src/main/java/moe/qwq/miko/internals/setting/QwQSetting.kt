package moe.qwq.miko.internals.setting

import com.tencent.mmkv.MMKV
import moe.qwq.miko.tools.MMKVTools
import mqq.app.MobileQQ
import kotlin.reflect.KProperty

object QwQSetting {
    const val INTERCEPT_RECALL = "intercept_recall"
    const val ANTI_BROWSER_ACCESS_RESTRICTIONS = "anti_browser_access_restrictions"
    const val SIMPLIFY_HOMEPAGE_SIDEBAR = "simplify_homepage_sidebar"
    const val DISABLE_UPDATE_CHECK = "disable_update_check"
    const val DISABLE_HOT_UPDATE_SO_BY_TRAFFIC = "disable_hot_update_so_by_traffic"
    const val DISABLE_USELESS_PACKET = "disable_useless_packet"
    const val ONE_KEY_LIKE = "one_click_like"
    const val FORCE_TABLET_MODE = "force_tablet_mode"
    const val SIMPLIFY_BUBBLE_FONT = "simplify_bubble_font"
    const val SIMPLIFY_BUBBLE_AVATAR = "simplify_bubble_avatar"
    const val REPEAT_MESSAGE = "repeat_message"
    const val DISABLE_VISIT_GROUP_ANIMATION = "disable_visit_group_animation"
    const val SUPER_GROUP_FILE = "super_group_file"
    const val SHOW_BAN_OPERATOR = "show_ban_operator"
    const val OPTIMIZE_AT_SORT = "optimize_at_sort"
    const val DISABLE_FLASH_PICTURE = "disable_flash_picture"
    const val ALLOW_GROUP_FLASH_PIC = "allow_group_flash_pic"

    internal val dataDir = MobileQQ.getContext().getExternalFilesDir(null)!!
        .parentFile!!.resolve("Tencent/QwQ").also {
            it.mkdirs()
        }
    private val config: MMKV get() = MMKVTools.mmkvWithId("qwq")
    private val settingMap = hashMapOf(
        INTERCEPT_RECALL to Setting<Boolean>(INTERCEPT_RECALL, SettingType.BOOLEAN),
        ANTI_BROWSER_ACCESS_RESTRICTIONS to Setting<Boolean>(ANTI_BROWSER_ACCESS_RESTRICTIONS, SettingType.BOOLEAN),
        SIMPLIFY_HOMEPAGE_SIDEBAR to Setting<Boolean>(SIMPLIFY_HOMEPAGE_SIDEBAR, SettingType.BOOLEAN),
        DISABLE_UPDATE_CHECK to Setting<Boolean>(DISABLE_UPDATE_CHECK, SettingType.BOOLEAN),
        DISABLE_HOT_UPDATE_SO_BY_TRAFFIC to Setting<Boolean>(DISABLE_HOT_UPDATE_SO_BY_TRAFFIC, SettingType.BOOLEAN),
        DISABLE_USELESS_PACKET to Setting<Boolean>(DISABLE_USELESS_PACKET, SettingType.BOOLEAN),
        ONE_KEY_LIKE to Setting<Boolean>(ONE_KEY_LIKE, SettingType.BOOLEAN),
        FORCE_TABLET_MODE to Setting<Boolean>(FORCE_TABLET_MODE, SettingType.BOOLEAN),
        SIMPLIFY_BUBBLE_FONT to Setting<Boolean>(SIMPLIFY_BUBBLE_FONT, SettingType.BOOLEAN),
        SIMPLIFY_BUBBLE_AVATAR to Setting<Boolean>(SIMPLIFY_BUBBLE_AVATAR, SettingType.BOOLEAN),
        REPEAT_MESSAGE to Setting<Boolean>(REPEAT_MESSAGE, SettingType.BOOLEAN),
        DISABLE_VISIT_GROUP_ANIMATION to Setting<Boolean>(DISABLE_VISIT_GROUP_ANIMATION, SettingType.BOOLEAN),
        SUPER_GROUP_FILE to Setting<Boolean>(SUPER_GROUP_FILE, SettingType.BOOLEAN),
        SHOW_BAN_OPERATOR to Setting<Boolean>(SHOW_BAN_OPERATOR, SettingType.BOOLEAN),
        OPTIMIZE_AT_SORT to Setting<Boolean>(OPTIMIZE_AT_SORT, SettingType.BOOLEAN),
        DISABLE_FLASH_PICTURE to Setting<Boolean>(DISABLE_FLASH_PICTURE, SettingType.BOOLEAN),
        ALLOW_GROUP_FLASH_PIC to Setting<Boolean>(ALLOW_GROUP_FLASH_PIC, SettingType.BOOLEAN),
    )

    var interceptRecall by settingMap[INTERCEPT_RECALL] as Setting<Boolean>
    var antiBrowserAccessRestrictions by settingMap[ANTI_BROWSER_ACCESS_RESTRICTIONS] as Setting<Boolean>
    var simplifyHomepageSidebar by settingMap[SIMPLIFY_HOMEPAGE_SIDEBAR] as Setting<Boolean>
    var disableUpdateCheck by settingMap[DISABLE_UPDATE_CHECK] as Setting<Boolean>
    var disableHotUpdateSoByTraffic by settingMap[DISABLE_HOT_UPDATE_SO_BY_TRAFFIC] as Setting<Boolean>
    var disableUselessPacket by settingMap[DISABLE_USELESS_PACKET] as Setting<Boolean>
    var oneClickLike by settingMap[ONE_KEY_LIKE] as Setting<Boolean>
    var forceTabletMode by settingMap[FORCE_TABLET_MODE] as Setting<Boolean>
    var simplifyBubbleFont by settingMap[SIMPLIFY_BUBBLE_FONT] as Setting<Boolean>
    var simplifyBubbleAvatar by settingMap[SIMPLIFY_BUBBLE_AVATAR] as Setting<Boolean>
    var repeatMessage by settingMap[REPEAT_MESSAGE] as Setting<Boolean>
    var disableVisitGroupAnimation by settingMap[DISABLE_VISIT_GROUP_ANIMATION] as Setting<Boolean>
    var superGroupFile by settingMap[SUPER_GROUP_FILE] as Setting<Boolean>
    var showBanOperator by settingMap[SHOW_BAN_OPERATOR] as Setting<Boolean>
    var optimizeAtSort by settingMap[OPTIMIZE_AT_SORT] as Setting<Boolean>
    var disableFlashPicture by settingMap[DISABLE_FLASH_PICTURE] as Setting<Boolean>
    var allowGroupFlashPic by settingMap[ALLOW_GROUP_FLASH_PIC] as Setting<Boolean>

    val settingUrl: String
        get() = "file:///android_asset/qwq/setting/index.html"

    fun getSetting(key: String): Setting<*> {
        return settingMap[key] ?: Setting<Boolean>(key, SettingType.BOOLEAN)
    }

    enum class SettingType {
        STRING, INT, BOOLEAN
    }

    class Setting<T: Any>(
        val name: String,
        private val type: SettingType
    ) {
        /**
         * 功能不支持该QQ版本时为true
         */
        var isFailed = false

        operator fun getValue(any: Any, property: KProperty<*>?): T {
            val value = when(type) {
                SettingType.STRING -> config.getString(name, "")
                SettingType.INT -> config.getInt(name, 0)
                SettingType.BOOLEAN -> config.getBoolean(name, false)
            }
            return value as T
        }

        operator fun setValue(any: Any, property: KProperty<*>?, t: T) {
            when(type) {
                SettingType.STRING -> config.putString(name, t as String)
                SettingType.INT -> config.putInt(name, t as Int)
                SettingType.BOOLEAN -> config.putBoolean(name, if (t is String) t.toBooleanStrict() else t as Boolean)
            }
        }
    }
}
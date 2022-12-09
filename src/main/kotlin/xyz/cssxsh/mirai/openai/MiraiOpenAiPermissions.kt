package xyz.cssxsh.mirai.openai

import net.mamoe.mirai.console.permission.*
import net.mamoe.mirai.event.*
import kotlin.properties.*
import kotlin.reflect.*

@PublishedApi
internal object MiraiOpenAiPermissions : ReadOnlyProperty<SimpleListenerHost, Permission> {
    private val records: MutableMap<String, Permission> = HashMap()

    @Synchronized
    override fun getValue(thisRef: SimpleListenerHost, property: KProperty<*>): Permission {
        return records[property.name] ?: kotlin.run {
            val permission = PermissionService.INSTANCE.register(
                id = MiraiOpenAiPlugin.permissionId(property.name),
                description = "触发 ${property}_prefix",
                parent = MiraiOpenAiPlugin.parentPermission
            )

            records[property.name] = permission

            MiraiOpenAiPlugin.logger.info("${property}_prefix 权限 ${permission.id}")

            permission
        }
    }
}
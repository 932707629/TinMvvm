package me.soushin.tinmvvm.utils

import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import me.soushin.tinmvvm.listener.ConfigModule
import java.util.*

/**
 *
 * @auther SouShin
 * @time 2020/7/15 14:59
 */
class ManifestParser {
    private val MODULE_VALUE = "ConfigModule"
    private val ctx:Context

    constructor(ctx:Context){
        this.ctx=ctx
    }

    fun parse(): MutableList<ConfigModule>? {
        val modules: MutableList<ConfigModule> = ArrayList<ConfigModule>()
        try {
            val appInfo: ApplicationInfo = ctx.packageManager.getApplicationInfo(
                ctx.packageName, PackageManager.GET_META_DATA)
            if (appInfo.metaData != null) {
                for (key in appInfo.metaData.keySet()) {
                    if (MODULE_VALUE == appInfo.metaData[key]) {
                        modules.add(parseModule(key))
                    }
                }
            }
        } catch (e: PackageManager.NameNotFoundException) {
            throw RuntimeException("Unable to find metadata to parse ConfigModule", e)
        }
        return modules
    }

    private fun parseModule(className: String): ConfigModule {
        val clazz: Class<*>
        clazz = try {
            Class.forName(className)
        } catch (e: ClassNotFoundException) {
            throw IllegalArgumentException("Unable to find ConfigModule implementation", e)
        }
        val module: Any
        module = try {
            clazz.newInstance()
        } catch (e: InstantiationException) {
            throw RuntimeException("Unable to instantiate ConfigModule implementation for $clazz", e)
        } catch (e: IllegalAccessException) {
            throw RuntimeException("Unable to instantiate ConfigModule implementation for $clazz", e)
        }
        if (module !is ConfigModule) {
            throw RuntimeException("Expected instanceof ConfigModule, but found: $module")
        }
        return module
    }



}
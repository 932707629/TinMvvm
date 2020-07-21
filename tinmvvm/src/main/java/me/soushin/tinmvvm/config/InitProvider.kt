package me.soushin.tinmvvm.config

import android.content.ContentProvider
import android.content.ContentValues
import android.database.Cursor
import android.net.Uri
import android.util.Log
import kotlinx.coroutines.Job
import me.soushin.tinmvvm.base.*
import me.soushin.tinmvvm.listener.AppLifecycle
import me.soushin.tinmvvm.listener.ConfigModule
import me.soushin.tinmvvm.utils.*

/**
 * 第三方库初始化操作
 * @auther SouShin
 * @time 2020/6/28 11:19
 */
class InitProvider :ContentProvider() {

    override fun onCreate(): Boolean {
        LogUtils.i("InitProvider.class","初始化开始")
        LogUtils.i("InitProvider", BaseActivity::class.java.simpleName)
        LogUtils.i("InitProvider", BaseApp::class.java.simpleName)
        LogUtils.i("InitProvider", BaseFragment::class.java.simpleName)
        LogUtils.i("InitProvider", BaseModel::class.java.simpleName)
        LogUtils.i("InitProvider", BasePopup::class.java.simpleName)
        LogUtils.i("InitProvider", BaseViewModel::class.java.simpleName)
        LogUtils.i("InitProvider", AppComponent::class.java.simpleName)
        LogUtils.i("InitProvider", AppDelegate::class.java.simpleName)
        LogUtils.i("InitProvider", GlobalConfigModule::class.java.simpleName)
        LogUtils.i("InitProvider", HttpHandleCallBack::class.java.simpleName)
        LogUtils.i("InitProvider", LiveDataTag::class.java.simpleName)
        LogUtils.i("InitProvider", AppLifecycle::class.java.simpleName)
        LogUtils.i("InitProvider", ConfigModule::class.java.simpleName)
        LogUtils.i("InitProvider", AppManager::class.java.simpleName)
        LogUtils.i("InitProvider", FragmentUtils::class.java.simpleName)
        LogUtils.i("InitProvider", KeyboardUtils::class.java.simpleName)
        LogUtils.i("InitProvider", ManifestParser::class.java.simpleName)
        LogUtils.i("InitProvider", FragmentUtils::class.java.simpleName)
        return true
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        return null
    }

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? {
        return null
    }


    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?
    ): Int {
        return 0
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        return 0
    }

    override fun getType(uri: Uri): String? {
        return null
    }
}
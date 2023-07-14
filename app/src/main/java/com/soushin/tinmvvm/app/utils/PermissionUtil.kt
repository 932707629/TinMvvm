package com.soushin.tinmvvm.app.utils

import android.Manifest
import androidx.lifecycle.LifecycleOwner
import com.blankj.ALog
import com.tbruyelle.rxpermissions3.Permission
import com.tbruyelle.rxpermissions3.RxPermissions
import me.soushin.tinmvvm.config.AppComponent
import me.soushin.tinmvvm.rxerror.handler.ErrorHandleSubscriber
import java.util.*

/**
 *
 * @auther SouShin
 * @time 2020/5/13 17:02
 */
object PermissionUtil {

    val TAG = "Permission"

    private fun PermissionUtil() {
        throw IllegalStateException("you can't instantiate me!")
    }

    interface RequestPermission {
        /**
         * 权限请求成功
         */
        fun onRequestPermissionSuccess()

        /**
         * 用户拒绝了权限请求, 权限请求失败, 但还可以继续请求该权限
         *
         * @param permissions 请求失败的权限名
         */
        fun onRequestPermissionFailure(permissions: List<String>?)

        /**
         * 用户拒绝了权限请求并且用户选择了以后不再询问, 权限请求失败, 这时将不能继续请求该权限, 需要提示用户进入设置页面打开该权限
         *
         * @param permissions 请求失败的权限名
         */
        fun onRequestPermissionFailureWithAskNeverAgain(permissions: List<String>?)
    }

    fun requestPermission(
        requestPermission: RequestPermission,
        rxPermissions: RxPermissions,
        lifecycle: LifecycleOwner,
        permissions: Array<String>) {
        if (permissions.isEmpty()) return
        val needRequest: MutableList<String> = ArrayList()
        permissions.forEach {//过滤调已经申请过的权限
            if (!rxPermissions.isGranted(it)) {
                needRequest.add(it)
            }
        }
        if (needRequest.isEmpty()) { //全部权限都已经申请过，直接执行操作
            requestPermission.onRequestPermissionSuccess()
        } else { //没有申请过,则开始申请
            rxPermissions
                .requestEach(*needRequest.toTypedArray())
                .buffer(permissions.size)
                .subscribe(object : ErrorHandleSubscriber<List<Permission>>(lifecycle,AppComponent.rxErrorHandler) {
                    override fun onNext(result: List<Permission>) {
                        super.onNext(result)
                        val failurePermissions: MutableList<String> =
                            ArrayList()
                        val askNeverAgainPermissions: MutableList<String> =
                            ArrayList()
                        for (p in result) {
                            if (!p.granted) {
                                if (p.shouldShowRequestPermissionRationale) {
                                    failurePermissions.add(p.name)
                                } else {
                                    askNeverAgainPermissions.add(p.name)
                                }
                            }
                        }
                        if (failurePermissions.size > 0) {
                            ALog.d("Request permissions failure")
                            requestPermission.onRequestPermissionFailure(failurePermissions)
                        }
                        if (askNeverAgainPermissions.size > 0) {
                            ALog.d("Request permissions failure with ask never again")
                            requestPermission.onRequestPermissionFailureWithAskNeverAgain(
                                askNeverAgainPermissions
                            )
                        }
                        if (failurePermissions.size == 0 && askNeverAgainPermissions.size == 0) {
                            ALog.d("Request permissions success")
                            requestPermission.onRequestPermissionSuccess()
                        }
                    }
                })
        }
    }

    /**
     * 请求摄像头权限
     */
    fun launchCamera(
        requestPermission: RequestPermission,
        rxPermissions: RxPermissions,
        lifecycle: LifecycleOwner) {
        requestPermission(
            requestPermission,
            rxPermissions,
            lifecycle,
            arrayOf(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA
            )
        )
    }

    /**
     * 请求外部存储的权限
     */
    fun externalStorage(
        requestPermission: RequestPermission,
        rxPermissions: RxPermissions,
        lifecycle: LifecycleOwner) {
        requestPermission(
            requestPermission,
            rxPermissions,
            lifecycle,
            arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        )
    }

    /**
     * 请求发送短信权限
     */
    fun sendSms(
        requestPermission: RequestPermission,
        rxPermissions: RxPermissions,
        lifecycle: LifecycleOwner) {
        requestPermission(
            requestPermission,
            rxPermissions,
            lifecycle,
            arrayOf(Manifest.permission.SEND_SMS)
        )
    }

    /**
     * 请求打电话权限
     */
    fun callPhone(
        requestPermission: RequestPermission,
        rxPermissions: RxPermissions,
        lifecycle: LifecycleOwner) {
        requestPermission(
            requestPermission,
            rxPermissions,
            lifecycle,
            arrayOf(Manifest.permission.CALL_PHONE)
        )
    }

    /**
     * 请求获取手机状态的权限
     */
    fun readPhonestate(
        requestPermission: RequestPermission,
        rxPermissions: RxPermissions,
        lifecycle: LifecycleOwner) {
        requestPermission(
            requestPermission,
            rxPermissions,
            lifecycle,
            arrayOf(Manifest.permission.READ_PHONE_STATE)
        )
    }

}
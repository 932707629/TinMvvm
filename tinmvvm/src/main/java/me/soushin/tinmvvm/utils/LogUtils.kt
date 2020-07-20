package me.soushin.tinmvvm.utils

import android.util.Log

/**
 *
 * @auther SouShin
 * @time 2020/7/19 10:02
 */
object LogUtils {


    private var isEnable = true;
    private var TAG = "br";

    /*
     *自定义
     */
    fun changeLog(tag: String, isEnable: Boolean) {
        TAG = tag;
        this.isEnable = isEnable;
    }

    /*
     * 打印information日志
     */
    fun i(msg: String) {
        if (isEnable)
            Log.i(TAG, msg);
    }

    fun i(tag: String, msg: Any) {
        if (isEnable)
            Log.i(tag, msg.toString());
    }

    /*
     * 打印verbose日志
     */
    fun v(msg: String) {
        if (isEnable)
            Log.v(TAG, msg);
    }

    fun v(tag: String, msg: String) {
        if (isEnable)
            Log.v(tag, msg);
    }


    /*
     * 打印debug信息
     */
    fun d(msg: String) {
        if (isEnable)
            Log.d(TAG, msg);
    }

    fun d(tag: String, msg: String) {
        if (isEnable)
            Log.d(tag, msg);
    }

    /*
     * 打印warn日志
     */
    fun w(msg: String) {
        if (isEnable)
            Log.w(TAG, msg);
    }

    fun w(tag: String, msg: String) {
        if (isEnable)
            Log.w(tag, msg);
    }

    /*
     * 打印error日志
     */
    fun e(msg: String) {
        if (isEnable)
            Log.e(TAG, msg);
    }

    fun e(tag: String, msg: String) {
        if (isEnable)
            Log.e(tag, msg);
    }


}
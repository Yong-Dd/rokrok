package com.yongdd.core.common.log

import android.util.Log
import androidx.annotation.WorkerThread
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

object Logger {
    const val BASIC_TAG_NAME = "RokRok"
    private var saveLogHandler : SaveLogHandler? = null
    fun v(message: String) {
        Log.println(Log.VERBOSE, BASIC_TAG_NAME, changeMessage(message))
        saveLogHandler?.saveLogWithLaunch(message = message, type = Log.VERBOSE, tag = BASIC_TAG_NAME)
    }
    fun v(message: String, tag: String) {
        Log.println(Log.VERBOSE, tag, changeMessage(message))
        saveLogHandler?.saveLogWithLaunch(message = message, type = Log.VERBOSE, tag = tag)
    }
    fun d(message: String) {
        Log.println(Log.DEBUG, BASIC_TAG_NAME, changeMessage(message))
        saveLogHandler?.saveLogWithLaunch(message = message, type = Log.DEBUG, tag = BASIC_TAG_NAME)
    }
    fun d(message: String, tag: String) {
        Log.println(Log.DEBUG, tag, changeMessage(message))
        saveLogHandler?.saveLogWithLaunch(message = message, type = Log.DEBUG, tag = tag)
    }
    fun i(message: String) {
        Log.println(Log.INFO, BASIC_TAG_NAME, changeMessage(message))
        saveLogHandler?.saveLogWithLaunch(message = message, type = Log.INFO, tag = BASIC_TAG_NAME)
    }
    fun i(message: String, tag: String) {
        Log.println(Log.INFO, tag, changeMessage(message))
        saveLogHandler?.saveLogWithLaunch(message = message, type = Log.INFO, tag = tag)
    }
    fun w(message: String) {
        Log.println(Log.WARN, BASIC_TAG_NAME, changeMessage(message))
        saveLogHandler?.saveLogWithLaunch(message = message, type = Log.WARN, tag = BASIC_TAG_NAME)
    }
    fun w(message: String, tag: String) {
        Log.println(Log.WARN, tag, changeMessage(message))
        saveLogHandler?.saveLogWithLaunch(message = message, type = Log.WARN, tag = tag)
    }
    fun e(message: String) {
        Log.println(Log.ERROR, BASIC_TAG_NAME, changeMessage(message))
        saveLogHandler?.saveLogWithLaunch(message = message, type = Log.ERROR, tag = BASIC_TAG_NAME)
    }
    fun e(message: String, tag: String) {
        Log.println(Log.ERROR, tag, changeMessage(message))
        saveLogHandler?.saveLogWithLaunch(message = message, type = Log.ERROR, tag = tag)
    }

    fun print(message: String, tag: String, type: Int = Log.INFO) {
        Log.println(type, tag, message)
        saveLogHandler?.saveLogWithLaunch(message = message, type = type, tag = tag)
    }

    private fun changeMessage(message: String) : String {
        val msgBuilder = StringBuilder()
            .append(Thread.currentThread().stackTrace[4].methodName).append("()").append(" : ")
            .append('\t').append(message)
            .append(" (").append(Thread.currentThread().stackTrace[4].fileName).append(":")
            .append(Thread.currentThread().stackTrace[4].lineNumber).append(")");


        return msgBuilder.toString()
    }

    fun setSaveLogHandler(saveLogHandler : SaveLogHandler){
        this.saveLogHandler = saveLogHandler
    }
}

abstract class SaveLogHandler{
    private val scopeApplication by lazy { CoroutineScope(SupervisorJob()) }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    fun saveLogWithLaunch(message : String, type: Int, tag : String){
        scopeApplication.launch {
            saveLog(message = message, type = type, tag = tag)
        }
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    protected abstract suspend fun saveLog(message : String, type : Int, tag : String)
}
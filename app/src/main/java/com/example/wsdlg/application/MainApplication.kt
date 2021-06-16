package com.example.wsdlg.application

import android.app.Application
import android.content.Context
import com.hyphenate.chat.EMClient
import me.jessyan.autosize.AutoAdaptStrategy
import me.jessyan.autosize.AutoSize
import me.jessyan.autosize.AutoSizeConfig
import me.jessyan.autosize.DefaultAutoAdaptStrategy
import com.hyphenate.chat.EMOptions

/**
 *  created by ws
 *   on 2021/6/2
 *   describe:
 */
class MainApplication : Application() {
    companion object {
        var application = this
    }

    override fun onCreate() {
        super.onCreate()
        initAutoSize()
        initEm()
    }

    private fun initEm() {
        val emOptions = EMOptions()
        /*//默认添加好友时,是不需要验证的,改成需要验证
        emOptions.acceptInvitationAlways = false*/

        //自动将消息附件上传到环信服务器,如果设置为false 需要开发者自己处理附件消息的上传与下载
        // 是否自动下载附件类消息的缩略图等，默认为 true 这里和上边这个参数相关联
        emOptions.autoTransferMessageAttachments = true
        emOptions.setAutoDownloadThumbnail(true)

        //初始化
        EMClient.getInstance().init(applicationContext, emOptions)
        //在做打包混淆时，关闭debug模式，避免消耗不必要的资源
        EMClient.getInstance().setDebugMode(true);
    }

    private fun initAutoSize() {
        AutoSize.initCompatMultiProcess(this)
        AutoSizeConfig.getInstance().apply {
            this.designWidthInDp = 375
            this.designHeightInDp = 667
            this.setAutoAdaptStrategy(object : DefaultAutoAdaptStrategy() {})
            isExcludeFontScale = true
        }

    }
}
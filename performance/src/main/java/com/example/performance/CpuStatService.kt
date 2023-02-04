package com.example.performance

import android.app.Service
import android.content.Intent
import android.os.*
import androidx.annotation.RequiresApi
import com.example.performance.data.DataMessenger
import kotlinx.coroutines.*


/**
 * 客户端发出的 请求 业务类型
 */
const val EVENT_STYLE_CPU_USAGE = 1

class CpuStatService : Service() {

    private lateinit var handlerThread: HandlerThread
    private var job: Job? = null
    private val serviceMessenger by lazy {
        Messenger(Handler(handlerThread.looper, callback))
    }

    /**
     * 客户端发出消息,  请求 cpu 使用率.
     * 服务端 将 [serviceMessenger] 作为 IBinder 返回给服务端,, 客户端 通过此 [serviceMessenger.send] 发送消息给服务端
     *
     * 客户端向服务端发送消息: 定义发送消息[Message],和接受消息[clientMessenger]对象. 并通过[Message.replyTo= clientMessenger]
     * 将要发送的消息与[clientMessenger] 进行绑定.
     *
     * 服务端处理消息:  服务端接收到消息后, 处理业务, 得到结果, 通过[Message.replyTo]得到 [clientMessenger], 将结果通过[Messenger.send]
     *                方法回复给客户端,  整个消息流程结束.
     */
    private val callback = Handler.Callback {
        val clientMessenger = it.replyTo ?: return@Callback true
        //进行相关业务计算.得到结果后 通过 clientMessage.send 方法发送消息回复给客户端.
        when (it.what) {
            EVENT_STYLE_CPU_USAGE -> {
                calculateCpuUsage(it)
            }
        }

        true
    }


    override fun onCreate() {
        super.onCreate()
        handlerThread = HandlerThread(this.javaClass.simpleName)
        handlerThread.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        handlerThread.quit()
    }

    override fun onBind(intent: Intent): IBinder {
        return serviceMessenger.binder
    }


    //计算 cpu 使用率..
    private fun calculateCpuUsage(it: Message) {
        val replyTo = it.replyTo ?: return

        replyTo.send(Message().apply {
            data = Bundle().apply { putParcelable("dataMessenger", DataMessenger("haha")) }
        })
    }

}
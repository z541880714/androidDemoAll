package com.example.performance

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.*
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.performance.data.DataMessenger
import com.example.performance.ui.theme.AndroidDemoAllTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndroidDemoAllTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting("Android") {
                        sendMessage()
                    }
                }
            }
        }

        bindMyService()
    }

    private fun bindMyService() {
        this.bindService(
            Intent(this, CpuStatService::class.java),
            connection,
            Context.BIND_AUTO_CREATE
        )
    }


    private val clientMessenger: Messenger by lazy {
        Messenger(Handler(mainLooper) {
            Log.i(
                "log_zc",
                "MainActivity-> clientMessenger: ${it.data.getParcelable<DataMessenger>("dataMessenger")}"
            )
            true
        })
    }
    private lateinit var serviceBinder: Messenger
    private var bindingState = false
    private val connection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            Log.i("log_zc", "MainActivity-> onServiceConnected: success!!!")
            bindingState = true
            serviceBinder = Messenger(service ?: return)
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            bindingState = false

        }

    }

    private fun sendMessage() {
        if (!this::serviceBinder.isInitialized) return
        val msg = Message().apply {
            what = EVENT_STYLE_CPU_USAGE
            replyTo = clientMessenger
        }
        Log.i("log_zc", "MainActivity-> sendMessage: click !!")
        serviceBinder.send(msg)
    }
}


@Composable
fun Greeting(name: String, click: () -> Unit) {
    Text(text = "Hello $name!", modifier = Modifier.clickable { click() })
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    AndroidDemoAllTheme {
        Greeting("Android") {}
    }
}
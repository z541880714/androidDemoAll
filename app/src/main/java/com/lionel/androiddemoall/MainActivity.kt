package com.lionel.androiddemoall

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.core.view.*
import androidx.core.view.WindowInsetsCompat.Type.systemBars
import com.google.accompanist.insets.*
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.lionel.androiddemoall.ui.theme.AndroidDemoAllTheme
import com.lionel.androiddemoall.ui.theme.ImmerseTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        // compose 沉浸式
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            rememberSystemUiController().setStatusBarColor(Color.Transparent)

            ImmerseTheme {
                //compose 沉浸式...
                ProvideWindowInsets {
                    Box(modifier = Modifier.statusBarsPadding()) {
                        Greeting("android")
                    }
                }
            }


        }
    }
}

fun Modifier.statusBarsPadding(): Modifier = composed {
    padding(
        rememberInsetsPaddingValues(
            insets = LocalWindowInsets.current.statusBars,
            applyTop = true
        ).apply {
            Log.i("log_zc", "statusBarsPadding: top:${this.calculateTopPadding()}")
        }
    )
}


@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    AndroidDemoAllTheme {
        Greeting("Android")
    }
}
package com.lionel.androiddemoall

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.core.view.*
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.lionel.androiddemoall.ui.theme.AndroidDemoAllTheme
import com.lionel.androiddemoall.ui.theme.ImmerseTheme


/**
 * compose 沉浸式 ui
 * 方式1: 使用 [ProvideWindowInsets] 会根据系统 状态栏, 导航栏做对应的偏移
 * 方式2: 手动计算 statusBar 的高度,手动做 偏移 [getStatusBarHeight]
 */
class ImmerseActivity : ComponentActivity() {
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


fun Context.getStatusBarHeight(): Dp {
    val resId = resources.getIdentifier("status_bar_height", "dimen", "android")
    val pixel = resources.getDimensionPixelSize(resId)
    val density = resources.displayMetrics.density
    return (pixel / density).toInt().dp
}

fun Modifier.getStatusBarHeight(): Modifier = composed {
    val context = LocalContext.current
    val resid = context.resources.getIdentifier("status_bar_height", "dimen", "android")
    this.then(height(dimensionResource(id = resid)))
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
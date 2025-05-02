package com.rafsan.babytube

import VideoScreenList
import YouTubePlayerComposable
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.rafsan.babytube.ui.theme.BabyTubeTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BabyTubeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val items = (1..12).map { "Video $it" }

                    VideoScreenList  (
                        items = items,
                        columns = 3,
                        modifier = Modifier.padding(innerPadding)
                    ) { selected ->
                        println("Currently selected: $selected")
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    BabyTubeTheme {
        Greeting("Android")
    }
}
package com.rafsan.babytube

import VideoScreenList
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.rafsan.babytube.ui.theme.BabyTubeTheme


class MainActivity : ComponentActivity() {

    val mainViewModel : MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        mainViewModel.fetchVideoList()
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
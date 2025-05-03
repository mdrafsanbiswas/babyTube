package com.rafsan.babytube

import VideoScreenList
import YouTubePlayerScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.rafsan.babytube.ui.theme.BabyTubeTheme
import dagger.hilt.android.AndroidEntryPoint
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.google.gson.Gson
import com.rafsan.babytube.data.DataState
import com.rafsan.babytube.data.VideoItem
import com.rafsan.babytube.ui.theme.screens.WebScreen
import kotlinx.coroutines.flow.collectLatest


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val mainViewModel : MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        mainViewModel.fetchVideoList()

        setContent {

            var videoList : List<VideoItem> ? by remember {
                mutableStateOf(null)
            }

            var loadingState by remember {
                mutableStateOf(false)
            }

            LaunchedEffect(Unit) {

                mainViewModel.videoList.collectLatest {
                    when(it) {
                        DataState.Loading -> {
                          loadingState = true
                        }
                        is DataState.Success -> {
                           videoList = it.data
                           loadingState = false
                        }
                        null -> {
                            loadingState = false
                        }
                    }
                }
            }


            BabyTubeTheme {
                val navController = rememberNavController()

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = "video_list",
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable("video_list") {

                            if (loadingState) {
                                CircularProgressIndicator()
                            } else {
                                VideoScreenList(
                                    items = videoList,
                                    onItemSelected = { item ->
                                        val data = Gson().toJson(item)
                                        navController.navigate("video_detail/$data")
                                    },
                                )
                            }
                        }

                        composable(
                            route = "video_detail/{data}",
                        ) { backStackEntry ->
                            val rawData = backStackEntry.arguments?.getString("data")
                            val data = Gson().fromJson(rawData, VideoItem::class.java)

                            when(data.type) {
                                "youtube" -> {
                                    YouTubePlayerScreen(
                                        videoId = data.videoId,
                                    )
                                }

                                "webview" -> {
                                    WebScreen(data.url, onBackPressed = { navController.popBackStack()})
                                }
                            }

                        }
                    }
                }
            }
        }
    }
}

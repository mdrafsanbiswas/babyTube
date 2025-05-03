package com.rafsan.babytube

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rafsan.babytube.data.DataState
import com.rafsan.babytube.data.VideoItem
import com.rafsan.babytube.repo.VideoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: VideoRepository
) : ViewModel() {

   private val _videoList = MutableStateFlow<DataState<List<VideoItem>>?>(null)
   val videoList = _videoList.asStateFlow()

    fun fetchVideoList() {
       viewModelScope.launch {
           repository.getVideos().collectLatest {
               _videoList.value = it
           }
       }
    }
}
package com.rafsan.babytube

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rafsan.babytube.repo.VideoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: VideoRepository
) : ViewModel() {

    fun fetchVideoList() {
       viewModelScope.launch {
           repository.getVideos().collectLatest {

           }
       }
    }
}
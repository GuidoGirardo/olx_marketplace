package com.guido.olx_marketplace.ui.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.guido.olx_marketplace.model.firebase.getPost

class AppViewModel : ViewModel() {
    private val _postList = MutableLiveData<List<Map<String, Any>>>()
    val postList: LiveData<List<Map<String, Any>>> = _postList

    var currentUser: MutableState<String?> = mutableStateOf(null)
    init {
        getPost { posts ->
            _postList.value = posts
        }
    }

}
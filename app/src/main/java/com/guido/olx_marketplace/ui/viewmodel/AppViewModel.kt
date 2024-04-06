package com.guido.olx_marketplace.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.guido.olx_marketplace.model.firebase.getPost

class AppViewModel : ViewModel() {
    private val _postList = MutableLiveData<List<Map<String, Any>>>()
    val postList: LiveData<List<Map<String, Any>>> = _postList

    init {
        getPost { posts ->
            _postList.value = posts
        }
    }

}
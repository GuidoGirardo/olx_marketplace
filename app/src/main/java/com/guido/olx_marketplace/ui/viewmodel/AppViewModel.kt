package com.guido.olx_marketplace.ui.viewmodel

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.guido.olx_marketplace.model.firebase.getPost
import com.guido.olx_marketplace.model.firebase.getPostsForUserFirestore

class AppViewModel : ViewModel() {
    private val _postList = MutableLiveData<List<Map<String, Any>>>()
    val postList: LiveData<List<Map<String, Any>>> = _postList


    private val _postUserList = MutableLiveData<List<Map<String, Any>>>()
    val postUserList: LiveData<List<Map<String, Any>>> = _postUserList

    var currentUser: MutableState<String?> = mutableStateOf(null)
    init {
        getPost { posts ->
            _postList.value = posts
        }
    }

    fun getPostsForUser(user: String) {
        getPostsForUserFirestore(user,
            onSuccess = { fetchedPosts ->
                _postUserList.postValue(fetchedPosts)
            },
            onFailure = { exception ->
                Log.i("xd", "algo fallo en viewmodel")
            }
        )
    }


}
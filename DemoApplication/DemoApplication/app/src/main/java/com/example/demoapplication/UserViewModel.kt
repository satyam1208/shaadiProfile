package com.example.demoapplication

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class UserViewModel : ViewModel() {
    private val _userList = MutableLiveData<List<Result>>()
    val userList: LiveData<List<Result>> get() = _userList

    fun fetchUserList() {
        viewModelScope.launch {
            try {
                val apiService = ApiClient.retrofit.create(ApiService::class.java)
                val response = apiService.getUsers()
                if (response.isSuccessful) {
                    val userListResponse = response.body()
                    userListResponse?.let {
                        _userList.value = it.results
                    }
                } else {
                    // Handle error response
                }
            } catch (e: Exception) {
                // Handle network failure
            }
        }
    }
}

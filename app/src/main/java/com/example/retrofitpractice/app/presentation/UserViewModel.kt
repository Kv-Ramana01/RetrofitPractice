package com.example.retrofitpractice.app.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.retrofitpractice.app.data.User // Using DTO directly
import com.example.retrofitpractice.app.data.RetrofitInstance
import kotlinx.coroutines.launch

class UserViewModel : ViewModel() {

    // LiveData holding the UserDto directly from the API
    private val _user = MutableLiveData<User?>()
    val user: LiveData<User?> = _user

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun fetchUser(userId: Int) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.getUserById(userId)
                if (response.isSuccessful) {
                    _user.value = response.body()
                    _error.value = null
                } else {
                    _user.value = null
                    _error.value = "Error: ${response.code()} ${response.message()}"
                }
            } catch (e: Exception) {
                _user.value = null
                _error.value = "Exception: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }
}
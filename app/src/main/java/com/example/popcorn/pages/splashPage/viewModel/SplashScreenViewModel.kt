//package com.example.popcorn.pages.splashPage.viewModel
//
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import kotlinx.coroutines.delay
//import kotlinx.coroutines.flow.MutableStateFlow
//import kotlinx.coroutines.flow.StateFlow
//import kotlinx.coroutines.launch
//
//
//class SplashViewModel : ViewModel() {
//
//    private val _navigateToLogin = MutableStateFlow(false)
//    val navigateToLogin: StateFlow<Boolean> get() = _navigateToLogin
//
//    init {
//        startSplashTimer()
//    }
//
//    private fun startSplashTimer() {
//        viewModelScope.launch {
//            delay(3000) // 3 saniye bekleme
//            _navigateToLogin.value = true
//        }
//    }
//}
//


package com.example.popcorn.pages.splashPage.viewModel

import android.annotation.SuppressLint
import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch



class SplashViewModel : ViewModel() {

    private val _navigateToMain = MutableLiveData<Boolean>()
    val navigateToMain: LiveData<Boolean> get() = _navigateToMain

    private val _isSplashScreenVisible = MutableLiveData<Boolean>(true) // Splash ekranının başlangıçta görünürlüğü
    val isSplashScreenVisible: LiveData<Boolean> get() = _isSplashScreenVisible

    init {
       OpenSplashScreen()
    }

    fun OpenSplashScreen() {
        _isSplashScreenVisible.value = true // Splash ekranını göster
        viewModelScope.launch {
            delay(1000) // 3 saniye bekle
            // Ana ekrana yönlendir
            _navigateToMain.value = true
        }
    }

    fun CloseSplashScreen() {

     //   fetchDataAndUpdateDatabase {
            // Veriler yüklendi veya hata alındı
//            _navigateToMain.value = true
            _isSplashScreenVisible.value = false // Splash ekranını kapat
      //  }
    }
}



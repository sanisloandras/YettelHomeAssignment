package com.szaniszlo.yettelhomeassignment.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.szaniszlo.yettelhomeassignment.domain.usecase.GetHighwayInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MainVignettePurchaseViewModel @Inject constructor(
    private val getHighwayInfoUseCase: GetHighwayInfoUseCase,
) : ViewModel() {

}
package com.szaniszlo.yettelhomeassignment.ui

import com.szaniszlo.yettelhomeassignment.MainDispatcherExtension
import com.szaniszlo.yettelhomeassignment.ui.countyvignettes.CountyVignettePurchaseViewModel
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class,MainDispatcherExtension::class)
class CountyVignettePurchaseViewModelTest {

    private fun createSut() = CountyVignettePurchaseViewModel()
}
package com.szaniszlo.yettelhomeassignment.ui

import com.szaniszlo.yettelhomeassignment.MainDispatcherExtension
import com.szaniszlo.yettelhomeassignment.domain.usecase.GetHighwayInfoUseCase
import com.szaniszlo.yettelhomeassignment.ui.main.MainVignettePurchaseViewModel
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class,MainDispatcherExtension::class)
class MainVignettePurchaseViewModelTest {

    @MockK
    private lateinit var mockGetHighwayInfoUseCase: GetHighwayInfoUseCase

    private fun createSut() = MainVignettePurchaseViewModel(
        getHighwayInfoUseCase = mockGetHighwayInfoUseCase,
        getVehicleUseCase = mockk(),
        storeVignetteSelectionUseCase = mockk()
    )
}
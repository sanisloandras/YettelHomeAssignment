package com.szaniszlo.yettelhomeassignment.ui

import com.szaniszlo.yettelhomeassignment.MainDispatcherExtension
import com.szaniszlo.yettelhomeassignment.ui.orderconfirmation.OrderConfirmationViewModel
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class,MainDispatcherExtension::class)
class OrderConfirmationViewModelTest {

    private fun createSut() = OrderConfirmationViewModel()
}
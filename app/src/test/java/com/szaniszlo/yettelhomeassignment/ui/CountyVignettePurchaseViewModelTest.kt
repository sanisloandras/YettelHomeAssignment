package com.szaniszlo.yettelhomeassignment.ui

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.szaniszlo.yettelhomeassignment.MainDispatcherExtension
import com.szaniszlo.yettelhomeassignment.domain.model.county.County
import com.szaniszlo.yettelhomeassignment.domain.usecase.GetCountiesWithCostUseCase
import com.szaniszlo.yettelhomeassignment.domain.usecase.GetCountryGeoJsonUseCase
import com.szaniszlo.yettelhomeassignment.ui.countyvignettes.CountyVignettePurchaseUiState
import com.szaniszlo.yettelhomeassignment.ui.countyvignettes.CountyVignettePurchaseViewModel
import com.szaniszlo.yettelhomeassignment.ui.countyvignettes.SelectableCounty
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class, MainDispatcherExtension::class)
class CountyVignettePurchaseViewModelTest {

    private val savedStateHandle = SavedStateHandle()

    @MockK
    private lateinit var mockGetCountiesWithCostUseCase: GetCountiesWithCostUseCase

    @MockK
    private lateinit var mockGetCountryGeoJsonUseCase: GetCountryGeoJsonUseCase

    @BeforeEach
    fun beforeEach() {
        every {
            mockGetCountiesWithCostUseCase()
        } returns flowOf(COUNTIES)

        coEvery {
            mockGetCountryGeoJsonUseCase()
        } returns null
    }

    @Test
    fun `verify default state is correct`() = runTest {
        // given
        val viewModel = createSut()

        // when
        viewModel.uiState.test {
            // then
            assertThat(awaitItem()).isEqualTo(
                CountyVignettePurchaseUiState.Default(
                    counties = SELECTABLE_COUNTIES,
                    totalCost = 0f,
                    isDirectConnectionPresent = true,
                    geoJson = null,
                    selectedCountyNames = emptySet(),
                )
            )
        }
    }

    @Test
    fun `when county is checked then total cost is correct`() = runTest {
        // given
        val viewModel = createSut()

        // when
        viewModel.onCountyCheckChanged(countyId = "YEAR_29")

        viewModel.uiState.test {
            // then
            assertThat(
                (awaitItem() as CountyVignettePurchaseUiState.Default).totalCost
            ).isEqualTo(5450f)
        }
    }

    @Test
    fun `when attempt to continue without selecting at least one county then show warning`() =
        runTest {
            // given
            val viewModel = createSut()

            viewModel.noCountySelectedEvent.test {
                // when
                viewModel.next()

                // then
                assertThat(awaitItem()).isEqualTo(Unit)
            }
        }

    @Test
    fun `when single county is checked then direct connection is present`() = runTest {
        // given
        val viewModel = createSut()

        // when
        viewModel.onCountyCheckChanged(countyId = "YEAR_27")

        viewModel.uiState.test {
            // then
            assertThat((awaitItem() as CountyVignettePurchaseUiState.Default).isDirectConnectionPresent).isTrue()
        }
    }

    @Test
    fun `when only neighbour counties are checked then direct connection is present`() = runTest {
        // given
        val viewModel = createSut()

        // when
        viewModel.onCountyCheckChanged(countyId = "YEAR_27")
        viewModel.onCountyCheckChanged(countyId = "YEAR_29")

        viewModel.uiState.test {
            // then
            assertThat((awaitItem() as CountyVignettePurchaseUiState.Default).isDirectConnectionPresent).isTrue()
        }
    }

    @Test
    fun `when not only neighbour counties are checked then direct connection is absent`() =
        runTest {
            // given
            val viewModel = createSut()

            // when
            viewModel.onCountyCheckChanged(countyId = "YEAR_27")
            viewModel.onCountyCheckChanged(countyId = "YEAR_29")
            viewModel.onCountyCheckChanged(countyId = "YEAR_15")

            viewModel.uiState.test {
                // then
                assertThat((awaitItem() as CountyVignettePurchaseUiState.Default).isDirectConnectionPresent).isFalse()
            }
        }

    @Test
    fun `when next called then navigate to confirm order and store vignette selection`() =
        runTest {
            // given
            val viewModel = createSut().apply {
                onCountyCheckChanged(countyId = "YEAR_27")
                onCountyCheckChanged(countyId = "YEAR_29")
            }

            // when
            viewModel.navigateToConfirmOrder.test {
                viewModel.next()

                // then
                assertThat(awaitItem()).isEqualTo("YEAR_27,YEAR_29")
            }
        }

    private fun createSut() = CountyVignettePurchaseViewModel(
        savedStateHandle = savedStateHandle,
        getCountiesUseCase = mockGetCountiesWithCostUseCase,
        getCountryGeoJsonUseCase = mockGetCountryGeoJsonUseCase,
    )

    companion object {
        private val COUNTIES = listOf(
            County(
                id = "YEAR_15",
                name = "Csongrád",
                cost = 5450f,
                trxFee = 120f,
                sum = 5570f,
            ),
            County(
                id = "YEAR_27",
                name = "Vas",
                cost = 5450f,
                trxFee = 120f,
                sum = 5570f,
            ),
            County(
                id = "YEAR_29",
                name = "Zala",
                cost = 5450f,
                trxFee = 120f,
                sum = 5570f,
            ),
        )

        val SELECTABLE_COUNTIES = listOf(
            SelectableCounty(
                isSelected = false,
                county = County(
                    id = "YEAR_15",
                    name = "Csongrád",
                    cost = 5450f,
                    trxFee = 120f,
                    sum = 5570f,
                ),
            ),
            SelectableCounty(
                isSelected = false,
                county = County(
                    id = "YEAR_27",
                    name = "Vas",
                    cost = 5450f,
                    trxFee = 120f,
                    sum = 5570f,
                ),
            ),
            SelectableCounty(
                isSelected = false,
                county = County(
                    id = "YEAR_29",
                    name = "Zala",
                    cost = 5450f,
                    trxFee = 120f,
                    sum = 5570f,
                ),
            )
        )
    }
}
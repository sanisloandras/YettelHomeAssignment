package com.szaniszlo.yettelhomeassignment.ui.orderconfirmation

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.szaniszlo.yettelhomeassignment.R
import com.szaniszlo.yettelhomeassignment.domain.model.county.County
import com.szaniszlo.yettelhomeassignment.domain.model.orderconfirmation.OrderConfirmationData
import com.szaniszlo.yettelhomeassignment.domain.model.orderconfirmation.OrderVignetteType
import com.szaniszlo.yettelhomeassignment.ui.components.ErrorLayout
import com.szaniszlo.yettelhomeassignment.ui.components.FullScreenLoader
import com.szaniszlo.yettelhomeassignment.ui.components.TotalCostComponent
import com.szaniszlo.yettelhomeassignment.ui.components.YettelAppBar
import com.szaniszlo.yettelhomeassignment.ui.core.theme.Typography
import com.szaniszlo.yettelhomeassignment.ui.core.theme.yettelBlue
import com.szaniszlo.yettelhomeassignment.ui.util.formatPlate
import java.text.NumberFormat

@Composable
fun OrderConfirmationScreen(
    viewModel: OrderConfirmationViewModel = hiltViewModel(),
    onOrderConfirmed: () -> Unit,
    onCancel: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.successfulOrder.collect { onOrderConfirmed() }
    }

    OrderConfirmationScreenContent(
        uiState = uiState,
        onOrderConfirmed = viewModel::confirmOrder,
        onRetryClicked = viewModel::confirmOrder,
        onCancel = onCancel,
    )
}

@Composable
private fun OrderConfirmationScreenContent(
    uiState: OrderConfirmationUiState,
    onOrderConfirmed: () -> Unit,
    onRetryClicked: () -> Unit,
    onCancel: () -> Unit,
) {
    Scaffold(
        topBar = {
            YettelAppBar(
                onBackClicked = onCancel,
            )
        }
    ) {
        AnimatedContent(
            targetState = uiState,
            modifier = Modifier.padding(it)
        ) { uiState ->
            when (uiState) {
                is OrderConfirmationUiState.Default -> DefaultContent(
                    uiState = uiState,
                    onOrderConfirmed = onOrderConfirmed,
                    onCancel = onCancel
                )

                OrderConfirmationUiState.Loading -> FullScreenLoader()
                OrderConfirmationUiState.Error -> ErrorLayout(
                    message = R.string.order_error_message,
                    onRetryClicked = onRetryClicked
                )
            }
        }
    }
}

@Composable
private fun DefaultContent(
    uiState: OrderConfirmationUiState.Default,
    onOrderConfirmed: () -> Unit,
    onCancel: () -> Unit,
) {
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .padding(all = 16.dp)
    ) {
        Text(
            text = stringResource(id = R.string.confirm_order),
            style = Typography.headlineSmall,
            fontWeight = FontWeight.Bold,
        )

        Spacer(modifier = Modifier.height(8.dp))

        HorizontalDivider()

        Spacer(modifier = Modifier.height(16.dp))

        KeyValuePair(
            key = stringResource(id = R.string.plate_number),
            value = uiState.orderConfirmationData.plate.uppercase().formatPlate(),
        )

        Spacer(modifier = Modifier.height(8.dp))

        KeyValuePair(
            key = stringResource(id = R.string.order_vignette_type),
            value = stringResource(
                id = when (uiState.orderConfirmationData.orderVignetteType) {
                    OrderVignetteType.YEAR_COUNTY -> R.string.order_vignette_type_year_county
                    OrderVignetteType.YEAR -> R.string.order_vignette_type_year
                    OrderVignetteType.MONTH -> R.string.order_vignette_type_month
                    OrderVignetteType.WEEK -> R.string.order_vignette_type_week
                    OrderVignetteType.DAY -> R.string.order_vignette_type_day
                }
            ),
        )

        Spacer(modifier = Modifier.height(8.dp))

        HorizontalDivider()

        Spacer(modifier = Modifier.height(16.dp))

        if (uiState.orderConfirmationData.counties.isNotEmpty()) {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                uiState.orderConfirmationData.counties.forEach { confirmedCounty ->
                    KeyValuePair(
                        key = confirmedCounty.name,
                        isKeyBold = true,
                        value = stringResource(
                            id = R.string.amount,
                            NumberFormat.getInstance().format(confirmedCounty.cost)
                        ),
                    )
                }

                KeyValuePair(
                    key = stringResource(id = R.string.trx_fee),
                    value = stringResource(
                        id = R.string.amount,
                        NumberFormat.getInstance().format(uiState.orderConfirmationData.trxFee),
                    )
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
            HorizontalDivider()
            Spacer(modifier = Modifier.height(16.dp))
        }

        TotalCostComponent(
            totalCost = uiState.orderConfirmationData.totalCost,
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = onOrderConfirmed,
            colors = ButtonDefaults.buttonColors().copy(
                containerColor = yettelBlue
            ),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = stringResource(id = R.string.proceed))
        }

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedButton(
            onClick = onCancel,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = stringResource(id = R.string.cancel))
        }
    }
}

@Composable
private fun KeyValuePair(
    key: String,
    isKeyBold: Boolean = false,
    value: String,
) {
    Row {
        Text(
            text = key,
            fontWeight = if (isKeyBold) FontWeight.Bold else FontWeight.Normal,
        )
        Text(
            text = value,
            textAlign = TextAlign.End,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
@Preview
private fun OrderConfirmationScreenPreview(
    @PreviewParameter(OrderConfirmationUiStatePreviewProvider::class) uiState: OrderConfirmationUiState,
) {
    OrderConfirmationScreenContent(
        uiState = uiState,
        onOrderConfirmed = {},
        onRetryClicked = {},
        onCancel = {}
    )
}

private class OrderConfirmationUiStatePreviewProvider :
    PreviewParameterProvider<OrderConfirmationUiState> {

    override val values = sequenceOf<OrderConfirmationUiState>(
        OrderConfirmationUiState.Default(
            orderConfirmationData = OrderConfirmationData(
                plate = "abc-123",
                orderVignetteType = OrderVignetteType.WEEK,
                counties = emptyList(),
                trxFee = 110f,
                totalCost = 6000f,
            ),
        ),
        OrderConfirmationUiState.Default(
            orderConfirmationData = OrderConfirmationData(
                plate = "abc-123",
                orderVignetteType = OrderVignetteType.YEAR_COUNTY,
                counties = listOf(
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
                ),
                trxFee = 110f,
                totalCost = 21900f,
            ),
        ),
        OrderConfirmationUiState.Error,
    )
}
package com.szaniszlo.yettelhomeassignment.ui.countyvignettes

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.szaniszlo.yettelhomeassignment.R
import com.szaniszlo.yettelhomeassignment.domain.model.county.County
import com.szaniszlo.yettelhomeassignment.ui.components.FullScreenLoader
import com.szaniszlo.yettelhomeassignment.ui.components.TotalCostComponent
import com.szaniszlo.yettelhomeassignment.ui.components.YettelAppBar
import com.szaniszlo.yettelhomeassignment.ui.core.theme.Typography
import com.szaniszlo.yettelhomeassignment.ui.core.theme.yettelBlue
import com.szaniszlo.yettelhomeassignment.ui.core.theme.yettelGreen
import kotlinx.coroutines.flow.collectLatest
import java.text.NumberFormat
import java.util.Locale

@Composable
fun CountyVignettePurchaseScreen(
    viewModel: CountyVignettePurchaseViewModel = hiltViewModel(),
    onBack: () -> Unit,
    onContinue: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.navigateToConfirmOrder.collectLatest {
            onContinue()
        }
    }

    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.noCountySelectedEvent.collectLatest {
            snackbarHostState.showSnackbar(
                message = context.getString(R.string.no_county_selected),
                withDismissAction = true,
                duration = SnackbarDuration.Short,
            )
        }
    }

    CountyVignettePurchaseScreenContent(
        uiState = uiState,
        snackbarHostState = snackbarHostState,
        onBackClick = onBack,
        onNextClick = viewModel::next,
        onCountyCheckChanged = viewModel::onCountyCheckChanged,
    )
}

@Composable
private fun CountyVignettePurchaseScreenContent(
    uiState: CountyVignettePurchaseUiState,
    onBackClick: () -> Unit,
    onNextClick: () -> Unit,
    onCountyCheckChanged: (String) -> Unit,
    snackbarHostState: SnackbarHostState,
) {
    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            YettelAppBar(
                onBackClicked = onBackClick,
            )
        }
    ) {
        AnimatedContent(
            targetState = uiState,
            contentKey = { uiState !is CountyVignettePurchaseUiState.Loading },
            modifier = Modifier.padding(it),
        ) { uiState ->
            when (uiState) {
                is CountyVignettePurchaseUiState.Default -> DefaultContent(
                    uiState = uiState,
                    onCountyCheckChanged = onCountyCheckChanged,
                    onContinueClick = onNextClick
                )

                CountyVignettePurchaseUiState.Loading -> FullScreenLoader()
            }
        }
    }
}

@Composable
private fun DefaultContent(
    uiState: CountyVignettePurchaseUiState.Default,
    onCountyCheckChanged: (String) -> Unit,
    onContinueClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .padding(all = 16.dp)
    ) {
        YearlyCountyVignettesLabel()

        Text(text = "TODO: CountryView")

        Spacer(modifier = Modifier.height(16.dp))

        // could be a lazy list, but for not it's fine
        uiState.counties.forEach { county ->
            CountyItem(
                selectableCounty = county,
                onCountyCheckChanged = onCountyCheckChanged
            )
        }

        HorizontalDivider()

        Spacer(modifier = Modifier.height(16.dp))

        AnimatedVisibility(
            visible = !uiState.isDirectConnectionPresent
        ) {
            NoDirectConnectionWarning(
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }

        TotalCostComponent(
            totalCost = uiState.totalCost,
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = onContinueClick,
            colors = ButtonDefaults.buttonColors().copy(
                containerColor = yettelBlue
            ),
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(text = stringResource(id = R.string.proceed))
        }
    }
}

@Composable
private fun YearlyCountyVignettesLabel() {
    Text(
        text = stringResource(id = R.string.yearly_country_vignettes),
        style = Typography.headlineSmall,
    )
}

@Composable
private fun NoDirectConnectionWarning(
    modifier: Modifier = Modifier
) {
    Text(
        text = stringResource(id = R.string.no_direct_connection_warning),
        style = Typography.headlineSmall,
        fontWeight = FontWeight.Bold,
        modifier = modifier
            .background(color = yettelGreen,shape = RoundedCornerShape(16.dp))
            .padding(all = 8.dp)
    )
}

@Composable
private fun CountyItem(
    selectableCounty: SelectableCounty,
    onCountyCheckChanged: (String) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth(),
    ) {
        Checkbox(
            checked = selectableCounty.isSelected,
            onCheckedChange = { onCountyCheckChanged(selectableCounty.id) },
        )

        Text(
            text = selectableCounty.county.name,
            modifier = Modifier.weight(1f)
        )

        val format = NumberFormat.getNumberInstance(Locale.getDefault())
        Text(
            text = stringResource(id = R.string.amount,format.format(selectableCounty.county.cost)),
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
@Preview(locale = "hu-HU")
private fun CountyVignettePurchaseScreenContentPreview(
    @PreviewParameter(CountyVignettePurchaseUiStatePreviewProvider::class) uiState: CountyVignettePurchaseUiState
) {
    CountyVignettePurchaseScreenContent(
        uiState = uiState,
        onBackClick = {},
        onNextClick = {},
        onCountyCheckChanged = {},
        snackbarHostState = SnackbarHostState(),
    )
}

private class CountyVignettePurchaseUiStatePreviewProvider :
    PreviewParameterProvider<CountyVignettePurchaseUiState> {

    override val values = sequenceOf(
        CountyVignettePurchaseUiState.Default(
            counties = listOf(
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
                    isSelected = true,
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
            ),
            totalCost = 5570f,
            isDirectConnectionPresent = false,
        )
    )
}
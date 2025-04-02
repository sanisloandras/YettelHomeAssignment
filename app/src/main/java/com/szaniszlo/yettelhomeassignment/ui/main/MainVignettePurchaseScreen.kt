package com.szaniszlo.yettelhomeassignment.ui.main

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.szaniszlo.yettelhomeassignment.R
import com.szaniszlo.yettelhomeassignment.domain.model.VignetteCategory
import com.szaniszlo.yettelhomeassignment.domain.model.VignetteType
import com.szaniszlo.yettelhomeassignment.ui.components.ErrorLayout
import com.szaniszlo.yettelhomeassignment.ui.components.FullScreenLoader
import com.szaniszlo.yettelhomeassignment.ui.components.YettelAppBar
import com.szaniszlo.yettelhomeassignment.ui.core.theme.Typography
import com.szaniszlo.yettelhomeassignment.ui.core.theme.YettelAppTheme
import com.szaniszlo.yettelhomeassignment.ui.core.theme.lightGrey
import com.szaniszlo.yettelhomeassignment.ui.core.theme.yettelBlue
import com.szaniszlo.yettelhomeassignment.ui.util.formatPlate
import java.text.NumberFormat
import java.util.Locale

@Composable
fun MainVignettePurchaseScreen(
    viewModel: MainVignettePurchaseViewModel = hiltViewModel(),
    onBackClick: () -> Unit,
    onPurchaseClicked: (String) -> Unit,
    onCountyVignettesClicked: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.navigateToPurchaseConfirmationEvent.collect { vignetteSelection ->
            onPurchaseClicked(vignetteSelection)
        }
    }

    MainVignettePurchaseScreenContent(
        uiState = uiState,
        onBackClicked = onBackClick,
        onRetryClicked = viewModel::fetchData,
        onPurchaseClicked = viewModel::purchase,
        onCountryVignetteClicked = viewModel::onCountryVignetteClicked,
        onCountyVignettesClicked = onCountyVignettesClicked,
    )
}

@Composable
private fun MainVignettePurchaseScreenContent(
    uiState: MainVignettePurchaseUiState,
    onBackClicked: () -> Unit,
    onRetryClicked: () -> Unit,
    onPurchaseClicked: () -> Unit,
    onCountryVignetteClicked: (CountryVignette) -> Unit,
    onCountyVignettesClicked: () -> Unit,
) {
    Scaffold(
        topBar = {
            YettelAppBar(onBackClicked = onBackClicked)
        },
        containerColor = lightGrey
    ) { contentPadding ->
        AnimatedContent(
            targetState = uiState,
            contentKey = { it !is MainVignettePurchaseUiState.Loading },
            modifier = Modifier.padding(contentPadding)
        ) { uiState ->
            when (uiState) {
                is MainVignettePurchaseUiState.Loading -> FullScreenLoader()

                is MainVignettePurchaseUiState.Default -> {
                    DefaultContent(
                        uiState = uiState,
                        onPurchaseClicked = onPurchaseClicked,
                        onCountryVignetteClicked = onCountryVignetteClicked,
                        onCountyVignettesClicked = onCountyVignettesClicked,
                    )
                }

                MainVignettePurchaseUiState.Error -> ErrorLayout(
                    onRetryClicked = onRetryClicked,
                )
            }
        }

    }
}

@Composable
private fun DefaultContent(
    uiState: MainVignettePurchaseUiState.Default,
    onPurchaseClicked: () -> Unit,
    onCountryVignetteClicked: (CountryVignette) -> Unit,
    onCountyVignettesClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .padding(all = 16.dp)
    ) {
        CarInfoCard(
            plate = uiState.plate,
            name = uiState.name,
            vignetteCategory = uiState.vignetteCategory,
        )

        CountryVignettesCard(
            countryVignettes = uiState.countryVignettes,
            onCountryVignetteClicked = onCountryVignetteClicked,
            onPurchaseClicked = onPurchaseClicked
        )

        NavigationBox(
            title = stringResource(id = R.string.yearly_country_vignettes),
            onClick = onCountyVignettesClicked,
        )
    }
}

@Composable
private fun CountryVignettesCard(
    countryVignettes: List<CountryVignette>,
    onCountryVignetteClicked: (CountryVignette) -> Unit,
    onPurchaseClicked: () -> Unit,
) {
    Card(
        colors = CardDefaults.cardColors().copy(
            containerColor = Color.White,
        ),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.padding(all = 16.dp)
        ) {
            Text(
                text = stringResource(id = R.string.country_vignettes),
                style = Typography.headlineSmall,
                fontWeight = FontWeight.Bold,
            )

            countryVignettes.forEach { countryVignette ->
                VignettesRadioGroup(
                    countryVignette = countryVignette,
                    onClick = {
                        onCountryVignetteClicked(countryVignette)
                    },
                )
            }

            Button(
                onClick = onPurchaseClicked,
                colors = ButtonDefaults.buttonColors().copy(
                    containerColor = yettelBlue
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = stringResource(id = R.string.purchase))
            }
        }
    }
}

@Composable
private fun CarInfoCard(
    plate: String,
    name: String,
    vignetteCategory: VignetteCategory,
) {
    Card(
        colors = CardDefaults.cardColors().copy(
            containerColor = Color.White,
        ),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(all = 16.dp)
        ) {
            Icon(
                imageVector = when (vignetteCategory) {
                    VignetteCategory.D1 -> Icons.Filled.DirectionsCar
                },
                contentDescription = null,
            )

            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = plate.formatPlate(),
                    style = Typography.bodyLarge,
                )
                Text(text = name)
            }
        }
    }
}

@Composable
private fun VignettesRadioGroup(
    countryVignette: CountryVignette,
    onClick: () -> Unit,
) {
    OutlinedCard(
        modifier = Modifier
            .fillMaxWidth(),
        colors = CardDefaults.outlinedCardColors().copy(
            containerColor = Color.White,
        ),
        border = BorderStroke(
            width = 2.dp,
            color = if (countryVignette.isSelected) Color.Black else lightGrey
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .padding(end = 16.dp)
        ) {
            RadioButton(
                selected = countryVignette.isSelected,
                onClick = onClick,
            )

            Text(
                text = stringResource(
                    id = when (countryVignette.vignetteType) {
                        VignetteType.DAY -> R.string.vignette_category_with_type_day
                        VignetteType.MONTH -> R.string.vignette_category_with_type_month
                        VignetteType.WEEK -> R.string.vignette_category_with_type_week
                        VignetteType.YEAR -> R.string.vignette_category_with_type_year
                        else -> throw IllegalArgumentException("Not supported $this")
                    },
                    countryVignette.vignetteCategory.category,
                ),
                modifier = Modifier.weight(1f)
            )

            val format = NumberFormat.getNumberInstance(Locale.getDefault())
            Text(
                text = stringResource(id = R.string.amount,format.format(countryVignette.cost)),
                fontWeight = FontWeight.Bold,
            )
        }
    }
}

@Composable
fun NavigationBox(
    title: String,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                modifier = Modifier.weight(1f),
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
            )
            Icon(
                imageVector = Icons.Filled.ChevronRight,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Composable
@Preview(locale = "hu-HU")
private fun MainVignettePurchaseScreenContentPreview(
    @PreviewParameter(MainVignettePurchaseUiStatePreviewProvider::class) uiState: MainVignettePurchaseUiState,
) {
    YettelAppTheme {
        MainVignettePurchaseScreenContent(
            uiState = uiState,
            onBackClicked = {},
            onPurchaseClicked = {},
            onCountryVignetteClicked = {},
            onCountyVignettesClicked = {},
            onRetryClicked = {},
        )
    }
}

private class MainVignettePurchaseUiStatePreviewProvider :
    PreviewParameterProvider<MainVignettePurchaseUiState> {

    override val values = sequenceOf(
        MainVignettePurchaseUiState.Default(
            plate = "abc-123",
            name = "Michael Scott",
            vignetteCategory = VignetteCategory.D1,
            countryVignettes = listOf(
                CountryVignette(
                    isSelected = false,
                    vignetteType = VignetteType.WEEK,
                    vignetteCategory = VignetteCategory.D1,
                    cost = 6400f,
                ),
                CountryVignette(
                    isSelected = true,
                    vignetteType = VignetteType.MONTH,
                    vignetteCategory = VignetteCategory.D1,
                    cost = 10360f,
                ),
                CountryVignette(
                    isSelected = false,
                    vignetteType = VignetteType.DAY,
                    vignetteCategory = VignetteCategory.D1,
                    cost = 5150f,
                ),
            )
        ),
        MainVignettePurchaseUiState.Error,
    )
}
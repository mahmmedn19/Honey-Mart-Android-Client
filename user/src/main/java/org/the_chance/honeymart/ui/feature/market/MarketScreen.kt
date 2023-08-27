package org.the_chance.honeymart.ui.feature.market


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import org.the_chance.honeymart.ui.NavigationHandler
import org.the_chance.honeymart.ui.composables.ConnectionErrorPlaceholder
import org.the_chance.honeymart.ui.composables.ContentVisibility
import org.the_chance.honeymart.ui.feature.category.navigateToCategoryScreen
import org.the_chance.honeymart.ui.feature.market.compoaseable.MarketItem
import org.the_chance.honymart.ui.composables.AppBarScaffold
import org.the_chance.honymart.ui.composables.Loading
import org.the_chance.honymart.ui.theme.dimens


@Composable
fun MarketScreen(
    viewModel: MarketViewModel = hiltViewModel(),
) {
    val state = viewModel.state.collectAsState().value

    NavigationHandler(
        effects = viewModel.effect,
        handleEffect = {effect, navController ->
            when (effect) {
                is MarketUiEffect.ClickMarketEffect -> navController.navigateToCategoryScreen(effect.marketId)
            }
    })

    MarketContent(state = state, listener = viewModel)
}

@Composable
fun MarketContent(
    state: MarketsUiState,
    listener: MarketInteractionListener,
) {
    AppBarScaffold {
        ContentVisibility(state = state.showMarket()) {
            LazyColumn(
                modifier = Modifier.background(color = MaterialTheme.colorScheme.secondary),
                state = rememberLazyListState(),
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space16),
                contentPadding = PaddingValues(
                    horizontal = MaterialTheme.dimens.space16,
                    vertical = MaterialTheme.dimens.space8
                ),
            ) {
                items(state.markets.size) { position ->
                    MarketItem(onClickItem = listener::onClickMarket, state.markets[position])
                }
            }
        }
        ConnectionErrorPlaceholder(
            state = state.errorPlaceHolder(),
            onClickTryAgain = listener::getChosenMarkets
        )
        Loading(state.isLoading)
    }
}

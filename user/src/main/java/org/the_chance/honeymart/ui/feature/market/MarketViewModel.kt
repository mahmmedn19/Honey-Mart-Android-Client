package org.the_chance.honeymart.ui.feature.market

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.update
import org.the_chance.honeymart.domain.usecase.GetAllMarketsUseCase
import org.the_chance.honeymart.domain.util.ErrorHandler
import org.the_chance.honeymart.ui.base.BaseViewModel
import org.the_chance.honeymart.ui.feature.uistate.MarketUiState
import org.the_chance.honeymart.ui.feature.uistate.MarketsUiState
import org.the_chance.honeymart.ui.feature.uistate.toMarketUiState
import javax.inject.Inject

@HiltViewModel
class MarketViewModel @Inject constructor(
    private val getAllMarket: GetAllMarketsUseCase,
) : BaseViewModel<MarketsUiState, Long>(MarketsUiState()) {

    override val TAG: String = this::class.java.simpleName
    private var job: Job? = null

    init {
        getAllMarkets()
    }

    fun getAllMarkets() {
        _state.update { it.copy(isLoading = true, isError = false) }
        tryToExecute(
            { getAllMarket().map { it.toMarketUiState() } },
            ::onGetMarketSuccess,
            ::onGetMarketError
        )
    }


    private fun onGetMarketError(error: ErrorHandler) {
        _state.update { it.copy(isLoading = false) }
        if (error is ErrorHandler.NoConnection) {
            _state.update { it.copy(isLoading = false, isError = true) }
        }
    }

    private fun onGetMarketSuccess(markets: List<MarketUiState>) {
        _state.update {
            it.copy(
                isLoading = false,
                markets = markets
            )
        }
    }


}
package org.the_chance.honeymart.ui.feature.profile

import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import org.the_chance.honeymart.domain.model.OrderEntity
import org.the_chance.honeymart.domain.model.ProfileUserEntity
import org.the_chance.honeymart.domain.usecase.GetProfileUserUseCase
import org.the_chance.honeymart.domain.util.ErrorHandler
import org.the_chance.honeymart.ui.base.BaseViewModel
import org.the_chance.honeymart.ui.feature.orders.OrderStates
import org.the_chance.honeymart.ui.feature.orders.OrderUiEffect
import org.the_chance.honeymart.ui.feature.orders.toOrderUiState
import org.the_chance.honeymart.ui.feature.product_details.ProductDetailsInteraction
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getProfileUseCase : GetProfileUserUseCase,
    savedStateHandle: SavedStateHandle,
) : BaseViewModel<ProfileUiState, ProfileUiEffect>(ProfileUiState()),
    ProfileInteractionsListener {

    override val TAG: String = this::class.simpleName.toString()


    init {
        getData()
    }

    override fun getData(){
        _state.update {
            it.copy(isLoading = true, isError = false,)
        }
        tryToExecute(
            { getProfileUseCase()},
            ::onGetProfileSuccess,
            ::onGetProfileError
        )
    }

    private fun onGetProfileSuccess(user: ProfileUserEntity) {
        _state.update { it.copy(isLoading = false, accountInfo = user.toProfileUiState()) }
    }

    private fun onGetProfileError(error: ErrorHandler) {
        _state.update { it.copy(isLoading = false, error = error) }
        if (error is ErrorHandler.NoConnection) {
            _state.update { it.copy(isError = true) }
        }
    }

    override fun onClickMyOrder() {
        effectActionExecutor(_effect, ProfileUiEffect.ClickMyOrderEffect)
    }

    override fun onClickCoupons() {
        effectActionExecutor(_effect, ProfileUiEffect.ClickCouponsEffect)
    }

    override fun onClickNotification() {
        effectActionExecutor(_effect, ProfileUiEffect.ClickNotificationEffect)
    }

    override fun onClickTheme() {
        TODO("Not yet implemented")
    }

    override fun onClickLogout() {
        TODO("Not yet implemented")
    }

    override fun showSnackBar(massage: String) {
        TODO("Not yet implemented")
    }

    override fun showDialog(massage: String) {
        TODO("Not yet implemented")
    }

    override fun updateImage() {
        TODO("Not yet implemented")
    }
}
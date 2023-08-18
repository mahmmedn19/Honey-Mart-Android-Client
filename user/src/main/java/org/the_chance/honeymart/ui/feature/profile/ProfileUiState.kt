package org.the_chance.honeymart.ui.feature.profile

import com.airbnb.lottie.L
import org.the_chance.honeymart.domain.model.OrderEntity
import org.the_chance.honeymart.domain.model.ProfileUserEntity
import org.the_chance.honeymart.domain.util.ErrorHandler
import org.the_chance.honeymart.ui.feature.orders.OrderUiState

data class ProfileUiState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val error: ErrorHandler? = null,
    val snackBar: SnackBarState = SnackBarState(),
    val isConnectionError: Boolean = false,
    val accountInfo: AccountState = AccountState(),
){
    data class AccountState(
        val userId : Long = 0,
        val fullName : String = "",
        val email : String = "",
        val profileImage : String = "",

        )
}

data class SnackBarState(
    val isShow: Boolean = false,
    val massage: String = "",
)

fun ProfileUiState.contentScreen()= !this.isLoading && !this.isConnectionError


fun ProfileUserEntity.toProfileUiState(): ProfileUiState.AccountState {
    return ProfileUiState.AccountState(
    userId = userId,
    fullName = fullName,
    email = email,
    profileImage = profileImage,
    )
}
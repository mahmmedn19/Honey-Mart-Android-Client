package org.the_chance.honeymart.ui.feature.product

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import org.the_chance.design_system.R
import org.the_chance.honeymart.ui.LocalNavigationProvider
import org.the_chance.honeymart.ui.feature.authentication.navigateToAuth
import org.the_chance.honeymart.ui.feature.product_details.navigateToProductDetailsScreen
import org.the_chance.honymart.ui.composables.ConnectionErrorPlaceholder
import org.the_chance.honymart.ui.composables.EmptyProductPlaceholder
import org.the_chance.honymart.ui.composables.Loading
import org.the_chance.honymart.ui.composables.ProductCard
import org.the_chance.honymart.ui.composables.SideBarItem
import org.the_chance.honymart.ui.theme.dimens


@Composable
fun ProductsScreen(
    viewModel: ProductViewModel = hiltViewModel(),
) {
    val navController = LocalNavigationProvider.current
    val state by viewModel.state.collectAsState()

    ProductsContent(
        state = state,
        viewModel = viewModel,
        productInteractionListener = viewModel,
        navigateToProductScreen = { productId ->
            navController.navigateToProductDetailsScreen(productId)
        },
        navigateToAuth = {
            navController.navigateToAuth()
        }
    )
}

@OptIn(ExperimentalAnimationApi::class)
@SuppressLint("SuspiciousIndentation")
@Composable
private fun ProductsContent(
    state: ProductsUiState,
    viewModel: ProductViewModel,
    productInteractionListener: ProductInteractionListener,
    navigateToProductScreen: (productId: Long) -> Unit,
    navigateToAuth: () -> Unit,
) {
    Loading(state.isLoadingCategory)
    ConnectionErrorPlaceholder(state.isError, {})

    AnimatedVisibility(
        visible = !state.isLoadingCategory && !state.isError,
        enter = fadeIn(animationSpec = tween(durationMillis = 500)) + slideInHorizontally(),
        exit = fadeOut(animationSpec = tween(durationMillis = 500)) + slideOutVertically()
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
                    .padding(
                        start = MaterialTheme.dimens.space16,
                        end = MaterialTheme.dimens.space16
                    ),
                horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space12)
            ) {
                LazyColumn(
                    contentPadding = PaddingValues(
                        top = MaterialTheme.dimens.space24,
                        end = MaterialTheme.dimens.space12
                    ),
                    verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space16)
                ) {
                    items(state.categories.size) { index ->
                        val category = state.categories[index]
                        SideBarItem(
                            icon = R.drawable.ic_bed,
                            categoryName = category.categoryName,
                            isSelected = category.isCategorySelected,
                            onClick = {
                                productInteractionListener.onClickCategory(category.categoryId)
                            }
                        )
                    }
                }
                EmptyProductPlaceholder(state.isEmptyProducts)
                Loading(state.isLoadingProduct)
                AnimatedVisibility(
                    visible = !state.isLoadingProduct,
                    enter = fadeIn(animationSpec = tween(durationMillis = 2000)) + slideInVertically(),
                    exit = fadeOut(animationSpec = tween(durationMillis = 500)) + slideOutHorizontally()
                ) {
                    LazyColumn(
                        contentPadding = PaddingValues(top = MaterialTheme.dimens.space24),
                        verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space8)
                    ) {
                        items(state.products.size) { index ->
                            val product = state.products[index]
                            ProductCard(
                                modifier = Modifier.fillMaxWidth(),
                                imageUrl = product.productImages.firstOrNull() ?: "",
                                productName = product.productName,
                                productPrice = product.productPrice.toString(),
                                secondaryText = product.productDescription,
                                isFavoriteIconClicked = product.isFavorite,
                                onClickCard = {
                                    productInteractionListener.onClickProduct(product.productId)
                                },
                                onClickFavorite = {
                                    productInteractionListener.onClickFavIcon(product.productId)
                                }
                            )
                        }
                    }
                }
            }
        }
    }

    LaunchedEffect(key1 = state.navigateToProductDetailsState.isNavigate) {
        if (state.navigateToProductDetailsState.isNavigate) {
            navigateToProductScreen(state.navigateToProductDetailsState.id)
            viewModel.resetNavigation()
        }
    }
    LaunchedEffect(key1 = state.navigateToAuthGraph) {
        if (state.navigateToAuthGraph.isNavigate) {
            navigateToAuth()
            viewModel.resetNavigation()
        }
    }
}




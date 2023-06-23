package org.the_chance.honeymart.ui.feature.product_details

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import org.the_chance.honeymart.ui.base.BaseFragment
import org.the_chance.honeymart.util.AuthData
import org.the_chance.honeymart.util.collect
import org.the_chance.honeymart.util.showSnackBar
import org.the_chance.user.R
import org.the_chance.user.databinding.FragmentProductDetailsBinding

@AndroidEntryPoint
class ProductDetailsFragment : BaseFragment<FragmentProductDetailsBinding>() {
    override val TAG: String = this::class.java.simpleName
    override val layoutIdFragment: Int = R.layout.fragment_product_details
    override val viewModel: ProductDetailsViewModel by viewModels()

    override fun setup() {
        hideAppBarAndBottomNavigation()
        initiateAdapter()
        navigateBack()
        collectEffect()
    }

    private fun initiateAdapter() {
        val adapter = ProductDetailsAdapter(viewModel)
        binding.recyclerProductImages.adapter = adapter
    }

    private fun navigateBack() {
        binding.cardBackArrow.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun collectEffect() {
        collect(viewModel.effect) { effect ->
            effect.getContentIfHandled()?.let { observeViewModelEvents(it) }
        }
    }

    private fun observeViewModelEvents(effect: ProductDetailsUiEffect) {
        when (effect) {
            is ProductDetailsUiEffect.AddToCartSuccess -> {
                showSnackBar(getString(org.the_chance.design_system.R.string.addedToCartSuccessMessage))
            }

            is ProductDetailsUiEffect.AddToCartError -> {
                showSnackBar(getString(org.the_chance.design_system.R.string.addedToCartFailedMessage))
            }

            is ProductDetailsUiEffect.UnAuthorizedUserEffect -> navigateToAuthenticate(
                effect.authData
            )

            is ProductDetailsUiEffect.AddProductToWishListEffectError -> {
                showSnackBar(effect.error.toString())
            }

            is ProductDetailsUiEffect.AddProductToWishListEffectSuccess -> {
                showSnackBar(getString(org.the_chance.design_system.R.string.addedToWishlistSuccessMessage))
            }

            is ProductDetailsUiEffect.AddProductToWishListEffectError -> {
                showSnackBar(getString(org.the_chance.design_system.R.string.addedToWishlistFailedMessage))
            }

            is ProductDetailsUiEffect.RemoveProductFromWishListEffectSuccess -> {
                showSnackBar(getString(org.the_chance.design_system.R.string.removedFromWishListSuccessMessage))
            }

            is ProductDetailsUiEffect.RemoveProductFromWishListEffectError -> {
                showSnackBar(getString(org.the_chance.design_system.R.string.removedFromWishListFailedMessage))
            }
        }
    }

    private fun navigateToAuthenticate(authData: AuthData.ProductDetails) {
        val action = ProductDetailsFragmentDirections.actionProductDetailsToUserNavGraph(
            authData
        )
        findNavController().navigate(action)
    }
}
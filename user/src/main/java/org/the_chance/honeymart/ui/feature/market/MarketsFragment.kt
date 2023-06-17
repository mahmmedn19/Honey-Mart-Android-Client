package org.the_chance.honeymart.ui.feature.market

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.imageview.ShapeableImageView
import dagger.hilt.android.AndroidEntryPoint
import org.the_chance.honeymart.ui.base.BaseFragment
import org.the_chance.honeymart.util.addOnScrollListenerWithAppbarColor
import org.the_chance.honeymart.util.addOnScrollListenerWithImageVisibility
import org.the_chance.honeymart.util.collect
import org.the_chance.user.R
import org.the_chance.user.databinding.FragmentMarketsBinding

@AndroidEntryPoint
class MarketsFragment : BaseFragment<FragmentMarketsBinding>() {
    override val TAG: String = this::class.java.simpleName
    override val layoutIdFragment = R.layout.fragment_markets
    override val viewModel: MarketViewModel by viewModels()
    private lateinit var appBarLayout: AppBarLayout
    private lateinit var imageLogoDefault: ShapeableImageView
    private lateinit var imageLogoScrolled: ShapeableImageView
    override fun setup() {

        val adapter = MarketAdapter(viewModel)
        binding.recyclerMarkets.adapter = adapter

        appBarLayout = requireActivity().findViewById(R.id.appBarLayout)
        imageLogoDefault = requireActivity().findViewById(R.id.image_logo)
        imageLogoScrolled = requireActivity().findViewById(R.id.image_logo_scroll)

        binding.recyclerMarkets
            .addOnScrollListenerWithAppbarColor(requireContext(), this, appBarLayout)
        binding.recyclerMarkets
            .addOnScrollListenerWithImageVisibility(imageLogoDefault, imageLogoScrolled)

        collectAction()
    }


    private fun collectAction() {
        collect(viewModel.effect) { effect ->
            effect.getContentIfHandled()?.let { navigateToCategory(it) }
        }
    }

    private fun navigateToCategory(marketId: Long) {
        val action = MarketsFragmentDirections.actionMarketsFragmentToCategoriesFragment(marketId)
        findNavController().navigate(action)
    }
}
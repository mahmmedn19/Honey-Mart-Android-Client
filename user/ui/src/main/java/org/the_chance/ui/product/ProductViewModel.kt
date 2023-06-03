package org.the_chance.ui.product

import dagger.hilt.android.lifecycle.HiltViewModel
import org.the_chance.ui.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor() : BaseViewModel() {
    override val TAG: String = this::class.java.simpleName
}
package org.the_chance.honeymart.util

import android.view.View
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.textview.MaterialTextView
import org.the_chance.design_system.R
import org.the_chance.honeymart.ui.feature.uistate.ProductUiState
import org.the_chance.ui.BaseAdapter

@BindingAdapter(value = ["app:items"])
fun <T> setRecyclerItems(view: RecyclerView, items: List<T>?) {
    (view.adapter as BaseAdapter<T>?)?.setItems(items ?: emptyList())
}

@BindingAdapter("app:showIfTrue")
fun showIfTrue(view: View, condition: Boolean) {
    view.isVisible = condition
}

@BindingAdapter(value = ["app:showIfNoProducts", "app:hideIfLoading"])
fun productPlaceholder(view: View, productList: List<ProductUiState>, condition: Boolean) {
    view.isVisible = condition == false && productList.isEmpty()
}

@BindingAdapter("app:changeIfSelected")
fun changeIfSelected(view: View, isSelected: Boolean) {
    val context = view.context

    when (view) {
        is CardView -> {
            val colorRes = if (isSelected) R.color.primary_100 else R.color.white_100
            val color = ContextCompat.getColor(context, colorRes)
            view.setCardBackgroundColor(color)
        }

        is ShapeableImageView -> {
            val drawableRes =
                if (isSelected) R.drawable.icon_category_white else R.drawable.icon_category
            val drawable = ContextCompat.getDrawable(context, drawableRes)
            view.setImageDrawable(drawable)
        }

        is MaterialTextView -> {
            val colorRes = if (isSelected) R.color.primary_100 else R.color.black_60
            val color = ContextCompat.getColor(context, colorRes)
            view.setTextColor(color)
        }
    }
}

@BindingAdapter("app:changeColorIfSelected")
fun changeColorIfSelected(view: View, isFavorite: Boolean) {
    val context = view.context
    when (view) {
        is CardView -> {
            val colorRes = if (isFavorite) R.color.white else R.color.primary_100
            val color = ContextCompat.getColor(context, colorRes)
            view.setCardBackgroundColor(color)
        }

        is ShapeableImageView -> {
            val drawableRes =
                if (isFavorite) R.drawable.icon_favorite_selected else R.drawable.icon_favorite_unselected
            val drawable = ContextCompat.getDrawable(context, drawableRes)
            view.setImageDrawable(drawable)
        }
    }
}

@BindingAdapter("scrollToPosition")
fun scrollToPosition(recyclerView: RecyclerView, position: Int) {
    recyclerView.scrollToPosition(position)
}

@BindingAdapter(value = ["app:imageUrl"])
fun setImageFromUrl(view: ImageView, url: String?) {
    url.let {
        Glide
            .with(view)
            .load(url)
            .placeholder(R.drawable.placeholder_wish_list)
            .centerCrop()
            .into(view)
    }
}
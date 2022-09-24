package dev.mnglarora.interview.binding

import android.net.Uri
import android.widget.ImageView
import androidx.appcompat.widget.SearchView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

object ViewBinding {

    @JvmStatic
    @BindingAdapter("loadImage")
    fun bindLoadImage(view: ImageView, path: String?) {
        Glide.with(view.context)
            .load(Uri.parse(path))
            .into(view)
    }

    @JvmStatic
    @BindingAdapter("queryTextListener")
    fun setOnQueryTextListener(
        searchView: SearchView,
        listener: SearchView.OnQueryTextListener?
    ) {
        searchView.setOnQueryTextListener(listener)
    }

}
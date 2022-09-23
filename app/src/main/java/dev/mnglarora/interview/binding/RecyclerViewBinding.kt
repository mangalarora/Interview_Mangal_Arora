package dev.mnglarora.interview.binding

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import dev.mnglarora.interview.ui.adapter.GithubRepoAdapter

object RecyclerViewBinding {

    @JvmStatic
    @BindingAdapter("adapter")
    fun bindAdapter(view:RecyclerView, adapter: GithubRepoAdapter){
        view.adapter = adapter
    }

}
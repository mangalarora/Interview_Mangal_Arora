package dev.mnglarora.interview.ui.adapter

import android.text.Editable
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import dev.mnglarora.interview.R
import dev.mnglarora.interview.databinding.ListItemBinding
import dev.mnglarora.interview.model.GhRepo
import kotlinx.coroutines.flow.StateFlow

class GithubRepoAdapter : ListAdapter<GhRepo, GithubRepoAdapter.ListViewHolder>(DiffUtilsImpl()) {


    private lateinit var kFunction: (Int) -> Unit
    private lateinit var selectedListFlow: StateFlow<ArrayList<Int>>
    private var mutableRepoListSF: List<GhRepo> = listOf()

    fun search(str: Editable?) {
        str?.toString()?.let { finalString ->
            search(finalString)
        }
    }

    private fun search(str: String) {
        submitList(mutableRepoListSF.filter {
            it.name?.contains(str, true) ?: false ||
                    it.owner?.login?.contains(str, true) ?: false
        }.sortedBy { it.rank })
    }

    fun updateProperties(selectedListFlow: StateFlow<ArrayList<Int>>, kFunction: (Int) -> Unit) {
        this.selectedListFlow = selectedListFlow
        this.kFunction = kFunction
    }

    fun updateData(sortedList: List<GhRepo>, searchText: String) {
        mutableRepoListSF = sortedList
        if (!TextUtils.isEmpty(searchText))
            search(searchText)
        else
            submitList(sortedList)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        return ListViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.list_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind()
    }

    class DiffUtilsImpl : DiffUtil.ItemCallback<GhRepo>() {
        override fun areItemsTheSame(oldItem: GhRepo, newItem: GhRepo): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: GhRepo, newItem: GhRepo): Boolean =
            oldItem == newItem
    }

    inner class ListViewHolder(private val binding: ListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind() {
            val obj = getItem(absoluteAdapterPosition)
            binding.apply {
                model = obj
                populateView(obj.id)
            }
            binding.item.setOnClickListener {
                Log.e("Item", "Clicked $position ${obj.name}")
                kFunction(obj.id)
                notifyItemChanged(absoluteAdapterPosition)
            }
        }

        private fun ListItemBinding.populateView(id: Int) {
            if (selectedListFlow.value.contains(id)) {
                profileImage.borderWidth = 12.dp.value.toInt()
                profileImage.borderColor =
                    ContextCompat.getColor(itemView.context, android.R.color.holo_blue_dark)
            } else {
                profileImage.borderWidth = 2.dp.value.toInt()
                profileImage.borderColor =
                    ContextCompat.getColor(
                        itemView.context,
                        android.R.color.darker_gray
                    )
            }
        }

    }


}
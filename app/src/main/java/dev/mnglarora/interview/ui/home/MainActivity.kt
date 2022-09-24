package dev.mnglarora.interview.ui.home


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import dev.mnglarora.interview.R
import dev.mnglarora.interview.databinding.ActivityMainBinding
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.getViewModel


class MainActivity : ComponentActivity() {

    private val binding: ActivityMainBinding by lazy {
        DataBindingUtil.setContentView(this, R.layout.activity_main)
    }
    private val viewModel: MainViewModel by lazy { getViewModel() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        viewModel.mAdapter.updateProperties(viewModel.selectedListFlow, viewModel::updateSelectedList)

        binding.apply {
            adapter = viewModel.mAdapter
        }

        setupRepoFlowCollector()

    }

    private fun setupRepoFlowCollector() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.getLocalRepos().collect { ghRepos ->
                    val sortedList = ghRepos.sortedBy { it.rank }
                    val searchText = binding.search.text.toString().trim()
                    viewModel.mAdapter.updateData(sortedList, searchText)
                }
            }
        }
    }

    override fun onSaveInstanceState(state: Bundle) {
        super.onSaveInstanceState(state)
        viewModel.recyclerViewState = binding.recyclerView.layoutManager?.onSaveInstanceState()
    }

    override fun onResume() {
        super.onResume()
        binding.recyclerView.layoutManager?.onRestoreInstanceState(viewModel.recyclerViewState)
    }

}
package dev.mnglarora.interview.ui.home

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import dev.mnglarora.interview.R
import dev.mnglarora.interview.databinding.ActivityMainBinding
import dev.mnglarora.interview.ui.adapter.GithubRepoAdapter
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.getViewModel

class MainActivity : ComponentActivity() {

    private val binding: ActivityMainBinding by lazy {
        DataBindingUtil.setContentView(this, R.layout.activity_main)
    }
    private val viewModel: MainViewModel by lazy { getViewModel() }
    private val mAdapter: GithubRepoAdapter = GithubRepoAdapter()
    private var mListState: Int? = null

    private lateinit var layoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        mAdapter.updateProperties(viewModel.selectedListFlow, viewModel::updateSelectedList)

        setupRecyclerView()

        binding.apply {
            vm = viewModel
            adapter = mAdapter
        }

        //setupSearchBar()

        setupRepoFlowCollector()

    }


/*    private fun setupSearchBar() {
        binding.search.addTextChangedListener { str ->
            mAdapter.search(str)
        }
    }*/


    private fun setupRecyclerView() {
        layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.setHasFixedSize(true)
    }


    private fun setupRepoFlowCollector() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.getLocalRepos().collect { ghRepos ->
                    val sortedList = ghRepos.sortedBy { it.rank }
                    val searchText = binding.search.text.toString().trim()
                    mAdapter.updateData(sortedList, searchText)
                }
            }
        }
    }

/*
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        binding.recyclerView.layoutManager?.onSaveInstanceState()
            ?.let { viewModel.updateRecyclerState(it) }
    } */

    override fun onSaveInstanceState(state: Bundle) {
        super.onSaveInstanceState(state)

        // Save list state
        val mListState =
            (binding.recyclerView.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
        state.putInt("LIST_STATE_KEY", mListState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        // Retrieve list state and list/item positions
        mListState = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            savedInstanceState.getInt("LIST_STATE_KEY", 0)
        } else {
            savedInstanceState.getInt("LIST_STATE_KEY", 0)
        }
    }


    override fun onResume() {
        super.onResume()
        mListState?.let {
            Log.e("Resumed", "Activity Is Resumed")
            binding.recyclerView.scrollToPosition(it)
        }
    }


}
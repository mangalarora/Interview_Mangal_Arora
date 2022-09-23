package dev.mnglarora.interview.ui.home

import android.os.Parcelable
import androidx.lifecycle.ViewModel
import dev.mnglarora.interview.model.GhRepo
import dev.mnglarora.interview.repository.MainRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class MainViewModel(private val repository: MainRepository) : ViewModel() {

    private val _selectedListFlow = MutableStateFlow<ArrayList<Int>>(arrayListOf())
    val selectedListFlow = _selectedListFlow.asStateFlow()

    var parcelable : Parcelable? = null

    fun updateSelectedList(id: Int) {
        if (_selectedListFlow.value.contains(id))
            _selectedListFlow.value.remove(id)
        else
            _selectedListFlow.value.add(id)
    }

    fun updateRecyclerState(state:Parcelable){

        parcelable = state

    }

    fun getLocalRepos(): Flow<List<GhRepo>> = repository.getLocalRepos()


}
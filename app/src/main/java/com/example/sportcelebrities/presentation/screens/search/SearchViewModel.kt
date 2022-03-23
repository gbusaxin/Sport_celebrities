package com.example.sportcelebrities.presentation.screens.search

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.sportcelebrities.domain.model.Celebrity
import com.example.sportcelebrities.domain.use_cases.UseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val useCases: UseCases
) : ViewModel() {

    private val _searchQuery = mutableStateOf("")
    val searchQuery = _searchQuery

    private val _searchedCelebrity = MutableStateFlow<PagingData<Celebrity>>(PagingData.empty())
    val searchedCelebrity = _searchedCelebrity

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun searchCelebrity(query:String){
        viewModelScope.launch(Dispatchers.IO) {
            useCases.searchCelebrityUseCase(query = query).cachedIn(viewModelScope).collect {
                _searchedCelebrity.value = it
            }
        }
    }

}
package com.orumgames.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.orumgames.domain.common.usecase.LoadBeersUseCase
import com.orumgames.domain.common.usecase.flow.onFailure
import com.orumgames.domain.common.usecase.flow.onSuccess
import com.orumgames.domain.models.Beer
import com.orumgames.ui.base.BaseViewModel
import com.orumgames.ui.utils.merge
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val loadBeersUseCase: LoadBeersUseCase
) : BaseViewModel() {

    private val _states = MutableStateFlow<State>(State.Loading(false))
    val states: StateFlow<State> = _states.asStateFlow()

    private val _beer = MutableLiveData<List<Beer>>()
    val beer: LiveData<List<Beer>>
        get() = _beer
    private var currentPage = 0

    fun viewAllBeers() = viewModelScope.launch {
        currentPage++
        loadBeersUseCase
            .execute(currentPage)
            .onStart { _states.value = State.Loading(true) }
            .catch { }
            .onEach { res ->
                res.onSuccess {
                    _beer.value = merge(beer.value, it)
                    _states.value = State.OnSuccess
                }.onFailure {
                    _states.value = State.OnError
                }
            }
            .onCompletion { _states.value = State.Loading(false) }
            .collect()
    }

    sealed class State {
        data class Loading(val isLoading: Boolean) : State()
        object OnSuccess : State()
        object OnError : State()
    }

    companion object {
        const val PAGE_THRESHOLD = 6
    }
}